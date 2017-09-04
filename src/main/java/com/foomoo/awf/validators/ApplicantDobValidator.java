package com.foomoo.awf;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.time.LocalDate;

import static com.foomoo.awf.ValidationConfig.APPLICANT_VALIDATION_FAIL_MSG;

@FacesValidator("applicantDobValidator")
public class ApplicantDobValidator implements Validator {
    @Override
    public void validate(final FacesContext context, final UIComponent component, final Object value) throws ValidatorException {

        final LocalDate dateOfBirth = (LocalDate) value;
        if (ValidationConfig.APPLICANT_DOB_MAX.isBefore(dateOfBirth)) {

            final FacesMessage message = new FacesMessage(APPLICANT_VALIDATION_FAIL_MSG);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }

    }
}
