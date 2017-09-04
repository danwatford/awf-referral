package com.foomoo.awf.render;

import com.foomoo.awf.pojo.Referral;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
        referral.setApplicantGender("Female");
        referral.setApplicantDateOfBirth(LocalDate.of(1999, 1, 12));
        referral.setApplicantAddress("Applicant address\nsecond line");
        referral.setReferrerName("Referrer Name");
        referral.setReferrerAddress("Referrer address\nsecond line");

        final XmlReferralRenderer xmlReferralRenderer = new XmlReferralRenderer();
        final String renderedXml = xmlReferralRenderer.render(referral);

        assertEquals(expectedXml, renderedXml);

    }
}
