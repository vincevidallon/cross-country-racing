package seng201.team0.models;

import java.util.Random;

public class Car {
    private String name;
    private double speed;
    private double handling;
    private double reliability;
    private double fuelEconomy;

    private final Random rng = new Random();

    public Car() {
        name = "Car";
        speed = (double) rng.nextInt(1, 10) / 10;
        handling = (double) rng.nextInt(1, 10) / 10;
        reliability = (double) rng.nextInt(1, 10) / 10;
        fuelEconomy = (double) rng.nextInt(1, 10) / 10;
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

    public double getSpeed() {
        return speed;
    }

    public double getHandling() {
        return handling;
    }

    public double getReliability() {
        return reliability;
    }

    public double getFuelEconomy() {
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
