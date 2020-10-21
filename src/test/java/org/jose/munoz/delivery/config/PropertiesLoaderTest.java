package org.jose.munoz.delivery.config;

import org.jose.munoz.delivery.config.PropertiesLoader;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class PropertiesLoaderTest {

    private PropertiesLoader propertiesLoader;

    @Before
    public void before() {
        propertiesLoader = PropertiesLoader.getInstance();
        propertiesLoader.setProperty("test.one", "test.one");
    }

    @Test
    public void getDefinedProperties() {
        assertTrue("test.one".equals(propertiesLoader.getProperty("test.one")));
        assertTrue(null == propertiesLoader.getProperty("test.two"));
    }
}
