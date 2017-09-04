package com.foomoo.awf.render;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

@Tag("integration")
class PdfRendererIntegrationTest {

    /**
     * Tests that the PDF Renderer can be called, triggering it to read any required configuration files.
     */
    @Test
    public void rendersReferral() throws IOException {

        final String xmlInput = IOUtils.toString(Thread.currentThread()
                                                       .getContextClassLoader()
                                                       .getResourceAsStream("PdfRenderer-input.xml"), StandardCharsets.UTF_8);

        final PdfRenderer pdfRenderer = new PdfRenderer();

        final OutputStream os = Files.newOutputStream(Paths.get("test.pdf"));
        pdfRenderer.render(xmlInput, os);
    }
}