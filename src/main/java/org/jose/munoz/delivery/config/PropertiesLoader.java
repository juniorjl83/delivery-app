package org.jose.munoz.delivery.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Singleton class help get properties defined to the application
 *
 * @author Jose Munoz (jose.munoz@gmail.com)
 */
public class PropertiesLoader {
    private static PropertiesLoader instance = null;
    private Properties properties = new Properties();
    private ClassLoader loader = Thread.currentThread().getContextClassLoader();

    /**
     * Default class constructor
     */
    private PropertiesLoader() {
        try (InputStream resourceStream = loader.getResourceAsStream("application.properties")) {
            properties.load(resourceStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * get instance of the singleton
     */
    public static PropertiesLoader getInstance() {
        if (instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    /**
     * get String property by key
     *
     * @param key
     * @return
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * get int property by key
     *
     * @param key
     * @return
     */
    public int getIntProperty(String key) {
        try {
            return Integer.parseInt(properties.getProperty(key));
        } catch (NumberFormatException e) {
            return 0;
        }

    }

    /**
     * set property
     *
     * @param key
     * @return
     */
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
