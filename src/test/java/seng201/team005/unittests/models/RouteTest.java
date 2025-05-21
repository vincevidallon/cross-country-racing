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
    void mediumDifficultyPrizeMoneyCalculation() {
        Race race = new Race(1);
        assertEquals(race.getEntries() + 2, race.getPrizeMoney(),
                "Prize money should be equal to entries + 2 for medium difficulty");
    }

    @RepeatedTest(10)
    void hardDifficultyPrizeMoneyCalculation() {
        Race race = new Race(2);
        assertEquals(race.getEntries() + 2, race.getPrizeMoney(),
                "Prize money should be equal to entries + 2 for hard difficulty");
    }

    @Test
    void invalidDifficultyDoesNotThrowException() {
        assertDoesNotThrow(() -> new Race(0),
                "Creating a Race with an invalid difficulty should not throw an exception");
    }


    @RepeatedTest(10)
    void maxDurationWithinExpectedRangeForMediumDifficulty() {
        Race race = new Race(1);
        assertTrue(race.getMaxDuration() >= 12 && race.getMaxDuration() < 18,
                "Max duration should be within the range 12 to 18 for medium difficulty");
    }

    @RepeatedTest(10)
    void maxDurationWithinExpectedRangeForHardDifficulty() {
        Race race = new Race(2);
        assertTrue(race.getMaxDuration() >= 16 && race.getMaxDuration() < 24,
                "Max duration should be within the range 16 to 24 for hard difficulty");
    }
}
