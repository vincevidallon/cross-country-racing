package seng201.team005.services;

import seng201.team005.models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RouteService {
    private final Random random = new Random();

    public List<Route> generateRoutes(int count) {
        List<Route> raceRoutes = new ArrayList<>();
        for (int i =0; i < count; i++) {
            raceRoutes.add(new Route());
        }
        return raceRoutes;
    }

    public Route selectRandomRoute(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            return null;
        }
        return routes.get(random.nextInt(routes.size()));
    }
}
