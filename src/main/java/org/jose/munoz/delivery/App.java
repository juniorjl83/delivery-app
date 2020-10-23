package org.jose.munoz.delivery;

import org.jose.munoz.delivery.config.PropertiesLoader;
import org.jose.munoz.delivery.domain.Drone;
import org.jose.munoz.delivery.file.FileReader;
import org.jose.munoz.delivery.file.FileWriter;

public class App {
    public static void main(String[] args) {
        PropertiesLoader propertiesLoader = PropertiesLoader.getInstance();
        int numberDrones = propertiesLoader.getIntProperty("drones.quantity");

        Thread[] threads = new Thread[numberDrones];
        for (int i = 0; i < threads.length; i++) {
            int id = i + 1;
            threads[i] = new Thread(new Runnable() {
                public void run() {
                    Drone drone = new Drone(propertiesLoader, id,
                            new FileReader(propertiesLoader), new FileWriter(propertiesLoader));
                    drone.deliver();
                }
            });
            threads[i].start();
        }
    }
}
