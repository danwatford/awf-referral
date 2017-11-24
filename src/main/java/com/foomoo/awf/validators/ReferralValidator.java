package com.foomoo.awf.validators;

import com.foomoo.awf.pojo.Referral;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static com.foomoo.awf.config.ValidationConfig.APPLICANT_DOB_MAX;

public class ReferralValidator implements Validator {
    @Override
    public boolean supports(final Class<?> clazz) {
        return Referral.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        final Referral referral = (Referral) target;

        final LocalDate dateOfBirth = referral.getApplicantDateOfBirth();
        // Non-null validation of DOB is handled by annotations in Referral.
        if (dateOfBirth != null) {
            if (!(APPLICANT_DOB_MAX.isAfter(dateOfBirth) || APPLICANT_DOB_MAX.isEqual(dateOfBirth))) {
                errors.rejectValue("applicantDateOfBirth", "applicant.dob.too.recent");
            }
        }

        final String referrerEmail = referral.getReferrerEmail();
        final String referrerEmailConfirmation = referral.getReferrerEmailConfirmation();
        // Non-null validation of email and email confirmation is handled by annotations in Referral.
        if (referrerEmail != null && referrerEmailConfirmation != null) {
            if (!referrerEmail.equalsIgnoreCase(referrerEmailConfirmation)) {
                errors.rejectValue("referrerEmailConfirmation", "referrer.email.mismatch");
            }
        }
    }
}
