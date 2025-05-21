package seng201.team005.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng201.team005.gui.MenuRaceController;
import seng201.team005.models.Entrant;
import seng201.team005.models.Race;
import seng201.team005.models.Route;

import java.util.*;

public class RaceService {
    private final MenuRaceController raceController;
    private final Race race;
    private final Route route;
    private final Entrant playerEntrant;
    private final ObservableList<Entrant> entrantList = FXCollections.observableArrayList();
    private final List<Entrant> finalEntrantList = new ArrayList<>();
    private int currentTime = 0;
    private RaceEvent currentPlayerEvent = null;

    private final Random rng = new Random();

    public enum RaceEvent {
        STRANDED_TRAVELER, FUEL_STOP
    }

    public ObservableList<Entrant> getEntrantList() {
        return entrantList;
    }

    public Entrant getPlayer() {
        return playerEntrant;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public Race getRace() {
        return race;
    }

    public Route getRoute() {
        return route;
    }

    public RaceService(MenuRaceController raceController, Race race, Route route, Entrant playerEntrant) {
        this.raceController = raceController;
        this.race = race;
        this.route = route;
        this.playerEntrant = playerEntrant;
    }

    public void setCurrentPlayerEvent(RaceEvent currentPlayerEvent) {
        this.currentPlayerEvent = currentPlayerEvent;
        raceController.onCurrentEvent(currentPlayerEvent);
    }

    private void sendBroadcast(Entrant entrant, String message) {
        if (entrant.equals(playerEntrant)) {
            raceController.displayEventBroadcast(message, "-fx-font-weight: bold");
        } else {
            raceController.displayEventBroadcast(message);
        }
    }

    private void carBreakDownEvent(Entrant entrant) {
        entrant.setBrokenDown(true);
        sendBroadcast(entrant, entrant.getName() + " has broken down.");
    }

    private void strandedTravelerEvent(Entrant entrant) {
        if (entrant.equals(playerEntrant)) {
            setCurrentPlayerEvent(RaceEvent.STRANDED_TRAVELER);
        } else {
            strandedTravelerChoice(entrant, rng.nextBoolean());
        }
    }

    public void strandedTravelerChoice(Entrant entrant, boolean stopping) {
        if (stopping) {
            entrant.setStopped(true);
            sendBroadcast(entrant, entrant.getName() + " has stopped to pick up a stranded traveler!");

            if (entrant == playerEntrant) raceController.addMoney(rng.nextInt(2, 5));
        }
        if (entrant == playerEntrant) setCurrentPlayerEvent(null);
    }

    public void fuelStopEvent(Entrant entrant) {
        if (entrant.equals(playerEntrant)) {
            setCurrentPlayerEvent(RaceEvent.FUEL_STOP);
        } else {
            fuelStopChoice(entrant, rng.nextBoolean());
        }
    }

    public void fuelStopChoice(Entrant entrant, boolean stopping) {
        sendBroadcast(entrant, entrant.getName() + " has reached a fuel stop!");
        if (stopping) {
            entrant.setStopped(true);
            sendBroadcast(entrant, entrant.getName() + " is refueling!");
            entrant.setFuel(100);

            // lets player refuel when reaching 0 fuel and a fuel stop at the same time
            if (entrant == playerEntrant) playerEntrant.setBrokenDown(false);
        }
        if (entrant == playerEntrant) setCurrentPlayerEvent(null);
    }

    private void severeWeatherEvent() {
        for (Entrant e : entrantList) {
            e.setBrokenDown(true);
        }
        sendBroadcast(playerEntrant, "A severe weather event has occurred on the current route.");
    }

    public void randomEvent(Entrant entrant) {
        int randomEventInt = rng.nextInt(100);
        if (randomEventInt >= 98) {
            strandedTravelerEvent(entrant);
        } else if (randomEventInt >= 95) {
            carBreakDownEvent(entrant);
        }
    }

    public void randomGlobalEvent() {
        if (rng.nextInt(200) == 199) {
            severeWeatherEvent();
        }
    }

    public void initEntrantList() {
        entrantList.add(playerEntrant);
        playerEntrant.setPosition(1);
        for (int i = 1; i <= race.getEntries(); i++) {
            Entrant entrant = new Entrant();
            entrantList.add(entrant);
            entrant.setPosition(i + 1);
        }
    }

    public void driveStep(Entrant entrant) {
        int speedAdjust = 10 * entrant.getSpeed();
        int reliabilityAdjust = rng.nextInt(-20, 21) / entrant.getReliability();
        entrant.addDistance(50 + speedAdjust + reliabilityAdjust);
        if (entrant.getDistance() >= route.getDistance() / route.getFuelStops()) {
            fuelStopEvent(entrant);
        }

        int fuelEconomyAdjust = 2 * entrant.getFuelEconomy();
        int fuelAdjust = Math.max(fuelEconomyAdjust + reliabilityAdjust / 4, 0);
        int fuelDecrement = Math.max(15 - fuelAdjust, 1);
        entrant.setFuel(Math.max(entrant.getFuel() - fuelDecrement, 0));

        if (entrant.getFuel() == 0) {
            entrant.setBrokenDown(true);
            sendBroadcast(entrant, entrant.getName() + " has ran out of fuel!");
        }
    }

    public void timeStep() {


        for (Entrant entrant : entrantList) {

            if (!entrant.isFinished() && !entrant.isStopped() && !entrant.isBrokenDown()) {

                randomEvent(entrant);

                if ((entrant == playerEntrant && currentPlayerEvent != null) || !entrant.isStopped() && !entrant.isBrokenDown()) {
                    driveStep(entrant);
                }
            }

            entrant.setStopped(false);
        }
        randomGlobalEvent();

        entrantList.sort(Collections.reverseOrder());

        for (Entrant entrant: entrantList) {
            if (entrant.getDistance() > route.getDistance() && !entrant.isFinished()) {
                entrant.setFinished(true);
                finalEntrantList.add(entrant);
                entrant.setPosition(finalEntrantList.indexOf(entrant) + 1);
                sendBroadcast(entrant, entrant.getName() + " has completed the race at " +
                        entrant.positionString() + " place!");
            } else {
                entrant.setPosition(entrantList.indexOf(entrant) + 1);
            }
        }

        currentTime++;
        if (currentTime == race.getMaxDuration() || entrantList.stream().allMatch(Entrant::isFinished)) {
            endRace();
        }
    }

    private void endRace() {
        raceController.onEndReached();
    }
}
