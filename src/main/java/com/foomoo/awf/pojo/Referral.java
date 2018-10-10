package com.foomoo.awf.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.foomoo.awf.render.LocalDateAdapter;
import com.foomoo.awf.render.ZonedDateTimeAdapter;
import lombok.Data;
import org.hibernate.validator.constraints.Email;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.net.URI;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * Object holding the data related to a referral submission.
 */
@Data
@XmlRootElement
public class Referral implements Serializable {

    @Id
    private UUID id;

    @NotNull(message = "Please enter the applicant''s name.")
    private String applicantName;
    @NotNull(message = "Please enter the applicant''s date of birth.")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate applicantDateOfBirth;
    private Gender applicantGender;
    @NotNull(message = "Please enter the applicant''s address.")
    private String applicantAddress;
    private String applicantTelephone;
    @NotNull(message = "Please enter the applicant''s email address.")
    @Email(message = "Applicant email address format is invalid.")
    private String applicantEmail;

    @NotNull(message = "Please enter your name.")
    private String referrerName;
    private String referrerJobTitle;
    @NotNull(message = "Please enter the name or your organisation.")
    private String referrerOrganisation;
    @NotNull(message = "Please enter your organisation's address.")
    private String referrerAddress;
    private String referrerTelephone;
    @NotNull(message = "Please enter your email address.")
    @Email(message = "Your email address format is invalid.")
    private String referrerEmail;
    @NotNull(message = "Please confirm your email address.")
    @Email(message = "Your email address format is invalid.")
    private String referrerEmailConfirmation;

    private String aboutApplicant;
    private String howLongWorking;
    private String howReferred;
    private String howSupportingApplicant;
    private String anyAdditionalNeeds;

    private Set<ApplicableCircumstance> applicableCircumstances;
    private String circumstanceSpecificDetails;
    private String otherProfessionalSupport;
    private String prescribedMedication;

    private String emergencyContactName;
    private String emergencyContactTelephone;
    private String emergencyContactMobile;
    private String emergencyContactEmail;
    private String emergencyContactRelationship;

    private String experienceInstruments;
    private String experienceStudio;
    private String experienceSongwriting;
    private String experienceLivePerformance;

    private String whyReady;

    private URI musicLink1;
    private URI musicLink2;

    @JsonIgnore
    private ZonedDateTime submissionDateTime;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getApplicantDateOfBirth() {
        return applicantDateOfBirth;
    }

    @XmlJavaTypeAdapter(value = ZonedDateTimeAdapter.class)
    public ZonedDateTime getSubmissionDateTime() {
        return submissionDateTime;
    }
}
