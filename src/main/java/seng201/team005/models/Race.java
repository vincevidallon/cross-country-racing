package seng201.team005.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a race with specific attributes such as duration, number of entries, prize money, and routes.
 * The race can be configured based on difficulty levels.
 */
public class Race {
    private int maxDuration; // The maximum duration of the race in hours.
    private int entries; // The number of entries in the race.
    private final int prizeMoney; // The prize money for the race.
    private final List<Route> routeList = new ArrayList<>(); // The list of routes available for the race.

    /**
     * Constructs a Race object based on the specified difficulty level.
     *
     * @param difficulty The difficulty level of the race (1 for medium, 2 for hard).
     */
    public Race(int difficulty) {
        Random rng = new Random();
        switch (difficulty) {
            case 1: // medium
                maxDuration = rng.nextInt(12, 18);
                entries = rng.nextInt(4, 9);
                break;
            case 2: // hard
                maxDuration = rng.nextInt(16, 24);
                entries = rng.nextInt(6, 13);
                break;
        }
        prizeMoney = entries + 2;
    }

    /**
     * Returns the maximum duration of the race.
     *
     * @return The maximum duration in hours.
     */
    public int getMaxDuration() {
        return maxDuration;
    }

    /**
     * Returns the number of entries in the race.
     *
     * @return The number of entries.
     */
    public int getEntries() {
        return entries;
    }

    /**
     * Returns the list of routes available for the race.
     *
     * @return A list of Route objects.
     */
    public List<Route> getRouteList() {
        return routeList;
    }

    /**
     * Returns the prize money for the race.
     *
     * @return The prize money amount.
     */
    public int getPrizeMoney() {
        return prizeMoney;
    }
}