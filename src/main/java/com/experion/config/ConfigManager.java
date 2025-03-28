package com.experion.config;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/*
 * Centralized configuration manager that loads and provides access to application properties.
 * The configuration is loaded once during class initialization from a properties file located
 */
public class ConfigManager {
    private static Properties properties;
    
    /*
     * Static initialization block that loads the configuration file during class loading.
     * The configuration is read from 'config.properties' in /src/main/resources/config.properties
     * If loading fails, the application will fail fast with a runtime exception.
     */
    static {
        try {
            properties = new Properties();
            String configPath = System.getProperty("user.dir") + "/src/main/resources/config.properties";
            properties.load(new FileInputStream(configPath));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties", e);
        }
    }

    /*
     * Retrieves the base URL configured in the application properties.
     * 
     * @return The configured base URL, or null if the property is not defined
     */
    public static String getBaseUrl() {
        return properties.getProperty("base.url");
    }
}