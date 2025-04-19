package seng201.team0.models;

import java.util.Random;

public class Car {
    private String name;
    private int speed;
    private int handling;
    private int reliability;
    private int fuelEconomy;
    private int overall;

    private static final int STAT_MIN = 1;
    private static final int STAT_MAX = 101;

    private final Random rng = new Random();

    public Car() {
        name = "Car" + rng.nextInt(9) + rng.nextInt(9);
        speed = rng.nextInt(STAT_MIN, STAT_MAX);
        handling = rng.nextInt(STAT_MIN, STAT_MAX);
        reliability = rng.nextInt(STAT_MIN, STAT_MAX);
        fuelEconomy = rng.nextInt(STAT_MIN, STAT_MAX);
        overall = (speed + handling + reliability + fuelEconomy) / 4;
    }

    public Car(String name) {
        this.name = name;
        speed = rng.nextInt(STAT_MIN, STAT_MAX);
        handling = rng.nextInt(STAT_MIN, STAT_MAX);
        reliability = rng.nextInt(STAT_MIN, STAT_MAX);
        fuelEconomy = rng.nextInt(STAT_MIN, STAT_MAX);
        overall = (speed + handling + reliability + fuelEconomy) / 4;
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

    public int getOverall() { return overall; }

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

    public void setOverall(int overall) { this.overall = overall; }
}
