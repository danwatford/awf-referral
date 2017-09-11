package com.foomoo.awf.processors;

import com.foomoo.awf.pojo.Referral;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;

/**
 * Class to allow submission of referrals to the application.
 */
@Named
@RequestScoped
public class ReferralSubmitter {

    @Inject
    Referral referral;

    @Inject
    ConfirmationHandler confirmationHandler;

    @Inject
    SubmissionHandler submissionHandler;

    public String submit() throws MessagingException {

        submissionHandler.handleSubmission(referral);

        confirmationHandler.sendConfirmation(referral.getReferrerEmail());

        return "thanks";
    }

}
