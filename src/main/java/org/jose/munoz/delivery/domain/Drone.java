package org.jose.munoz.delivery.domain;

import org.jose.munoz.delivery.config.PropertiesLoader;

public class Drone {
    private int x;
    private int y;
    private int direction;
    private final PropertiesLoader propertiesLoader;

    public Drone(PropertiesLoader propertiesLoader) {
        x = 0;
        y = 0;
        direction = 0;
        this.propertiesLoader = propertiesLoader;
    }

    public void startDelivery(String sequence) {
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
                    System.out.println(propertiesLoader.getProperty("error.invalid.step"));
                    break;
            }
        }
    }

    public String getActualPosition() {
        return String.format("(%s,%s) %s", x, y, getStringDirection());
    }

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

    private void turnRight() {
        direction = direction == 3 ? 0 : direction + 1;
    }

    private void turnLeft() {
        direction = direction == 0 ? 3 : direction - 1;
    }

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
