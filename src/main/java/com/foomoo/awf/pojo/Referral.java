package com.foomoo.awf;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * Object holding the data related to a referral submission.
 */
@Named
@SessionScoped
public class Referral implements Serializable {

    private String applicantName;
    private LocalDate applicantDateOfBirth;
    private String applicantGender;
    private String applicantAddress;
    private String applicantTelephone;
    private String applicantEmail;

    private String referrerName;
    private String referrerJobTitle;
    private String referrerOrganisation;
    private String referrerAddress;
    private String referrerTelephone;
    private String referrerEmail;

    private String aboutApplicant;
    private String howLongWorking;
    private String howReferred;
    private String howSupportingApplicant;
    private String anyAdditionalNeeds;

    private List<String> applicableCircumstances;
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

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(final String applicantName) {
        this.applicantName = applicantName;
    }

    public LocalDate getApplicantDateOfBirth() {
        return applicantDateOfBirth;
    }

    public void setApplicantDateOfBirth(final LocalDate applicantDateOfBirth) {
        this.applicantDateOfBirth = applicantDateOfBirth;
    }

    public String getApplicantGender() {
        return applicantGender;
    }

    public void setApplicantGender(final String applicantGender) {
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

    public List<String> getApplicableCircumstances() {
        return applicableCircumstances;
    }

    public void setApplicableCircumstances(List<String> applicableCircumstances) {
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
}
