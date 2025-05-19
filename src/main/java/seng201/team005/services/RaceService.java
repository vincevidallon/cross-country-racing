package seng201.team005.services;

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
    private final List<Entrant> entrantList = new ArrayList<>();
    private final List<Entrant> finalEntrantList = new ArrayList<>();
    private int currentTime = 0;

    private final Random rng = new Random();

    public static String positionString(int pos) {
        if (pos % 10 == 1 && pos != 11) return pos + "st place";
        if (pos % 10 == 2 && pos != 12) return pos + "nd place";
        if (pos % 10 == 3 && pos != 13) return pos + "rd place";
        return pos + "th place";
    }

    public enum RaceEvent {
        STRANDED_TRAVELER, FUEL_STOP
    }

    public List<Entrant> getEntrantList() {
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


    public RaceService(MenuRaceController raceController, Race race, Route route, Entrant playerEntrant) {
        this.raceController = raceController;
        this.race = race;
        this.route = route;
        this.playerEntrant = playerEntrant;
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
            raceController.onCurrentEvent(RaceEvent.STRANDED_TRAVELER);
        } else {
            sendBroadcast(entrant, entrant.getName() + " has stopped to pick up a stranded traveler!");
        }
    }

    public void strandedTravelerChoice(boolean stopping) {
        if (stopping) {
            playerEntrant.setStopped(true);
            raceController.addMoney(rng.nextInt(2, 5));
        }
        raceController.onCurrentEvent(null);
    }

    public void fuelStopEvent(Entrant entrant) {
        if (entrant.equals(playerEntrant)) {
            raceController.onCurrentEvent(RaceEvent.FUEL_STOP);
        } else {
            sendBroadcast(entrant, entrant.getName() + " has reached a fuel stop!");
        }
    }

    public void fuelStopChoice(boolean stopping) {
        if (stopping) {
            playerEntrant.setStopped(true);
            playerEntrant.setFuel(100);
        }
        raceController.onCurrentEvent(null);
    }

    private void severeWeatherEvent() {
        finalEntrantList.addAll(entrantList);
        entrantList.clear();
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
        if (rng.nextInt(100) >= 98) {
            severeWeatherEvent();
        }
    }

    public void initEntrantList() {
        entrantList.add(playerEntrant);
        for (int i = 0; i < race.getEntries(); i++) {
            entrantList.add(new Entrant());
        }
        System.out.println(entrantList);
    }

    public void timeStep() {

        for (Entrant entrant : entrantList) {
            if (!entrant.isFinished() && !entrant.isStopped() && !entrant.isBrokenDown()) {
                randomEvent(entrant);
                if (!entrant.isStopped() && !entrant.isBrokenDown()) {
                    int speedAdjust = 10 * entrant.getSpeed();
                    int reliabilityAdjust = rng.nextInt(-20, 21) / entrant.getReliability();
                    entrant.addDistance(50 + speedAdjust + reliabilityAdjust);

                    int fuelEconomyAdjust = 2 * entrant.getFuelEconomy();
                    int fuelAdjust = Math.max(fuelEconomyAdjust + reliabilityAdjust / 4, 0);
                    int fuelDecrement = Math.max(15 - fuelAdjust, 1);
                    entrant.setFuel(Math.max(entrant.getFuel() - fuelDecrement, 0));

                    if (entrant.getFuel() == 0) {
                        entrant.setBrokenDown(true);
                        sendBroadcast(entrant, entrant.getName() + " has ran out of fuel!");
                    }
                }
            }

            entrant.setStopped(false);
        }
        randomGlobalEvent();

        entrantList.sort(Collections.reverseOrder());

        for (Entrant entrant: entrantList) {
            entrant.setPosition(entrantList.indexOf(entrant) + 1);

            if (entrant.getDistance() > route.getDistance() && !entrant.isFinished()) {
                entrant.setFinished(true);
                finalEntrantList.add(entrant);
                entrant.setPosition(finalEntrantList.indexOf(entrant));
                sendBroadcast(entrant, entrant.getName() + " has completed the race at " +
                        positionString(entrant.getPosition()) + "!");
            }
        }

        currentTime++;
        System.out.println(entrantList);
    }


}
