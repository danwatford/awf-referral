package com.foomoo.awf.config;

import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Configuration for validation of Referrals.
 */
public class ValidationConfig {

    /**
     * Maximum accepted date of birth for applicants in YYYY-MM-DD format.
     */
    public static final LocalDate APPLICANT_DOB_MAX;

    /**
     * Validation error message to display if an applicant date of birth is given which exceeds the maximum permitted.
     */
    public static final String APPLICANT_VALIDATION_FAIL_MSG;

    private static final String PROPS_FILE_NAME = "validation.properties";

    static {
        final Configurations configs = new Configurations();
        try {
            final PropertiesConfiguration properties = configs.properties(CommonConfig.getConfigDirectory()
                                                                                      .resolve(PROPS_FILE_NAME)
                                                                                      .toString());

            final String dobString = properties.getString("applicant.dob.max");
            APPLICANT_DOB_MAX = LocalDate.parse(dobString, DateTimeFormatter.ISO_LOCAL_DATE);

            APPLICANT_VALIDATION_FAIL_MSG = properties.getString("applicant.dob.validation.failure.message");

        } catch (ConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


}
