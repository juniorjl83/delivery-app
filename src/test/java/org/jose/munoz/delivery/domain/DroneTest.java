package org.jose.munoz.delivery.domain;

import org.jose.munoz.delivery.config.PropertiesLoader;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

import static org.junit.Assert.assertTrue;

public class DroneTest {

    PropertiesLoader propertiesLoader = mock(PropertiesLoader.class);
    private InOrder ordered;

    @Before
    public void setUp() {
        ordered = inOrder(propertiesLoader);
        when(propertiesLoader.getProperty("error.invalid.step")).thenReturn("Invalid Step. Nothing to do");
        when(propertiesLoader.getProperty("drone.north.direction")).thenReturn("North Direction");
        when(propertiesLoader.getProperty("drone.east.direction")).thenReturn("East Direction");
        when(propertiesLoader.getProperty("drone.south.direction")).thenReturn("South Direction");
        when(propertiesLoader.getProperty("drone.west.direction")).thenReturn("West Direction");
    }

    @Test
    public void droneInitialPositionTest() {
        Drone drone = new Drone(propertiesLoader);
        assertTrue("(0,0) North Direction".equals(drone.getActualPosition()));
        ordered.verify(propertiesLoader, times(1)).getProperty("drone.north.direction");
    }

    @Test
    public void testAllDirectionsDeliveryTest() {
        Drone drone = new Drone(propertiesLoader);
        drone.startDelivery("DAIAAIAAADAIA");
        assertTrue("(-3,3) West Direction".equals(drone.getActualPosition()));
        ordered.verify(propertiesLoader, times(1)).getProperty("drone.west.direction");
    }

    @Test
    public void basicDeliveryWrongDirectionTest() {
        Drone drone = new Drone(propertiesLoader);
        drone.startDelivery("AAAARDDAAA");
        assertTrue("(0,1) South Direction".equals(drone.getActualPosition()));
        ordered.verify(propertiesLoader, times(1)).getProperty("error.invalid.step");
        ordered.verify(propertiesLoader, times(1)).getProperty("drone.south.direction");
    }
}
