package seng201.team005.services;

import seng201.team005.models.Race;

import java.util.ArrayList;
import java.util.List;

public class GenerateRaceService {

    public List<Race> generateRaces(int difficulty, int count) {
        List<Race> races = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            races.add(new Race(difficulty));
        }
        return races;
    }
}
