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
                    FileWriter fw = new FileWriter();
                    FileReader fr = new FileReader();
                    Drone drone = new Drone(id, fr, fw);
                    drone.deliver();
                }
            });
            threads[i].start();
        }
    }
}
