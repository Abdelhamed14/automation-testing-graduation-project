
package com.orangehrm.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
                     ConfigReader.class.getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException(
                        "config.properties file was not found!"
                );
            }

            properties.load(input);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load configuration file", e
            );
        }
    }

    public static String getProperty(String key) {

        String value = properties.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Missing configuration property: " + key
            );
        }

        return value.trim();
    }

    public static int getIntProperty(String key) {
        return Integer.parseInt(getProperty(key));
    }
}
