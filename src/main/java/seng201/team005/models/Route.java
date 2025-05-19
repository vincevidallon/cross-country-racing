package seng201.team005.models;

import java.util.List;
import java.util.Random;

public class Route {
    private String description;
    private int distance;
    private int fuelStops;
    private int difficulty;

    public Route() {
        Random rng = new Random();

        difficulty = rng.nextInt(3);

        List<String> descriptionList = List.of("Baby Land", "Dusty Trail", "Hell Bends");
        description = descriptionList.get(difficulty);
        distance = 1000 + 500 * (difficulty + 1) + rng.nextInt(-200, 201);
        fuelStops = 3 - difficulty;
    }

    @Override
    public String toString() {
        return "Route{" +
                "description='" + description + '\'' +
                ", distance=" + distance +
                ", fuelStops=" + fuelStops +
                ", difficulty=" + difficulty +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public int getDistance() {
        return distance;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getFuelStops() {
        return fuelStops;
    }
}
