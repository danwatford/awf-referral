package com.foomoo.awf.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Constraint annotation to validate that the annotated value represents a referral applicant's date of birth and
 * that applicant meets the minimum age requirements.
 */
@Documented
@Constraint(validatedBy = ApplicantMinimumAgeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApplicantMinimumAgeConstraint {
    String message() default "Applicant does not meet minimum age requirements.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
