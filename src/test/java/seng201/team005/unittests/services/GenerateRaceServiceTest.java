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
    void generateMediumRacesTest() {
        int difficulty = 2;
        int count = 5;
        List<Race> races = generateRaceService.generateRaces(difficulty, count);

        assertEquals(count, races.size(), "Race List should be matching with the count parameter");
        assertNotNull(races, "Races List should not be null");
    }

    @Test
    void generateHardRacesTest() {
        int difficulty = 3;
        int count = 7;
        List<Race> races = generateRaceService.generateRaces(difficulty, count);

        assertEquals(count, races.size(), "Race List should be matching with the count parameter");
        assertNotNull(races, "Races List should not be null");
    }
}
