package seng201.team005.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race {
    private int hours;
    private int entries;
    private List<Route> routeList = List.of();
    private int prizeMoney;
    private Route route;

    public Race(int difficulty) {
        Random rng = new Random();
        switch (difficulty) {
            case 0: // easy
                hours = rng.nextInt(5, 10);
                entries = rng.nextInt(1, 5);
                for (int i = 0; i < rng.nextInt(1, 3); i++) {
                    routeList.add(new Route());
                }
                prizeMoney = rng.nextInt(3, 6);
            case 1: // medium
                hours = rng.nextInt(10, 15);
                entries = rng.nextInt(3, 9);
                for (int i = 0; i < rng.nextInt(1, 3); i++) {
                    routeList.add(new Route());
                }
                prizeMoney = rng.nextInt(6, 10);
            case 2: // hard
                hours = rng.nextInt(15, 21);
                entries = rng.nextInt(5, 13);
                for (int i = 0; i < rng.nextInt(2,4); i++) {
                    routeList.add(new Route());
                }
                prizeMoney = rng.nextInt(10, 16);
        }
    }

    public int getHours() {
        return hours;
    }

    public int getEntries() {
        return entries;
    }

    public List<Route> getRouteList() {
        return routeList;
    }

    public int getPrizeMoney() {
        return prizeMoney;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
