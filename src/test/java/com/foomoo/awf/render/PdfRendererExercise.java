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

@Tag("integration")
class PdfRendererExercise {

    /**
     * Exercises the PDF Renderer, triggering it to read any required configuration files.
     */
    @Test
    public void rendersReferral() throws IOException {

        final Referral referral = new Referral();
        ReferralPopulator.populateReferral(referral);

        final String xmlInput = new XmlReferralRenderer().render(referral);

        final PdfRenderer pdfRenderer = new PdfRenderer();

        final OutputStream os = Files.newOutputStream(Paths.get("test.pdf"));
        pdfRenderer.render(xmlInput, os);
    }
}