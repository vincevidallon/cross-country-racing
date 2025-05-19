package seng201.team005.services;

import seng201.team005.models.Race;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for generating {@link Race} instances based on the selected game difficulty.
 *
 * @author vvi29
 */
public class GenerateRaceService {

    /**
     * Generates a list of {@link Race} objects with the specified difficulty level.
     * The number of races generated is determined by the {@code count} parameter.
     *
     * @param difficulty the difficulty level applied to each generated race
     * @param count the number of Race objects to be generated
     * @return a list of Race instances with the provided difficulty
     */
    public List<Race> generateRaces(int difficulty, int count) {
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            races.add(new Race(difficulty));
        }
        return races;
    }
}
