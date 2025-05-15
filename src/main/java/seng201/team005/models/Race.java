package seng201.team005.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Race {
    private int maxDuration;
    private int entries;
    private int prizeMoney;
    private List<Route> routeList = new ArrayList<>();
    private Route selectedRoute;

    public Race(int difficulty) {
        Random rng = new Random();
        switch (difficulty) {
            case 0: // easy
                maxDuration = rng.nextInt(5, 10);
                entries = rng.nextInt(1, 5);
                prizeMoney = rng.nextInt(3, 6);

                for (int i = 0; i < rng.nextInt(1, 3); i++) {
                    routeList.add(new Route());
                }
                break;
            case 1: // medium
                maxDuration = rng.nextInt(10, 15);
                entries = rng.nextInt(3, 9);
                prizeMoney = rng.nextInt(6, 10);

                for (int i = 0; i < rng.nextInt(1, 3); i++) {
                    routeList.add(new Route());
                }
                break;
            case 2: // hard
                maxDuration = rng.nextInt(15, 21);
                entries = rng.nextInt(5, 13);
                prizeMoney = rng.nextInt(10, 16);

                for (int i = 0; i < rng.nextInt(2,4); i++) {
                    routeList.add(new Route());
                }
        }
    }

    @Override
    public String toString() {
        return "Race{" +
                "maxDuration=" + maxDuration +
                ", entries=" + entries +
                ", prizeMoney=" + prizeMoney +
                ", routeList=" + routeList +
                ", selectedRoute=" + selectedRoute +
                '}';
    }

    public int getMaxDuration() {
        return maxDuration;
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

    public Route getSelectedRoute() {
        return selectedRoute;
    }

    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }
}
