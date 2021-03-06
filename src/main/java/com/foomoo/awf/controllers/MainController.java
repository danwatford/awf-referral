package com.foomoo.awf.controllers;

import com.foomoo.awf.pojo.*;
import com.foomoo.awf.processors.ReferralSubmitter;
import com.foomoo.awf.validators.ReferralValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Name of the {@link Referral} object as stored in the user's HTTP session.
     */
    private static final String REFERRAL_ID_SESSION_ATTRIBUTE = "AWFREFERRAL_ID";

    /**
     * Length of time that sessions shall persist for.
     */
    private static final Duration SESSION_DURATION = Duration.ofDays(30);

    private final ReferralSubmitter referralSubmitter;

    private final ReferralRepository referralRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MainController(final ReferralSubmitter referralSubmitter, final ReferralRepository referralRepository) {
        this.referralSubmitter = referralSubmitter;
        this.referralRepository = referralRepository;
    }

    @ModelAttribute("allApplicableCircumstances")
    public List<ApplicableCircumstance> populateApplicableCicumstances() {
        return Arrays.asList(ApplicableCircumstance.values());
    }

    @ModelAttribute("allGenders")
    public List<Gender> populateGenders() {
        return Arrays.asList(Gender.values());
    }

    /**
     * Retrieve a view containing the client's {@link Referral} as found in the {@link HttpSession}. If no Referral is
     * found a new one will be created and stored in the session.
     *
     * @param model   The model used to render the view. Shall be populated with the client's Referral.
     * @param session The HttpSession to retrieve the {@link Referral} for.
     * @return The view to render the referral form.
     */
    @GetMapping
    public String getReferralFormView(final Model model, final HttpSession session) {

        logger.info("getReferralFormView: SessionId: " + session.getId());

        session.setMaxInactiveInterval((int) SESSION_DURATION.getSeconds());

        final Referral referral = getOrCreateReferralForSession(session);
        model.addAttribute(referral);

        return "form";
    }

    /**
     * Clear the  {@link Referral} held in the client's {@link HttpSession}.
     *
     * @param session The client's HttpSession.
     * @return A redirect view to get the referral form view.
     */
    @PostMapping(params = "action=clear")
    public RedirectView clearReferral(final HttpSession session) {
        logger.info("clearReferral: SessionId: " + session.getId());
        setReferralOnSession(session, new Referral());
        return new RedirectView("");
    }

    /**
     * Save the given {@link Referral} to the client's {@link HttpSession} and redirect to get the referral form view.
     *
     * @param referral The Referral to save.
     * @param session  The client's HttpSession.
     * @return A redirect view to get the referral form view.
     */
    @PostMapping(params = "action=save")
    public RedirectView saveReferral(final Referral referral, final HttpSession session) {
        logger.info("saveReferral: SessionId: " + session.getId());
        logger.debug("Referral details: " + referral);

        setReferralOnSession(session, referral);
        return new RedirectView("");
    }

    /**
     * If the given {@link Referral} is valid submit it to the configured {@link ReferralSubmitter} and return
     * a submission commplete view. If the Referral is invalid then return the form view.
     * <p>
     * Regardless of the validity of the Referral, store it in the {@link HttpSession} to it can be retrieved later.
     *
     * @param referral      The Referral to submit.
     * @param bindingResult Result of the Referral's validity checking.
     * @param session       The client's session.
     * @return View indicating submission completion or failure.
     */
    @PostMapping(params = "action=submit")
    public String checkReferral(@Valid final Referral referral,
                                final BindingResult bindingResult,
                                @RequestParam("file1") final MultipartFile file1,
                                @RequestParam("file2") final MultipartFile file2,
                                final HttpSession session) {

        logger.info("checkReferral: SessionId: " + session.getId());
        logger.debug("Referral details: " + referral);

        setReferralOnSession(session, referral);

        if (bindingResult.hasErrors()) {
            return "form";
        } else {

            final List<MultipartFile> multipartFiles = Stream.of(file1, file2)
                    .filter(f -> !f.isEmpty()).collect(Collectors.toList());
            referralSubmitter.submit(referral, multipartFiles);

            // Clear the referral from the session.
            setReferralOnSession(session, new Referral());

            return "thanks";
        }
    }

    /**
     * Create a new {@link Referral} held in the client's {@link HttpSession} populated with test data.
     *
     * @param session The client's HttpSession.
     * @return A redirect view to get the referral form view.
     */
    @GetMapping("test")
    public RedirectView populateTestData(final HttpSession session) {
        final Referral referral = new Referral();
        ReferralPopulator.populateReferral(referral);
        setReferralOnSession(session, referral);
        return new RedirectView("");
    }

    /**
     * Initialise the data binder for this controller.
     */
    @InitBinder
    protected void initBinder(final WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new ReferralValidator());
    }

    /**
     * Retrieves the {@link Referral} for the given HttpSession. If no referral exists for the session then create
     * a new one.
     *
     * @param session The HttpSession to retrieve or create a Referral for.
     * @return The retrieved or created Referral.
     */
    private Referral getOrCreateReferralForSession(final HttpSession session) {
        final Object attribute = session.getAttribute(REFERRAL_ID_SESSION_ATTRIBUTE);
        Referral referral;
        if (attribute == null) {
            // Since there is no information about any Referrals in the session, just create a blank
            // referral and don't worry about persisting it at this stage.
            referral = new Referral();
        } else {
            referral = referralRepository.findOne((UUID) attribute);
            if (referral == null) {
                referral = new Referral();
            }
        }

        return referral;
    }

    /**
     * Persists the given Referral, reusing the referral id from the session.
     * If no referral id exists, create a new id and store on the session.
     *
     * @param session  The user's HttpSession.
     * @param referral The Referral to persist as part of the session.
     */
    private void setReferralOnSession(final HttpSession session, final Referral referral) {

        final Object attribute = session.getAttribute(REFERRAL_ID_SESSION_ATTRIBUTE);
        final UUID referralId;
        if (attribute == null) {
            referralId = UUID.randomUUID();
            session.setAttribute(REFERRAL_ID_SESSION_ATTRIBUTE, referralId);
        } else {
            referralId = (UUID) attribute;
        }

        referral.setId(referralId);
        referralRepository.save(referral);
    }

}
