package com.foomoo.awf.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.foomoo.awf.render.LocalDateAdapter;
import com.foomoo.awf.render.ZonedDateTimeAdapter;
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



    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    public LocalDate getApplicantDateOfBirth() {
        return applicantDateOfBirth;
    }

    public void setApplicantDateOfBirth(final LocalDate applicantDateOfBirth) {
        this.applicantDateOfBirth = applicantDateOfBirth;
    }

    public Gender getApplicantGender() {
        return applicantGender;
    }

    public void setApplicantGender(final Gender applicantGender) {
        this.applicantGender = applicantGender;
    }

    public String getApplicantAddress() {
        return applicantAddress;
    }

    public void setApplicantAddress(final String applicantAddress) {
        this.applicantAddress = applicantAddress;
    }

    public String getApplicantTelephone() {
        return applicantTelephone;
    }

    public void setApplicantTelephone(final String applicantTelephone) {
        this.applicantTelephone = applicantTelephone;
    }

    public String getApplicantEmail() {
        return applicantEmail;
    }

    public void setApplicantEmail(final String applicantEmail) {
        this.applicantEmail = applicantEmail;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(final String referrerName) {
        this.referrerName = referrerName;
    }

    public String getReferrerJobTitle() {
        return referrerJobTitle;
    }

    public void setReferrerJobTitle(final String referrerJobTitle) {
        this.referrerJobTitle = referrerJobTitle;
    }

    public String getReferrerOrganisation() {
        return referrerOrganisation;
    }

    public void setReferrerOrganisation(final String referrerOrganisation) {
        this.referrerOrganisation = referrerOrganisation;
    }

    public String getReferrerAddress() {
        return referrerAddress;
    }

    public void setReferrerAddress(final String referrerAddress) {
        this.referrerAddress = referrerAddress;
    }

    public String getReferrerTelephone() {
        return referrerTelephone;
    }

    public void setReferrerTelephone(final String referrerTelephone) {
        this.referrerTelephone = referrerTelephone;
    }

    public String getReferrerEmail() {
        return referrerEmail;
    }

    public void setReferrerEmail(final String referrerEmail) {
        this.referrerEmail = referrerEmail;
    }

    public String getReferrerEmailConfirmation() {
        return referrerEmailConfirmation;
    }

    public void setReferrerEmailConfirmation(final String referrerEmailConfirmation) {
        this.referrerEmailConfirmation = referrerEmailConfirmation;
    }

    public String getAboutApplicant() {
        return aboutApplicant;
    }

    public void setAboutApplicant(final String aboutApplicant) {
        this.aboutApplicant = aboutApplicant;
    }

    public String getHowLongWorking() {
        return howLongWorking;
    }

    public void setHowLongWorking(final String howLongWorking) {
        this.howLongWorking = howLongWorking;
    }

    public String getHowReferred() {
        return howReferred;
    }

    public void setHowReferred(final String howReferred) {
        this.howReferred = howReferred;
    }

    public String getHowSupportingApplicant() {
        return howSupportingApplicant;
    }

    public void setHowSupportingApplicant(final String howSupportingApplicant) {
        this.howSupportingApplicant = howSupportingApplicant;
    }

    public String getAnyAdditionalNeeds() {
        return anyAdditionalNeeds;
    }

    public void setAnyAdditionalNeeds(final String anyAdditionalNeeds) {
        this.anyAdditionalNeeds = anyAdditionalNeeds;
    }

    public Set<ApplicableCircumstance> getApplicableCircumstances() {
        return applicableCircumstances;
    }

    public void setApplicableCircumstances(Set<ApplicableCircumstance> applicableCircumstances) {
        this.applicableCircumstances = applicableCircumstances;
    }

    public String getCircumstanceSpecificDetails() {
        return circumstanceSpecificDetails;
    }

    public void setCircumstanceSpecificDetails(final String circumstanceSpecificDetails) {
        this.circumstanceSpecificDetails = circumstanceSpecificDetails;
    }

    public String getOtherProfessionalSupport() {
        return otherProfessionalSupport;
    }

    public void setOtherProfessionalSupport(String otherProfessionalSupport) {
        this.otherProfessionalSupport = otherProfessionalSupport;
    }

    public String getPrescribedMedication() {
        return prescribedMedication;
    }

    public void setPrescribedMedication(String prescribedMedication) {
        this.prescribedMedication = prescribedMedication;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactTelephone() {
        return emergencyContactTelephone;
    }

    public void setEmergencyContactTelephone(String emergencyContactTelephone) {
        this.emergencyContactTelephone = emergencyContactTelephone;
    }

    public String getEmergencyContactMobile() {
        return emergencyContactMobile;
    }

    public void setEmergencyContactMobile(String emergencyContactMobile) {
        this.emergencyContactMobile = emergencyContactMobile;
    }

    public String getEmergencyContactEmail() {
        return emergencyContactEmail;
    }

    public void setEmergencyContactEmail(String emergencyContactEmail) {
        this.emergencyContactEmail = emergencyContactEmail;
    }

    public String getEmergencyContactRelationship() {
        return emergencyContactRelationship;
    }

    public void setEmergencyContactRelationship(String emergencyContactRelationship) {
        this.emergencyContactRelationship = emergencyContactRelationship;
    }

    public String getExperienceInstruments() {
        return experienceInstruments;
    }

    public void setExperienceInstruments(String experienceInstruments) {
        this.experienceInstruments = experienceInstruments;
    }

    public String getExperienceStudio() {
        return experienceStudio;
    }

    public void setExperienceStudio(String experienceStudio) {
        this.experienceStudio = experienceStudio;
    }

    public String getExperienceSongwriting() {
        return experienceSongwriting;
    }

    public void setExperienceSongwriting(String experienceSongwriting) {
        this.experienceSongwriting = experienceSongwriting;
    }

    public String getExperienceLivePerformance() {
        return experienceLivePerformance;
    }

    public void setExperienceLivePerformance(String experienceLivePerformance) {
        this.experienceLivePerformance = experienceLivePerformance;
    }

    public String getWhyReady() {
        return whyReady;
    }

    public void setWhyReady(String whyReady) {
        this.whyReady = whyReady;
    }

    public URI getMusicLink1() {
        return musicLink1;
    }

    public void setMusicLink1(URI musicLink1) {
        this.musicLink1 = musicLink1;
    }

    public URI getMusicLink2() {
        return musicLink2;
    }

    public void setMusicLink2(URI musicLink2) {
        this.musicLink2 = musicLink2;
    }

    @XmlJavaTypeAdapter(value = ZonedDateTimeAdapter.class)
    public ZonedDateTime getSubmissionDateTime() {
        return submissionDateTime;
    }

    public void setSubmissionDateTime(final ZonedDateTime submissionDateTime) {
        this.submissionDateTime = submissionDateTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Referral{" +
                "id=" + id +
                ", applicantName='" + applicantName + '\'' +
                ", applicantDateOfBirth=" + applicantDateOfBirth +
                ", applicantGender=" + applicantGender +
                ", applicantAddress='" + applicantAddress + '\'' +
                ", applicantTelephone='" + applicantTelephone + '\'' +
                ", applicantEmail='" + applicantEmail + '\'' +
                ", referrerName='" + referrerName + '\'' +
                ", referrerJobTitle='" + referrerJobTitle + '\'' +
                ", referrerOrganisation='" + referrerOrganisation + '\'' +
                ", referrerAddress='" + referrerAddress + '\'' +
                ", referrerTelephone='" + referrerTelephone + '\'' +
                ", referrerEmail='" + referrerEmail + '\'' +
                ", referrerEmailConfirmation='" + referrerEmailConfirmation + '\'' +
                ", aboutApplicant='" + aboutApplicant + '\'' +
                ", howLongWorking='" + howLongWorking + '\'' +
                ", howReferred='" + howReferred + '\'' +
                ", howSupportingApplicant='" + howSupportingApplicant + '\'' +
                ", anyAdditionalNeeds='" + anyAdditionalNeeds + '\'' +
                ", applicableCircumstances=" + applicableCircumstances +
                ", circumstanceSpecificDetails='" + circumstanceSpecificDetails + '\'' +
                ", otherProfessionalSupport='" + otherProfessionalSupport + '\'' +
                ", prescribedMedication='" + prescribedMedication + '\'' +
                ", emergencyContactName='" + emergencyContactName + '\'' +
                ", emergencyContactTelephone='" + emergencyContactTelephone + '\'' +
                ", emergencyContactMobile='" + emergencyContactMobile + '\'' +
                ", emergencyContactEmail='" + emergencyContactEmail + '\'' +
                ", emergencyContactRelationship='" + emergencyContactRelationship + '\'' +
                ", experienceInstruments='" + experienceInstruments + '\'' +
                ", experienceStudio='" + experienceStudio + '\'' +
                ", experienceSongwriting='" + experienceSongwriting + '\'' +
                ", experienceLivePerformance='" + experienceLivePerformance + '\'' +
                ", whyReady='" + whyReady + '\'' +
                ", musicLink1=" + musicLink1 +
                ", musicLink2=" + musicLink2 +
                ", submissionDateTime=" + submissionDateTime +
                '}';
    }
}
