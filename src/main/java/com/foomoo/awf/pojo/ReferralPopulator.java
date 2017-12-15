package com.foomoo.awf.pojo;

import com.google.common.collect.ImmutableSet;

import java.net.URI;
import java.time.LocalDate;

/**
 * Populates a {@link com.foomoo.awf.pojo.Referral} with dummy data to assist testing.
 */
public class ReferralPopulator {

    public static void populateReferral(final Referral referral) {

        referral.setApplicantName("Applicant Name");
        referral.setApplicantDateOfBirth(LocalDate.of(1999, 9, 30));
        referral.setApplicantGender(Gender.FEMALE);
        referral.setApplicantAddress("Applicant Address Line 1\nAddress Line 2");
        referral.setApplicantTelephone("01234 123456");
        referral.setApplicantEmail("app@example.com");

        referral.setReferrerName("Referrer Name");
        referral.setReferrerJobTitle("Referrer Job Title");
        referral.setReferrerOrganisation("Referrer Organisation");
        referral.setReferrerAddress("Referrer Address Line 1\nAddress Line 2\nLine 3\nLine 4");
        referral.setReferrerTelephone("09876 543 2121");
        referral.setReferrerEmail("ref@example.com");
        referral.setReferrerEmailConfirmation(referral.getReferrerEmail());

        referral.setAboutApplicant("About Line 1 About Line 1 About Line 1 About Line 1 About Line 1 About Line 1 " +
                "About Line 1 \nAbout Line 2 About Line 2 About Line 2 About Line 2 \n\n" +
                "About Line 3 About Line 3 About Line 3 ");

        referral.setHowLongWorking("How Long Working Line 1 How Long Working Line 1\n" +
                "How Long Working Line 2 How Long Working Line 2");

        referral.setHowReferred("How Referred Line 1 How Referred Line 1 How Referred Line 1 \n" +
                "How Referred Line 2 How Referred Line 2");

        referral.setHowSupportingApplicant("How Supporting Line 1 How Supporting Line 1 How Supporting Line 1 \n" +
                "How Supporting Line 2 How Supporting Line 2 How Supporting Line 2 ");

        referral.setAnyAdditionalNeeds("Additional Needs Line 1 Additional Needs Line 1 Additional Needs Line 1 \n" +
                "Additional Needs Line 2 Additional Needs Line 2 Additional Needs Line 2 ");

        referral.setApplicableCircumstances(ImmutableSet.of(ApplicableCircumstance.HOMELESSNESS,
                ApplicableCircumstance.EATING_DISORDER, ApplicableCircumstance.HISTORY_OF_OFFENDING,
                ApplicableCircumstance.SELF_HARM, ApplicableCircumstance.ADDITIONAL_LEARNING_NEEDS,
                ApplicableCircumstance.OTHER));
        referral.setCircumstanceSpecificDetails("Circumstances Details Line 1 Circumstances Details Line 1 Circumstances Details Line 1 \n" +
                "Circumstances Details Line 2 Circumstances Details Line 2 Circumstances Details Line 2 \n" +
                "Circumstances Details Line 3 Circumstances Details Line 3 Circumstances Details Line 3 ");
        referral.setOtherProfessionalSupport("Other professional support line 1 Other professional support line 1 Other professional support line 1 \r\n" +
                "Other professional support line 2 Other professional support line 2 Other professional support line 2 ");
        referral.setPrescribedMedication("Prescribed Medication Line 1");

        referral.setEmergencyContactName("Emergency Name");
        referral.setEmergencyContactTelephone("04958 384738");
        referral.setEmergencyContactMobile("07983 473 4985");
        referral.setEmergencyContactEmail("emergency@example.com");
        referral.setEmergencyContactRelationship("Parent");

        referral.setExperienceInstruments("Instruments Experience");
        referral.setExperienceStudio("Studio Experience");
        referral.setExperienceSongwriting("Songwriting Experience");
        referral.setExperienceLivePerformance("Live Experience");

        referral.setWhyReady("Why Ready");

        referral.setMusicLink1(URI.create("https://www.youtube.com/watch?v=r-zYD-aVEXQ"));
    }
}
