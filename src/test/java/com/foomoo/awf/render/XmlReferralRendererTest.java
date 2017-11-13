package com.foomoo.awf.render;

import com.foomoo.awf.pojo.ApplicableCircumstance;
import com.foomoo.awf.pojo.Gender;
import com.foomoo.awf.pojo.Referral;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.io.IOUtils;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.Assert.assertThat;

/**
 * Tests for the {@link XmlReferralRenderer} class.
 */
public class XmlReferralRendererTest {

    /**
     * Ensures the expected XML output is rendered for the test referral.
     */
    @Test
    public void generatesXmlForReferral() throws IOException {

        final String expectedXml = IOUtils.toString(Thread.currentThread()
                                                          .getContextClassLoader()
                                                          .getResourceAsStream("XmlRenderer-expected-output.xml"), StandardCharsets.UTF_8);

        final Referral referral = new Referral();
        referral.setApplicantName("Applicant Name");
        referral.setApplicantGender(Gender.FEMALE);
        referral.setApplicantDateOfBirth(LocalDate.of(1999, 1, 12));
        referral.setApplicantAddress("Applicant address\nsecond line");
        referral.setReferrerName("Referrer Name");
        referral.setReferrerAddress("Referrer address\nsecond line");

        referral.setApplicableCircumstances(ImmutableSet.of(
                ApplicableCircumstance.ADDITIONAL_LEARNING_NEEDS,
                ApplicableCircumstance.HOMELESSNESS,
                ApplicableCircumstance.OTHER));

        final ZoneId zoneId = ZoneId.of("Europe/London");

        referral.setSubmissionDateTime(LocalDateTime.of(2017, 9, 11, 15, 33, 43)
                                                    .atZone(zoneId));

        final XmlReferralRenderer xmlReferralRenderer = new XmlReferralRenderer();
        final String renderedXml = xmlReferralRenderer.render(referral);

        assertThat(renderedXml, Matchers.equalToIgnoringWhiteSpace(expectedXml));
    }
}
