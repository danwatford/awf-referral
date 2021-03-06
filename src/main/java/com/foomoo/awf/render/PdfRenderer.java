package com.foomoo.awf.render;

import com.foomoo.awf.config.CommonConfig;
import org.apache.avalon.framework.configuration.Configuration;
import org.apache.avalon.framework.configuration.ConfigurationException;
import org.apache.avalon.framework.configuration.DefaultConfigurationBuilder;
import org.apache.fop.apps.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.*;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Class to render an XML representation of a {@link com.foomoo.awf.pojo.Referral} to PDF using Apache FOP.
 */
//@ApplicationScoped
public class PdfRenderer {

    /**
     * Name of the Apache FOP configuration file.
     */
    private static final String FOP_CONFIG_FILE_NAME = "fop-cfg.xml";

    /**
     * Name of the Apache FOP XSLT file.
     */
    private static final String FOP_XSLT_FILE_NAME = "referral-transform.xml";

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * FOP Factory for rendering to PDF.
     */
    private FopFactory fopFactory;

    /**
     * Render the given Referral to an OutputStream representing a PDF document.
     *
     * @param referralXmlString The referral XML to render.
     * @param outputStream      The output stream to write the rendered PDF document to.
     */
    public void render(final String referralXmlString, final OutputStream outputStream) {

        try {
            final TransformerFactory transformerFactory = TransformerFactory.newInstance();
            final Transformer toFoTransformer = transformerFactory.newTransformer(new StreamSource(getFopTransformerInputStream()));

            logger.debug("Creating FO");
            final StreamSource referralXmlSource = new StreamSource(new StringReader(referralXmlString));
            final StringWriter foWriter = new StringWriter();
            toFoTransformer.transform(referralXmlSource, new StreamResult(foWriter));
            final String foXml = foWriter.toString();
            logger.debug("FO: " + foXml);

            logger.debug("Creating PDF");
            final Transformer toPdfTransformer = transformerFactory.newTransformer();
            final Fop fop = getFactory().newFop(MimeConstants.MIME_PDF, outputStream);
            final SAXResult saxResult = new SAXResult(fop.getDefaultHandler());
            toPdfTransformer.transform(new StreamSource(new StringReader(foXml)), saxResult);
            logger.debug("PDF Results: " + fop.getResults().toString());
        } catch (FOPException | TransformerException e) {
            throw new RuntimeException("Exception while rendering Referral to PDF.", e);
        }
    }

    /**
     * Get the application FOP Factory.
     *
     * @return The factory.
     */
    private synchronized FopFactory getFactory() {
        if (fopFactory == null) {
            final DefaultConfigurationBuilder cfgBuilder = new DefaultConfigurationBuilder();
            try (final InputStream fopConfigurationInputStream = getFopConfigurationInputStream()) {

                final Configuration cfg = cfgBuilder.build(fopConfigurationInputStream);
                fopFactory = new FopFactoryBuilder(URI.create("/")).setConfiguration(cfg)
                        .build();
            } catch (IOException e) {
                throw new RuntimeException("IOException while processing Apache FOP configuration file: " + getFopConfigurationPath(), e);
            } catch (SAXException e) {
                throw new RuntimeException("Parse exception while processing Apache FOP configuration file: " + getFopConfigurationPath(), e);
            } catch (ConfigurationException e) {
                throw new RuntimeException("Configuration error while processing Apache FOP configuration file: " + getFopConfigurationPath(), e);
            }
        }

        return fopFactory;
    }

    /**
     * Get the {@link Path} to the Apache FOP configuration file.
     *
     * @return Path to the configuration file.
     */
    private Path getFopConfigurationPath() {
        return CommonConfig.getConfigDirectory()
                .resolve(FOP_CONFIG_FILE_NAME);
    }

    /**
     * Get an {@link InputStream} for the Apache FOP configuration file.
     *
     * @return InputStream of the configuration file.
     */
    private InputStream getFopConfigurationInputStream() {
        final Path fopConfigurationPath = getFopConfigurationPath();
        try {
            return Files.newInputStream(fopConfigurationPath);
        } catch (IOException e) {
            throw new RuntimeException("IOException when opening Apache FOP Configuration file: " + fopConfigurationPath);
        }
    }

    /**
     * Get the {@link Path} to the Apache FOP XSLT file.
     *
     * @return Path to the configuration file.
     */
    private Path getFopTransformerPath() {
        return CommonConfig.getConfigDirectory()
                .resolve(FOP_XSLT_FILE_NAME);
    }

    /**
     * Get an {@link InputStream} for the Apache FOP XSLT file.
     *
     * @return InputStream of the XSLT file.
     */
    private InputStream getFopTransformerInputStream() {
        final Path fopConfigurationPath = getFopTransformerPath();
        try {
            return Files.newInputStream(fopConfigurationPath);
        } catch (IOException e) {
            throw new RuntimeException("IOException when opening Apache FOP XSLT file: " + fopConfigurationPath);
        }
    }
}
