package com.foomoo.awf.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static com.foomoo.awf.config.ValidationConfig.APPLICANT_DOB_MAX;

public class ApplicantMinimumAgeValidator implements ConstraintValidator<ApplicantMinimumAgeConstraint, LocalDate> {

    @Override
    public void initialize(ApplicantMinimumAgeConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(final LocalDate dateOfBirth, final ConstraintValidatorContext context) {
        final boolean isValid = dateOfBirth != null &&
                (APPLICANT_DOB_MAX.isAfter(dateOfBirth) || APPLICANT_DOB_MAX.isEqual(dateOfBirth));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Applicant too young. Maximum DOB: " + APPLICANT_DOB_MAX)
                   .addConstraintViolation();
        }

        return isValid;
    }
}
