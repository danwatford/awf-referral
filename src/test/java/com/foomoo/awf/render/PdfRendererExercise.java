package com.foomoo.awf.render;

import com.foomoo.awf.pojo.Referral;
import com.foomoo.awf.pojo.ReferralPopulator;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Tag("integration")
class PdfRendererExercise {

    /**
     * Exercises the PDF Renderer, triggering it to read any required configuration files.
     */
    @Test
    public void rendersReferral() throws IOException {

        final Referral referral = new Referral();
        ReferralPopulator.populateReferral(referral);

        final ZoneId zoneId = ZoneId.of("Europe/London");
        referral.setSubmissionDateTime(LocalDateTime.of(2017, 9, 11, 15, 33, 43)
                .atZone(zoneId));


        final String xmlInput = new XmlReferralRenderer().render(referral);

        final PdfRenderer pdfRenderer = new PdfRenderer();

        final OutputStream os = Files.newOutputStream(Paths.get("target/test.pdf"));
        pdfRenderer.render(xmlInput, os);
    }

    /**
     * Exercises rendering of existing XML to PDF.
     *
     * @throws IOException If there is an error reading the input XML.
     */
    @Test
    public void rendersXmToPdf() throws IOException {
        final String xmlString = IOUtils.toString(Thread.currentThread()
                .getContextClassLoader()
                .getResourceAsStream("PdfRenderer-input.xml"), StandardCharsets.UTF_8);

        final PdfRenderer pdfRenderer = new PdfRenderer();

        final OutputStream os = Files.newOutputStream(Paths.get("target/test.pdf"));
        pdfRenderer.render(xmlString, os);
    }
}