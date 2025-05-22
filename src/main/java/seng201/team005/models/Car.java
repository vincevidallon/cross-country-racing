package seng201.team005.models;

import seng201.team005.services.MenuService;

/**
 * Represents a car in the game, which is a purchasable item with various stats.
 * Extends the Purchasable class to inherit common properties and methods for items that can be bought or sold.
 */
public class Car extends Purchasable {

    /**
     * Constructs a Car instance with a specified stat cap.
     * The car's name is generated randomly, and its stats are initialized based on the provided stat cap.
     *
     * @param statCap The maximum value for the car's stats.
     */
    public Car(int statCap) {
        super("Car" + rng.nextInt(1, 10) + rng.nextInt(1, 10), 1, statCap);
    }

    /**
     * Constructs a Car instance with a default stat cap of 6.
     */
    public Car() {
        this(6);
    }

    /**
     * Generates a string representation of the car for display in the main menu.
     * Includes the car's name and its overall stats as a star-based representation.
     *
     * @return A string representing the car in the main menu format.
     */
    public String mainMenuString() {
        return name + " (" + (overall > 0 ? MenuService.convertStatToStars(overall) : "...") + ")";
    }
}