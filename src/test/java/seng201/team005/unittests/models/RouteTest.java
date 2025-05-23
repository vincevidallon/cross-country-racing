package seng201.team005.unittests.models;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import seng201.team005.models.Race;
import seng201.team005.models.Route;

import java.util.List;
import java.util.Set;

public class RouteTest {

    @RepeatedTest(10)
    void testRouteConstructorSetsExpectedFields() {
        Route route = new Route(1); // medium difficulty

        assertNotNull(route.getDescription(), "Description should not be null");
        assertTrue(route.getDistance() >= 1000 + 500 * 2 - 200 &&
                        route.getDistance() <= 1000 + 500 * 2 + 200,
                "Distance should be within valid range for difficulty 1");
        assertEquals(3, route.getFuelStops(), "Fuel stops should be difficulty + 2");
        assertEquals(1, route.getDifficulty(), "Difficulty should be set correctly");
        assertNotNull(route.getTerrain(), "Terrain should not be null");
    }

    @RepeatedTest(10)
    void testDefaultConstructorGeneratesValidRoute() {
        Route route = new Route();

        assertNotNull(route.getDescription(), "Description should not be null");
        assertTrue(route.getDifficulty() >= 0 && route.getDifficulty() <= 2,
                "Default constructor should generate difficulty between 0 and 2");
        assertTrue(route.getFuelStops() >= 2 && route.getFuelStops() <= 4,
                "Fuel stops should be between 2 and 4 for default difficulty range");
        assertTrue(route.getDistance() >= 1000 + 500 - 200,
                "Distance should be above minimum possible value");
        assertTrue(route.getDistance() <= 1000 + 500 * 3 + 200,
                "Distance should be below maximum possible value");
        assertNotNull(route.getTerrain(), "Terrain should not be null");
    }

    @RepeatedTest(10)
    void testTerrainAffectsDescription() {
        boolean hasHilly = false, hasWindy = false, hasOffRoad = false;

        for (int i = 0; i < 50; i++) {
            Route route = new Route();
            switch (route.getTerrain()) {
                case HILLY -> hasHilly = true;
                case WINDY -> hasWindy = true;
                case OFF_ROAD -> hasOffRoad = true;
            }
        }

        assertTrue(hasHilly && hasWindy && hasOffRoad,
                "Route generation should cover all terrain types over multiple runs");
    }
}
