package seng201.team005.unittests.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import seng201.team005.models.Route;
import java.util.Set;

public class RouteTest {
    @Test
    void testRouteAttributesMatchDifficulty() {
        // Run multiple times to cover all random possibilities
        boolean[] found = new boolean[3];
        for (int i = 0; i < 100; i++) {
            Route route = new Route();
            int difficulty = route.getDifficulty();
            found[difficulty] = true;
            String desc = route.getDescription();
            int distance = route.getDistance();
            int fuelStops = route.getFuelStops();
            if (difficulty == 0) {
                assertEquals("Baby Land", desc);
                assertTrue(distance >= 1300 && distance <= 1700);
                assertEquals(3, fuelStops);
            } else if (difficulty == 1) {
                assertEquals("Dusty Trail", desc);
                assertTrue(distance >= 1800 && distance <= 2200);
                assertEquals(2, fuelStops);
            } else if (difficulty == 2) {
                assertEquals("Hell Bends", desc);
                assertTrue(distance >= 2300 && distance <= 2700);
                assertEquals(1, fuelStops);
            } else {
                fail("Difficulty out of range: " + difficulty);
            }
        }
        // Ensure all difficulties were generated
        for (int i = 0; i < 3; i++) {
            assertTrue(found[i], "Difficulty " + i + " was not generated");
        }
    }

    @Test
    void testGettersReturnConsistentValues() {
        Route route = new Route();
        assertEquals(route.getDescription(), route.getDescription());
        assertEquals(route.getDistance(), route.getDistance());
        assertEquals(route.getDifficulty(), route.getDifficulty());
        assertEquals(route.getFuelStops(), route.getFuelStops());
    }
}
