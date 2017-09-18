package com.foomoo.awf.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<Email, String> {

    private org.apache.commons.validator.routines.EmailValidator emailValidator;

    @Override
    public void initialize(final Email constraintAnnotation) {
        emailValidator = org.apache.commons.validator.routines.EmailValidator.getInstance();
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext context) {
        return emailValidator.isValid(value);
    }
}
