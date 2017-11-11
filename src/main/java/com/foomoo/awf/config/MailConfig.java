package com.foomoo.awf.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

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

    private static final String PROPS_FILE_NAME = "mail.properties";

    static {
        final Configurations configs = new Configurations();
        try {
            final PropertiesConfiguration properties = configs.properties(CommonConfig.getConfigDirectory()
                                                                                      .resolve(PROPS_FILE_NAME)
                                                                                      .toString());

            RECIPIENT_ADDRESS = properties.getString("mail.recipient");

            SUBJECT = properties.getString("mail.subject");

            FROM = properties.getString("mail.from");

            CONFIRMATION_SUBJECT = properties.getString("main.confirmation.subject");

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }
}
