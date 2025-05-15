package seng201.team005.services;

import seng201.team005.models.Car;
import seng201.team005.models.Entrant;
import seng201.team005.models.Race;

import java.util.*;

public class RaceService {
//    private final MenuRaceController raceController;
    private final Race race;
    private final Entrant playerEntrant;
    private List<Entrant> entrantList = new ArrayList<>();
    private int currentTime = 0;

    public RaceService(Race race, Entrant playerEntrant) {
//        this.raceController = raceController;
        this.race = race;
        this.playerEntrant = playerEntrant;
    }

    private void carBreakDownEvent(Entrant entrant) {

    }

    private void strandedTravelerEvent(Entrant entrant) {

    }

    private void severeWeatherEvent() {

    }

    public void randomEvent(Entrant entrant) {
        Random rng = new Random();
        int randomEventInt = rng.nextInt(1, 101);
        if (randomEventInt >= 98) {
            severeWeatherEvent();
        } else if (randomEventInt >= 95) {
            strandedTravelerEvent(entrant);
        } else if (randomEventInt >= 92) {
            carBreakDownEvent(entrant);
        }
    }

    public List<Entrant> getEntrantList() {
        return entrantList;
    }

    public Race getRace() {
        return race;
    }

    public void initEntrantList() {
        entrantList.add(playerEntrant);
        for (int i = 0; i < race.getEntries(); i++) {
            entrantList.add(new Entrant());
        }
    }

    public void timeStep() {
        for (Entrant entrant : entrantList) {
            int fuelDecrement = (15 - 2 * entrant.getFuelEconomy());

            entrant.addDistance(entrant.getSpeed());
            entrant.setFuel(entrant.getFuel() - fuelDecrement > 0 ? fuelDecrement : 1);
        }

        Collections.sort(entrantList);

        for (Entrant entrant: entrantList) {
            entrant.setPosition(entrantList.indexOf(entrant));
            randomEvent(entrant);
        }

        currentTime += 1;
        System.out.println("Hour " + currentTime);
        System.out.println(entrantList);
    }

    public static void main(String[] args) {
        RaceService raceService = new RaceService(new Race(1), new Entrant());
        System.out.println("Race: " + raceService.getRace());
        raceService.initEntrantList();
        raceService.timeStep();
        raceService.timeStep();
    }
}
