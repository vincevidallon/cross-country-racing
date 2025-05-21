package seng201.team005.models;

import java.util.List;
import java.util.Random;


/**
 * Model class representing a route in the game. Has specific attributes such as description,
 * distance, fuel stops and difficulty.
 *<p>
 *     A single race route is randomly generated when created. The difficulty
 *     determines the other attributes (e.g distance, number of fuel stops, and description).
 *</p>
 *
 */
public class Route {
    private final String description;
    private final int distance, fuelStops, difficulty;


    /**
     * Constructs a randomly generated Route.
     */
    public Route() {
        Random rng = new Random();

        difficulty = rng.nextInt(3);

        List<String> descriptionList = List.of("Baby Land", "Dusty Trail", "Hell Bends");
        description = descriptionList.get(difficulty);
        distance = 1000 + 500 * (difficulty + 1) + rng.nextInt(-200, 201);
        fuelStops = 3 - difficulty;
    }

    /**
     * Returns the route description.
     *
     * @return the route description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Return the route distance.
     *
     * @return the route distance in km
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Returns the difficult level of the route.
     *
     * @return difficulty ()
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the number of fuel stops on the route
     * @return number of fuel stops
     */
    public int getFuelStops() {
        return fuelStops;
    }
}
