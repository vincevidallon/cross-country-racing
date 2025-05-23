package seng201.team005.unittests.models;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import seng201.team005.models.Race;
import seng201.team005.models.Route;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class RaceModelTest {

    @RepeatedTest(10)
    void mediumDifficultyPrizeMoneyCalculation() {
        Race race = new Race(1);
        assertEquals(race.getEntries(), race.getPrizeMoney(),
                "Prize money should be equal to entries for medium difficulty");
    }

    @RepeatedTest(10)
    void hardDifficultyPrizeMoneyCalculation() {
        Race race = new Race(2);
        assertEquals(race.getEntries(), race.getPrizeMoney(),
                "Prize money should be equal to entries for hard difficulty");
    }

    @Test
    void invalidDifficultyDoesNotThrowException() {
        assertDoesNotThrow(() -> new Race(0),
                "Creating a Race with an invalid difficulty should not throw an exception");
    }


    @RepeatedTest(10)
    void maxDurationWithinExpectedRangeForMediumDifficulty() {
        Race race = new Race(1);
        assertTrue(race.getMaxDuration() >= 15 && race.getMaxDuration() < 20,
                "Max duration should be within the range 12 to 18 for medium difficulty");
    }

    @RepeatedTest(10)
    void maxDurationWithinExpectedRangeForHardDifficulty() {
        Race race = new Race(2);
        assertTrue(race.getMaxDuration() >= 20 && race.getMaxDuration() < 25,
                "Max duration should be within the range 16 to 24 for hard difficulty");
    }

    @Test
    void getRouteListReturnsCorrectList() {
        Race testerRace = new Race(1);

        List<Route> raceRoutes = testerRace.getRouteList();

        assertNotNull(raceRoutes, "The route list should not be null");
        assertEquals(3, raceRoutes.size(), "Exactly 3 routes should be generated");

        for (Route route : raceRoutes) {
            assertNotNull(route, "Each route in route list should not be null");
            assertInstanceOf(Route.class, route, "Each list element should be a Route instance");
        }
    }
}


