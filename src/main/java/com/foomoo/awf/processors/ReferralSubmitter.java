package com.foomoo.awf.processors;

import com.foomoo.awf.pojo.Referral;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;

/**
 * Class to allow submission of referrals to the application.
 */
@Service
public class ReferralSubmitter {

    private final ConfirmationHandler confirmationHandler;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    OneDriveHandler oneDriveHandler;

    public ReferralSubmitter(ConfirmationHandler confirmationHandler, SubmissionHandler submissionHandler) {
        this.confirmationHandler = confirmationHandler;
    }

    public String submit(final Referral referral, final Collection<MultipartFile> multipartFiles) {

        final ZoneId zoneId = ZoneId.of("Europe/London");
        referral.setSubmissionDateTime(ZonedDateTime.now(zoneId));

        logger.info("Submitting referral");
        logger.info("Referral submitted by: " + referral.getReferrerOrganisation() + "/" + referral.getReferrerName() + "/" + referral.getReferrerEmail());
        logger.debug("Referral Details: " + referral);

        oneDriveHandler.handleSubmission(referral, multipartFiles);

        confirmationHandler.sendConfirmation(referral.getReferrerEmail());

        return "thanks";
    }

}
