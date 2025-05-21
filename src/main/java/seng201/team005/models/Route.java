package seng201.team005.models;

import java.util.List;
import java.util.Random;

/**
 * Model class representing a route in the game. Has specific attributes such as description,
 * distance, fuel stops, and difficulty.
 * <p>
 * A single race route is randomly generated when created. The difficulty
 * determines the other attributes (e.g., distance, number of fuel stops, and description).
 * </p>
 */
public class Route {
    // Description of the route (e.g., "Baby Land", "Dusty Trail", "Hell Bends").
    private final String description;

    // Distance of the route in kilometers.
    private final int distance;

    // Number of fuel stops available on the route.
    private final int fuelStops;

    // Difficulty level of the route (0 = easiest, 2 = hardest).
    private final int difficulty;

    private static final Random rng = new Random();

    /**
     * Constructs a randomly generated Route.
     * <p>
     * The difficulty is randomly chosen between 0 and 2, which determines the description,
     * distance, and number of fuel stops. The distance is calculated as a base value of 1000 km
     * plus an increment based on difficulty, with a random variation of ±200 km.
     * </p>
     */
    public Route(int difficulty) {
        // Set difficulty (0, 1, or 2).
        this.difficulty = difficulty;

        // List of possible route descriptions based on difficulty.
        List<String> descriptionList = List.of("Baby Land", "Dusty Trail", "Hell Bends");

        // Assign description based on difficulty.
        description = descriptionList.get(difficulty);

        // Calculate distance with a base value, difficulty increment, and random variation.
        distance = 1000 + 500 * (difficulty + 1) + rng.nextInt(-200, 201);

        // Calculate the number of fuel stops based on difficulty.
        fuelStops = 3 - difficulty;
    }

    public Route() {
        // Default constructor that generates a random difficulty for the route.
        this(rng.nextInt(3));
    }

    /**
     * Returns the route description.
     *
     * @return the route description (e.g., "Baby Land", "Dusty Trail", "Hell Bends").
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the route distance.
     *
     * @return the route distance in kilometers.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Returns the difficulty level of the route.
     *
     * @return the difficulty level (0 = easiest, 2 = hardest).
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the number of fuel stops on the route.
     *
     * @return the number of fuel stops (e.g., 3 for easiest, 1 for hardest).
     */
    public int getFuelStops() {
        return fuelStops;
    }
}