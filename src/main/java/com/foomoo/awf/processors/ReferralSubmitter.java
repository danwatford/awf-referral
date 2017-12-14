package com.foomoo.awf.processors;

import com.foomoo.awf.pojo.Referral;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Class to allow submission of referrals to the application.
 */
@Service
public class ReferralSubmitter {

    private final ConfirmationHandler confirmationHandler;

    @Autowired
    OneDriveHandler oneDriveHandler;

    public ReferralSubmitter(ConfirmationHandler confirmationHandler, SubmissionHandler submissionHandler) {
        this.confirmationHandler = confirmationHandler;
    }

    public String submit(final Referral referral) {

        final ZoneId zoneId = ZoneId.of("Europe/London");
        referral.setSubmissionDateTime(ZonedDateTime.now(zoneId));

        oneDriveHandler.handleSubmission(referral);

        confirmationHandler.sendConfirmation(referral.getReferrerEmail());

        return "thanks";
    }

}
