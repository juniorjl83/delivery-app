package org.jose.munoz.delivery.domain;

import org.jose.munoz.delivery.config.PropertiesLoader;
import org.jose.munoz.delivery.file.FileReader;
import org.jose.munoz.delivery.file.FileWriter;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class represents a Drone
 *
 * @author Jose Munoz (jose.munoz@gmail.com)
 */
public class Drone {
    private int id;
    private int x;
    private int y;
    private int direction;
    private final PropertiesLoader propertiesLoader;
    private Queue<String> queue;
    private final FileReader reader;
    private final FileWriter writer;

    /**
     * class constructor
     *
     * @param propertiesLoader
     * @param id
     */
    public Drone(PropertiesLoader propertiesLoader, int id, FileReader reader, FileWriter writer) {
        this.x = 0;
        this.y = 0;
        this.direction = 0;
        this.propertiesLoader = propertiesLoader;
        this.id = id;
        this.queue = new LinkedList<String>();
        this.reader = reader;
        this.writer = writer;
    }

    /**
     * method to start drone working
     */
    public void deliver() {
        queue = reader.read(this.id);
        int numberDeliveries = propertiesLoader.getIntProperty("max.delivery.to.attend");
        Queue<String> orders = new LinkedList<>();
        String controlValue = "";
        while (!queue.isEmpty()) {
            for (int i = 0; i < numberDeliveries && !queue.isEmpty(); i++) {
                orders.add(queue.remove());

            }

            while (!orders.isEmpty()) {
                String order = orders.remove();
                if (!isValidDestinationLimit(order)) {
                    System.out.println(String.format(propertiesLoader.getProperty("error.exceed.limit"), id, order));
                } else {
                    runDelivery(order, false);
                    FileWriter fw = new FileWriter(propertiesLoader);
                    fw.writeFile(getActualPosition(), id);
                }
                setInitialPosition();
            }
        }
    }

    /**
     * get actual drone position
     */
    protected String getActualPosition() {
        return String.format("(%s,%s) %s", x, y, getStringDirection());
    }

    protected boolean isValidDestinationLimit(String sequence) {
        runDelivery(sequence, true);
        int limit = propertiesLoader.getIntProperty("blocks.distance");
        boolean result = true;
        if (Math.abs(x) > limit || Math.abs(y) > limit) {
            result = false;
        }
        setInitialPosition();
        return result;
    }

    /**
     * method that run one delivery
     */
    private void runDelivery(String sequence, boolean isValidation) {
        for (char step : sequence.toCharArray()) {
            switch (step) {
                case 'A':
                    advance();
                    break;
                case 'I':
                    turnLeft();
                    break;
                case 'D':
                    turnRight();
                    break;
                default:
                    if (!isValidation) {
                        System.out.println(String.format(propertiesLoader.getProperty("error.invalid.step"), id, sequence, step));
                    }
                    break;
            }
        }
    }

    /**
     * method that return drone to the initial position
     */
    private void setInitialPosition() {
        this.x = 0;
        this.y = 0;
        this.direction = 0;
    }

    /**
     * method thet get the drone pointed direction
     */
    private String getStringDirection() {
        String stringDirection = null;
        switch (direction) {
            case 0:
                stringDirection = propertiesLoader.getProperty("drone.north.direction");
                break;
            case 1:
                stringDirection = propertiesLoader.getProperty("drone.east.direction");
                break;
            case 2:
                stringDirection = propertiesLoader.getProperty("drone.south.direction");
                break;
            case 3:
                stringDirection = propertiesLoader.getProperty("drone.west.direction");
                break;
            default:
                break;
        }
        return stringDirection;
    }

    /**
     * method that turns right the drone
     */
    private void turnRight() {
        direction = direction == 3 ? 0 : direction + 1;
    }

    /**
     * method that turns left the drone
     */
    private void turnLeft() {
        direction = direction == 0 ? 3 : direction - 1;
    }

    /**
     * method that advance one step the drone
     */

    private void advance() {
        switch (direction) {
            case 0:
                y++;
                break;
            case 1:
                x++;
                break;
            case 2:
                y--;
                break;
            case 3:
                x--;
                break;
            default:
                break;
        }
    }
}
