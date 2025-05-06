package seng201.team005.models;

import java.util.Random;

public class Car {
    private String name;
    private int speed;
    private int handling;
    private int reliability;
    private int fuelEconomy;
    private int overall;
    private int cost;

    private static final int STAT_MIN = 1;
    private static final int STAT_MAX = 101;

    private static final Random rng = new Random();

    public Car(String name) {
        this.name = name;
        speed = rng.nextInt(STAT_MIN, STAT_MAX);
        handling = rng.nextInt(STAT_MIN, STAT_MAX);
        reliability = rng.nextInt(STAT_MIN, STAT_MAX);
        fuelEconomy = rng.nextInt(STAT_MIN, STAT_MAX);
        overall = (speed + handling + reliability + fuelEconomy) / 4;
        cost = overall / 20 + 1;
    }

    public Car() {
        this("Car" + rng.nextInt(1, 10) + rng.nextInt(1, 10));
    }


    public String shopString() {
        return name + "\n" + "$".repeat(cost);
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

    public int getOverall() {
        return overall;
    }

    public int getCost() {
        return cost;
    }
}
