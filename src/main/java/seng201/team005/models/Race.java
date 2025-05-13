package seng201.team005.models;

import java.util.Random;

public class Race {
    private int hours;
    private int entries;
    private int routes;
    private int prizeMoney;

    public Race(int difficulty) {
        Random rng = new Random();
        switch (difficulty) {
            case 0: // easy
                hours = rng.nextInt(5, 10);
                entries = rng.nextInt(1, 5);
                routes = rng.nextInt(1, 3);
                prizeMoney = rng.nextInt(3, 6);
            case 1: // medium
                hours = rng.nextInt(10, 15);
                entries = rng.nextInt(3, 9);
                routes = rng.nextInt(1, 3);
                prizeMoney = rng.nextInt(6, 10);
            case 2: // hard
                hours = rng.nextInt(15, 21);
                entries = rng.nextInt(5, 13);
                routes = rng.nextInt(2, 4);
                prizeMoney = rng.nextInt(10, 16);
        }
    }

    public int getHours() {
        return hours;
    }

    public int getEntries() {
        return entries;
    }

    public int getRoutes() {
        return routes;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }
}
