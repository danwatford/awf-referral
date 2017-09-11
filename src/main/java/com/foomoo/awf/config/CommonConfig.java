package com.foomoo.awf.config;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Common configuration used as a basis to determine other configuration items.
 */
public class CommonConfig {

    /**
     * System property key for the optional property specifying the directory containing the application's configuration files.
     */
    private static final String CONFIG_DIR_PROPERTY = "awf-referral.config.dir";

    /**
     * Get the path to the configuration directory based on the system property awf-referral.config.dir, or falling back to ./config if the system
     * property is not set.
     *
     * @return Path to the configuration directory.
     */
    public static final Path getConfigDirectory() {

        return Paths.get(System.getProperty(CONFIG_DIR_PROPERTY, "config"));
    }

}
