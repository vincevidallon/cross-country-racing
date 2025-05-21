package seng201.team005.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.models.Race;
import seng201.team005.services.GenerateRaceService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateRaceServiceTest {

    private GenerateRaceService generateRaceService;

    @BeforeEach
    void setUp() {
        generateRaceService = new GenerateRaceService();
    }

    @Test
    void generateRacesWithZeroCount() {
        int difficulty = 1;
        int count = 0;
        List<Race> races = generateRaceService.generateRaces(difficulty, count);

        assertTrue(races.isEmpty(), "Race List should be empty when count is zero");
    }

    @Test
    void generateRacesWithNegativeCount() {
        int difficulty = 1;
        int count = -5;
        List<Race> races = generateRaceService.generateRaces(difficulty, count);

        assertTrue(races.isEmpty(), "Race List should be empty when count is negative");
    }

    @Test
    void generateRacesWithInvalidDifficulty() {
        int difficulty = -1;
        int count = 3;
        List<Race> races = generateRaceService.generateRaces(difficulty, count);

        assertEquals(count, races.size(), "Race List should still match the count parameter even with invalid difficulty");
        assertNotNull(races, "Races List should not be null");
    }

    @Test
    void generateRacesWithLargeCount() {
        int difficulty = 1;
        int count = 1000;
        List<Race> races = generateRaceService.generateRaces(difficulty, count);

        assertEquals(count, races.size(), "Race List should match the large count parameter");
        assertNotNull(races, "Races List should not be null");
    }
}
