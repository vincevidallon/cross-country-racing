package seng201.team005.models;

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
    private String description;

    // Distance of the route in kilometers.
    private final int distance;

    // Number of fuel stops available on the route.
    private final int fuelStops;

    // Difficulty level of the route (0 = easiest, 2 = hardest).
    private final int difficulty;

    // Terrain type of the route (e.g., HILLY, WINDY, OFF_ROAD).
    private final Terrain terrain;

    // Random number generator for generating route attributes.
    private static final Random rng = new Random();

    /**
     * Constructs a randomly generated Route.
     * <p>
     * The difficulty is randomly chosen between 0 and 2, which determines the description,
     * distance, and number of fuel stops. The distance is calculated as a base value of 1000 km
     * plus an increment based on difficulty, with a random variation of ±200 km.
     * </p>
     *
     * @param difficulty The difficulty level of the route (0 = easiest, 2 = hardest).
     */
    public Route(int difficulty) {
        // Set difficulty (0, 1, or 2).
        this.difficulty = difficulty;

        this.terrain = Terrain.values()[rng.nextInt(Terrain.values().length)];

        // Assign description based on a random choice from the enum.
        switch (terrain) {
            case HILLY -> description = "Hilly";
            case WINDY -> description = "Windy";
            case OFF_ROAD -> description = "Off-road";
        }

        // Calculate distance with a base value, difficulty increment, and random variation.
        distance = 1000 + 500 * (difficulty + 1) + rng.nextInt(-200, 201);

        // Calculate the number of fuel stops based on difficulty.
        fuelStops = difficulty + 2;
    }

    /**
     * Constructs a Route with a randomly generated difficulty.
     * <p>
     * This constructor generates a random difficulty level (0 to 2) and initializes
     * the route attributes accordingly.
     * </p>
     */
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

    /**
     * Returns the terrain type of the route.
     *
     * @return the terrain type (e.g., HILLY, WINDY, OFF_ROAD).
     */
    public Terrain getTerrain() {
        return terrain;
    }

    /**
     * Enum representing the terrain types for a route.
     * <p>
     * The terrain type affects the description of the route and is randomly assigned
     * when a route is created. Possible values are:
     * <ul>
     *     <li>HILLY - Represents a hilly terrain.</li>
     *     <li>WINDY - Represents a windy terrain.</li>
     *     <li>OFF_ROAD - Represents an off-road terrain.</li>
     * </ul>
     */
    public enum Terrain {
        HILLY, WINDY, OFF_ROAD
    }
}