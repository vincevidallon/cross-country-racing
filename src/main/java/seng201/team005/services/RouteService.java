package seng201.team005.services;

import seng201.team005.models.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A service class which is responsible for the generation and selection of race routes.
 *
 * @author vvi29
 */
public class RouteService {
    private final Random random = new Random();

    /**
     * Generates a list of {@link Route} objects.
     * @param count the number of Route objects to be generated
     * @return a list of Route instances
     */
    public List<Route> generateRoutes(int count) {
        List<Route> raceRoutes = new ArrayList<>();
        for (int i =0; i < count; i++) {
            raceRoutes.add(new Route());
        }
        return raceRoutes;
    }

    /**
     * Selects a random {@link Route} from the provided list of routes.
     * @param routes a list of available race routes
     * @return a randomly chosen Route, or {@code null} if the provided list is empty or null
     */
    public Route selectRandomRoute(List<Route> routes) {
        if (routes == null || routes.isEmpty()) {
            return null;
        }
        return routes.get(random.nextInt(routes.size()));
    }
}
