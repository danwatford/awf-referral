package com.foomoo.awf.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;

/**
 * Configuration for forwarding submitted referrals by email.
 */
public class MailConfig {

    /**
     * Recipient for referral submissions forwarded by email.
     */
    public static final String RECIPIENT_ADDRESS;

    /**
     * Subject for referral submissions forwarded by email.
     */
    public static final String SUBJECT;

    /**
     * Address of party sending the email.
     */
    public static final String FROM;

    /**
     * Subject for confirmation emails sent to those making referrals.
     */
    public static final String CONFIRMATION_SUBJECT;

    /**
     * Template for the html body of confirmation emails.
     */
    public static final String CONFIRMATION_BODY_TEMPLATE;

    private static final String PROPS_FILE_NAME = "mail.properties";

    static {
        final Configurations configs = new Configurations();
        try {
            final PropertiesConfiguration properties = configs.properties(PROPS_FILE_NAME);

            RECIPIENT_ADDRESS = properties.getString("mail.recipient");

            SUBJECT = properties.getString("mail.subject");

            FROM = properties.getString("mail.from");

            CONFIRMATION_SUBJECT = properties.getString("main.confirmation.subject");

            final String confirmationTemplateResource = properties.getString("main.confirmation.body.html.template");
            final InputStream templateStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(confirmationTemplateResource);
            CONFIRMATION_BODY_TEMPLATE = IOUtils.toString(templateStream, StandardCharsets.UTF_8);
        } catch (ConfigurationException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
