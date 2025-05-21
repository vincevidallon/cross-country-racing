package seng201.team005.models;

import seng201.team005.services.MenuService;

import java.util.Random;

/**
 * An abstract class which represents any item that can be purchased
 * in the game (e.g cars or parts). All items purchasable by the player
 * have performance stats, buy/sell values and name identifiers.
 * <p>
 *     Stats for a purchasable item include the speed, handling, reliability and fuel economy. An overall
 *     scored for the item is derived based on these stats, which then determines the item's
 *     buy/sell value.
 * </p>
 *
 * @author vvi29
 */
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


    /**
     * Constructs a {@code Purchasable} item with a specified name and stat range.
     * All performance stats are randomly generated within the specified range.
     * The overall item stat and buy/sell values are computed based on these stats.
     *
     * @param name the name of the item
     * @param STAT_MIN minimum bound (inclusive) for random stat generation
     * @param STAT_MAX maximum bound (exclusive) for random stat generation
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
     * Calculates the overall stat value, defined as the average of the four main
     * performance stats.
     *
     * @return the overall stat value
     */
    public int calculateOverall() {
        return (speed + handling + reliability + fuelEconomy) / 4;
    }


    /**
     * Recalculates the overall stats, and then updates the item's buy/sell values.
     * Method ensures that values at least 1, prevents zero-cost/zero-value items.
     *
     * @return recalculated overall value
     */
    public int recalculateOverallStats() {
        overall = calculateOverall();
        buyValue = overall > 0 ? overall : 1;
        sellValue = overall > 1 ? buyValue / 2 : 1;
        return overall;
    }


    /**
     * Returns a string representation of the item in the shop UI.
     * Includes the name of the item and a representation of the cost
     * using dollar signs.
     * @return a formatted string for shop display
     */
    public String shopString() {
        return name + "\n" + "$".repeat(buyValue);
    }


    /**
     * Returns a string representation of item for use in garage UI.
     * Includes the name of the item and a star representation rating
     * of the overall performance.
     *
     * @return a formatted string for garage display
     */
    public String garageString() {
        return name + "\n" + (overall > 0 ? MenuService.convertStatToStars(overall) : "...");
    }

    /**
     * @return the item name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the item's speed stat
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * @return the item's handling stat
     */
    public int getHandling() {
        return handling;
    }

    /**
     * @return the item's reliability stat
     */
    public int getReliability() {
        return reliability;
    }

    /**
     * @return the item's fuel economy stat
     */
    public int getFuelEconomy() {
        return fuelEconomy;
    }

    /**
     * @return the item's overall stat
     */
    public int getOverall() {
        return overall;
    }


    /**
     * @return the item's buy value
     */
    public int getBuyValue() {
        return buyValue;
    }

    /**
     * @return the item's sell value
     */
    public int getSellValue() {
        return sellValue;
    }

    /**
     * @param name sets the item name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param speed sets the item speed stat
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * @param handling sets the item handling stat
     */
    public void setHandling(int handling) {
        this.handling = handling;
    }

    /**
     * @param reliability sets the item reliability stat
     */
    public void setReliability(int reliability) {
        this.reliability = reliability;
    }

    /**
     * @param fuelEconomy sets the item fuel economy stat
     */
    public void setFuelEconomy(int fuelEconomy) {
        this.fuelEconomy = fuelEconomy;
    }

    /**
     * @param overall sets the item overall stat
     */
    public void setOverall(int overall) {
        this.overall = overall;
    }

    /**
     * @param sellValue sets the item sell value
     */
    public void setSellValue(int sellValue) {
        this.sellValue = sellValue;
    }
}
