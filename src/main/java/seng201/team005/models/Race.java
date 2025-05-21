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
            case 1: // medium
                maxDuration = rng.nextInt(8, 16);
                entries = rng.nextInt(4, 9);
                break;
            case 2: // hard
                maxDuration = rng.nextInt(12, 21);
                entries = rng.nextInt(6, 13);
                break;
        }
        prizeMoney = entries + 2;
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
