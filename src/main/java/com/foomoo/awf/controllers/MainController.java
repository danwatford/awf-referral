package com.foomoo.awf.controllers;

import com.foomoo.awf.pojo.ApplicableCircumstance;
import com.foomoo.awf.pojo.Gender;
import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.pojo.ReferralPopulator;
import com.foomoo.awf.processors.ReferralSubmitter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final ReferralSubmitter referralSubmitter;

    public MainController(ReferralSubmitter referralSubmitter) {
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

    @GetMapping
    public String createForm(Referral referral) {
        ReferralPopulator.populateReferral(referral);
        return "form";
    }

    @PostMapping
    public String checkReferral(@Valid Referral referral, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        } else {
            referralSubmitter.submit(referral);
            return "thanks";
        }
    }
}
