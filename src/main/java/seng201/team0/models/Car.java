package seng201.team0.models;

import java.util.Random;

public class Car {
    private String name;
    private int speed;
    private int handling;
    private int reliability;
    private int fuelEconomy;

    private final Random rng = new Random();

    public Car() {
        speed = rng.nextInt(1, 10);
        handling = rng.nextInt(1, 10);
        reliability = rng.nextInt(1, 10);
        fuelEconomy = rng.nextInt(1, 10);
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + "'" +
                ", speed=" + speed +
                ", handling=" + handling +
                ", reliability=" + reliability +
                ", fuelEconomy=" + fuelEconomy +
                '}';
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public int getHandling() {
        return handling;
    }

    public int getReliability() {
        return reliability;
    }

    public int getFuelEconomy() {
        return fuelEconomy;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setHandling(int handling) {
        this.handling = handling;
    }

    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    public void setFuelEconomy(int fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

}
