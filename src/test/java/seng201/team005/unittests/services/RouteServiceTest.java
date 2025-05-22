package seng201.team005.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.models.Route;
import seng201.team005.services.RouteService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RouteServiceTest {

    private RouteService routeService;

    @BeforeEach
    void setUp() {
        routeService = new RouteService();
    }

    @Test
    void generateRouteCreatesCorrectNumberOfRoute() {
        int count = 3;
        List<Route> routes = routeService.generateRoutes(count);
        assertEquals(count, routes.size());
    }

    @Test
    void selectRandomRouteTestReturnsNullForEmptyList() {
        Route route = routeService.selectRandomRoute(new ArrayList<>());
        assertNull(route);
    }

    @Test
    void selectRandomRouteTestReturnsRouteFromList() {
        List<Route> routes = routeService.generateRoutes(3);
        Route selectedRoute = routeService.selectRandomRoute(routes);
        assertNotNull(selectedRoute);
        assertTrue(routes.contains(selectedRoute), "Selected route should be in the list of routes");
    }
}
