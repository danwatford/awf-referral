package com.foomoo.awf.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.foomoo.awf.pojo.ApplicableCircumstance;
import com.foomoo.awf.pojo.Gender;
import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.pojo.ReferralPopulator;
import com.foomoo.awf.processors.ReferralSubmitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    /**
     * Name of the {@link Referral} object as stored in the user's HTTP session.
     */
    private static final String SESSION_REFERRAL_NAME = "AWFREFERRAL";

    /**
     * Length of time that sessions shall persist for.
     */
    private static final Duration SESSION_DURATION = Duration.ofDays(30);

    private final ReferralSubmitter referralSubmitter;

    public MainController(final ReferralSubmitter referralSubmitter) {
        this.referralSubmitter = referralSubmitter;
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
    public String checkReferral(@Valid final Referral referral, final BindingResult bindingResult,
                                final HttpSession session) {

        setReferralOnSession(session, referral);

        if (bindingResult.hasErrors()) {
            return "form";
        } else {
            referralSubmitter.submit(referral);
            return "thanks";
        }
    }

    /**
     * Create a new {@link Referral} held in the client's {@link HttpSession} populated with test data.
     *
     * @param session The client's HttpSession.
     * @return A redirect view to get the referral form view.
     */
    @PostMapping(params = "action=test-data")
    public RedirectView populateTestData(final HttpSession session) {
        final Referral referral = new Referral();
        ReferralPopulator.populateReferral(referral);
        setReferralOnSession(session, referral);
        return new RedirectView("");
    }

    /**
     * Retrieves the {@link Referral} for the given HttpSession. If no referral exists for the session then create
     * a new one.
     *
     * @param session The HttpSession to retrieve or create a Referral for.
     * @return The retrieved or created Referral.
     */
    private Referral getOrCreateReferralForSession(final HttpSession session) {
        final Object attribute = session.getAttribute(SESSION_REFERRAL_NAME);
        final Referral referral;
        if (attribute == null) {
            referral = new Referral();
            setReferralOnSession(session, referral);
        } else {
            referral = new ObjectMapper().convertValue(attribute, Referral.class);
        }

        return referral;
    }

    /**
     * Set the given {@link Referral} on the given {@link HttpSession}. This persists the referral as part of the
     * session allowing it to be retrieved later.
     *
     * @param session  The user's HttpSession.
     * @param referral The Referral to persist as part of the session.
     */
    private void setReferralOnSession(final HttpSession session, final Referral referral) {
        session.setAttribute(SESSION_REFERRAL_NAME, referral);
    }

}
