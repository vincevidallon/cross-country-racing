package seng201.team005.models;

import seng201.team005.services.MenuService;

import java.util.Random;

/**
 * An abstract class representing any item that can be purchased in the game, such as cars or parts.
 * Each purchasable item has performance stats, buy/sell values, and a name identifier.
 * <p>
 * The performance stats include speed, handling, reliability, and fuel economy. An overall score
 * is calculated based on these stats, which determines the item's buy and sell values.
 * </p>
 */
public abstract class Purchasable {

    // The name of the purchasable item.
    protected String name;
    // The speed stat of the item.
    protected int speed;
    // The handling stat of the item.
    protected int handling;
    // The reliability stat of the item.
    protected int reliability;
    // The fuel economy stat of the item.
    protected int fuelEconomy;
    // The overall performance stat of the item.
    protected int overall;

    // The value of the item when purchased.
    protected int buyValue;
    // The value of the item when sold.
    protected int sellValue;

    // The minimum bound for stat generation.
    protected final int STAT_MIN;
    // The maximum bound for stat generation.
    protected final int STAT_MAX;

    // A static random number generator for generating stats.
    protected static final Random rng = new Random();

    /**
     * Constructs a Purchasable item with a specified name and stat range.
     * Randomly generates performance stats within the given range and calculates
     * the overall stat, buy value, and sell value.
     *
     * @param name     The name of the item.
     * @param STAT_MIN The minimum bound (inclusive) for random stat generation.
     * @param STAT_MAX The maximum bound (exclusive) for random stat generation.
     */
    public Purchasable(String name, int STAT_MIN, int STAT_MAX) {
        this.name = name;
        this.STAT_MIN = STAT_MIN;
        this.STAT_MAX = STAT_MAX;
        speed = rng.nextInt(STAT_MIN, STAT_MAX);
        handling = rng.nextInt(STAT_MIN, STAT_MAX);
        reliability = rng.nextInt(STAT_MIN, STAT_MAX);
        fuelEconomy = rng.nextInt(STAT_MIN, STAT_MAX);
        recalculateOverallStats();
    }

    /**
     * Calculates the overall stat value as the average of the four main performance stats.
     *
     * @return The calculated overall stat value.
     */
    public int calculateOverall() {
        return (speed + handling + reliability + fuelEconomy) / 4;
    }

    /**
     * Recalculates the overall stat and updates the item's buy and sell values.
     * Ensures that the values are at least 1 to prevent zero-cost items.
     *
     * @return The recalculated overall stat value.
     */
    public int recalculateOverallStats() {
        overall = calculateOverall();
        buyValue = overall > 0 ? overall + 1 : 1;
        sellValue = overall > 1 ? buyValue / 2 : 1;
        return overall;
    }

    /**
     * Generates a string representation of the item for display in the shop UI.
     * Includes the item's name and a visual representation of its cost using dollar signs.
     *
     * @return A formatted string for shop display.
     */
    public String shopString() {
        return name + "\n" + "$".repeat(buyValue);
    }

    /**
     * Generates a string representation of the item for display in the garage UI.
     * Includes the item's name and a star rating based on its overall performance.
     *
     * @return A formatted string for garage display.
     */
    public String garageString() {
        return name + "\n" + (overall > 0 ? MenuService.convertStatToStars(overall) : "...");
    }

    /**
     * @return The name of the item.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The speed stat of the item.
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return The handling stat of the item.
     */
    public int getHandling() {
        return handling;
    }

    /**
     * @return The reliability stat of the item.
     */
    public int getReliability() {
        return reliability;
    }

    /**
     * @return The fuel economy stat of the item.
     */
    public int getFuelEconomy() {
        return fuelEconomy;
    }

    /**
     * @return The overall stat of the item.
     */
    public int getOverall() {
        return overall;
    }

    /**
     * @return The buy value of the item.
     */
    public int getBuyValue() {
        return buyValue;
    }

    /**
     * @return The sell value of the item.
     */
    public int getSellValue() {
        return sellValue;
    }

    /**
     * Sets the name of the item.
     *
     * @param name The new name of the item.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the speed stat of the item.
     *
     * @param speed The new speed stat.
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Sets the handling stat of the item.
     *
     * @param handling The new handling stat.
     */
    public void setHandling(int handling) {
        this.handling = handling;
    }

    /**
     * Sets the reliability stat of the item.
     *
     * @param reliability The new reliability stat.
     */
    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    /**
     * Sets the fuel economy stat of the item.
     *
     * @param fuelEconomy The new fuel economy stat.
     */
    public void setFuelEconomy(int fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

    /**
     * Sets the overall stat of the item.
     *
     * @param overall The new overall stat.
     */
    public void setOverall(int overall) {
        this.overall = overall;
    }

    /**
     * Sets the sell value of the item.
     *
     * @param sellValue The new sell value.
     */
    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }
}