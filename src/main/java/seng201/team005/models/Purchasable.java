package seng201.team005.models;

import seng201.team005.services.MenuService;

import java.util.Random;

public abstract class Purchasable {

    protected String name;
    protected int speed;
    protected int handling;
    protected int reliability;
    protected int fuelEconomy;
    protected int overall;

    protected int buyValue;
    protected int sellValue;

    protected final int STAT_MIN;
    protected final int STAT_MAX;

    protected static final Random rng = new Random();

    public Purchasable(String name, int STAT_MIN, int STAT_MAX) {
        this.name = name;
        this.STAT_MIN = STAT_MIN;
        this.STAT_MAX = STAT_MAX;
        speed = rng.nextInt(STAT_MIN, STAT_MAX);
        handling = rng.nextInt(STAT_MIN, STAT_MAX);
        reliability = rng.nextInt(STAT_MIN, STAT_MAX);
        fuelEconomy = rng.nextInt(STAT_MIN, STAT_MAX);
        overall = recalculateOverallStats();
    }

    public int calculateOverall() {
        return (speed + handling + reliability + fuelEconomy) / 4;
    }

    public int recalculateOverallStats() {
        overall = calculateOverall();
        buyValue = overall > 0 ? overall : 1;
        sellValue = overall > 1 ? buyValue / 2 : 1;
        return overall;
    }

    public String shopString() {
        return name + "\n" + "$".repeat(buyValue);
    }

    public String garageString() {
        return name + "\n" + (overall > 0 ? MenuService.convertStatToStars(overall) : "...");
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

    public int getBuyValue() {
        return buyValue;
    }

    public int getSellValue() {
        return sellValue;
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

    public void setOverall(int overall) {
        this.overall = overall;
    }

    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }
}
