package seng201.team005.services;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seng201.team005.gui.MenuRaceController;
import seng201.team005.models.Car;
import seng201.team005.models.Entrant;
import seng201.team005.models.Race;
import seng201.team005.models.Route;

import java.util.Collections;
import java.util.Random;

/**
 * Service class for managing race-related functionality.
 * Handles race events, entrant management, and race progression logic.
 */
public class RaceService {
    // Reference to the controller managing the race UI.
    private final MenuRaceController raceController;
    // The race instance being managed.
    private final Race race;
    // The route on which the race is taking place.
    private final Route route;
    // The player's entrant in the race.
    private final Entrant playerEntrant;
    // List of all entrants participating in the race.
    private final ObservableList<Entrant> entrantList = FXCollections.observableArrayList();
    // Random number generator for event handling.
    private final Random rng = new Random();
    // Counter for the number of entrants who have finished the race.
    private int finishedEntrantCount = 0;
    // The current time step of the race.
    private int currentTime = 0;
    // The current event affecting the player's entrant.
    private RaceEvent currentPlayerEvent = null;

    /**
     * Constructs a RaceService with the specified controller, race, route, and player's car.
     *
     * @param raceController The controller managing the race UI.
     * @param race           The race instance being managed.
     * @param route          The route on which the race is taking place.
     * @param playerCar      The player's car participating in the race.
     */
    public RaceService(MenuRaceController raceController, Race race, Route route, Car playerCar) {
        this.raceController = raceController;
        this.race = race;
        this.route = route;
        this.playerEntrant = new Entrant(playerCar, route.getTerrain());
    }

    /**
     * @return The list of all entrants participating in the race.
     */
    public ObservableList<Entrant> getEntrantList() {
        return entrantList;
    }

    /**
     * @return The player's entrant in the race.
     */
    public Entrant getPlayer() {
        return playerEntrant;
    }

    /**
     * @return The current time step of the race.
     */
    public int getCurrentTime() {
        return currentTime;
    }

    /**
     * @return The race instance being managed.
     */
    public Race getRace() {
        return race;
    }

    /**
     * @return The route on which the race is taking place.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Calculates the prize money for the player based on their position.
     *
     * @return The prize money awarded to the player.
     */
    public int calculatePrizeMoney() {
        if (playerEntrant.getPosition() <= 3) {
            return race.getPrizeMoney() / playerEntrant.getPosition();
        } else return 0;
    }

    /**
     * Sets the current event affecting the player's entrant and notifies the controller.
     *
     * @param currentPlayerEvent The current event affecting the player.
     */
    public void setCurrentPlayerEvent(RaceEvent currentPlayerEvent) {
        this.currentPlayerEvent = currentPlayerEvent;
        raceController.onCurrentEvent(currentPlayerEvent);
    }

    /**
     * Sends a broadcast message about an event involving an entrant.
     *
     * @param entrant The entrant involved in the event.
     * @param message The message to broadcast.
     */
    private void sendBroadcast(Entrant entrant, String message) {
        if (entrant.equals(playerEntrant)) {
            raceController.displayEventBroadcast(message, "-fx-font-weight: bold");
        } else {
            raceController.displayEventBroadcast(message);
        }
    }

    /**
     * Handles the player's choice during a car breakdown event.
     *
     * @param entrant          The entrant experiencing the breakdown.
     * @param payingForRepairs Whether the entrant is paying for repairs.
     */
    public void carBreakDownChoice(Entrant entrant, boolean payingForRepairs) {
        if (payingForRepairs) {
            entrant.setStopped(true);
            sendBroadcast(entrant, entrant.getName() + " has stopped for repairs.");
        } else {
            entrant.setBrokenDown(true);
            sendBroadcast(entrant, entrant.getName() + " has broken down.");
        }
        if (entrant == playerEntrant) setCurrentPlayerEvent(null);
    }

    /**
     * Handles a car breakdown event for an entrant.
     *
     * @param entrant The entrant experiencing the breakdown.
     */
    private void carBreakDownEvent(Entrant entrant) {
        if (entrant.isFinished()) return;
        if (entrant.equals(playerEntrant)) {
            setCurrentPlayerEvent(RaceEvent.BROKEN_DOWN);
        } else {
            carBreakDownChoice(entrant, rng.nextBoolean());
        }
    }

    /**
     * Handles a stranded traveler event for an entrant.
     *
     * @param entrant The entrant encountering the stranded traveler.
     */
    private void strandedTravelerEvent(Entrant entrant) {
        if (entrant.equals(playerEntrant)) {
            setCurrentPlayerEvent(RaceEvent.STRANDED_TRAVELER);
        } else {
            strandedTravelerChoice(entrant, rng.nextBoolean());
        }
    }

    /**
     * Handles the player's choice during a stranded traveler event.
     *
     * @param entrant  The entrant encountering the traveler.
     * @param stopping Whether the entrant stops to help.
     */
    public void strandedTravelerChoice(Entrant entrant, boolean stopping) {
        if (stopping) {
            entrant.setStopped(true);
            sendBroadcast(entrant, entrant.getName() + " has stopped to pick up a stranded traveler!");

            if (entrant == playerEntrant && rng.nextBoolean()) {
                sendBroadcast(playerEntrant, "The traveler has given you some money as thanks!");
                raceController.addMoney(rng.nextInt(2, 5));
            }
        }
        if (entrant == playerEntrant) setCurrentPlayerEvent(null);
    }

    /**
     * Handles a fuel stop event for an entrant.
     *
     * @param entrant The entrant reaching the fuel stop.
     */
    public void fuelStopEvent(Entrant entrant) {
        if (entrant.equals(playerEntrant)) {
            setCurrentPlayerEvent(RaceEvent.FUEL_STOP);
        } else {
            fuelStopChoice(entrant, rng.nextBoolean());
        }
    }

    /**
     * Handles the player's choice during a fuel stop event.
     *
     * @param entrant  The entrant reaching the fuel stop.
     * @param stopping Whether the entrant stops to refuel.
     */
    public void fuelStopChoice(Entrant entrant, boolean stopping) {
        sendBroadcast(entrant, entrant.getName() + " has reached a fuel stop!");
        if (stopping) {
            entrant.setStopped(true);
            sendBroadcast(entrant, entrant.getName() + " is refueling!");
            entrant.setFuel(100);

            // Lets player refuel when reaching 0 fuel and a fuel stop at the same time.
            if (entrant == playerEntrant) playerEntrant.setBrokenDown(false);
        }
        if (entrant == playerEntrant) setCurrentPlayerEvent(null);
    }

    /**
     * Handles a severe weather event affecting all entrants.
     */
    private void severeWeatherEvent() {
        for (Entrant e : entrantList) {
            e.setBrokenDown(true);
        }
        sendBroadcast(playerEntrant, "A severe weather event has occurred on the current route.");
    }

    /**
     * Triggers a random event for a specific entrant.
     *
     * @param entrant The entrant for whom the event is triggered.
     */
    public void randomEvent(Entrant entrant) {
        int randomEventInt = rng.nextInt(100);
        if (randomEventInt < 2) {
            strandedTravelerEvent(entrant);
        } else if (randomEventInt < 10 - entrant.getReliability()) {
            carBreakDownEvent(entrant);
        }
    }

    /**
     * Triggers a random global event affecting all entrants.
     */
    public void randomGlobalEvent() {
        if (rng.nextInt(200) == 0) {
            severeWeatherEvent();
        }
    }

    /**
     * Initializes the list of entrants for the race.
     * Adds the player and generates AI entrants based on race difficulty.
     */
    public void initEntrantList() {
        entrantList.add(playerEntrant);
        playerEntrant.setPosition(1);
        for (int i = 1; i <= race.getEntries(); i++) {

            // Entrant difficulty scales with the number of races played.
            Entrant entrant = new Entrant(route.getTerrain(), 4 + raceController.getNumberOfRacesPlayed());
            entrantList.add(entrant);
            entrant.setPosition(i + 1);
        }
    }

    /**
     * Simulates a single step of driving for an entrant.
     * Updates their distance, fuel, and handles events like fuel stops.
     *
     * @param entrant The entrant taking the driving step.
     */
    public void driveStep(Entrant entrant) {
        int speedAdjust = 10 * entrant.getSpeed();
        int reliabilityAdjust = rng.nextInt(-20, 21) / entrant.getReliability();
        int handlingAdjust = 0;
        switch (route.getTerrain()) {
            case HILLY -> handlingAdjust = -20 + 2 * entrant.getHandling();
            case WINDY -> handlingAdjust = -10 + entrant.getHandling();
            case OFF_ROAD -> handlingAdjust = -30 + 3 * entrant.getHandling();
        }

        entrant.addDistance(50 + speedAdjust + reliabilityAdjust + handlingAdjust);
        if (entrant.getDistance() < route.getDistance() &&
                entrant.getDistance() >= route.getDistance() * (double) (entrant.getFuelStopsPassed() + 1) / route.getFuelStops() + 1) {
            fuelStopEvent(entrant);
            entrant.incrementFuelStopsPassed();
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

    /**
     * Simulates a single time step in the race.
     * Updates entrant states, triggers events, and checks for race completion.
     */
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

        entrantList.sort(Entrant::compareTo);
        entrantList.sort(Collections.reverseOrder());

        for (Entrant entrant: entrantList) {
            if (entrant.getDistance() > route.getDistance() && !entrant.isFinished()) {
                entrant.setFinished(true);
                finishedEntrantCount++;
                entrant.setPosition(finishedEntrantCount);
                sendBroadcast(entrant, entrant.getName() + " has completed the race at " +
                        entrant.positionString() + " place!");
            } else {
                entrant.setPosition(entrantList.indexOf(entrant) + 1);
            }
        }

        currentTime++;
        if (currentTime == race.getMaxDuration() || entrantList.stream().allMatch(entrant -> entrant.isBrokenDown() || entrant.isFinished())) {
            raceController.onEndReached();
            raceController.addMoney(calculatePrizeMoney());
        }
    }

    /**
     * Enum representing possible race events.
     */
    public enum RaceEvent {
        STRANDED_TRAVELER, FUEL_STOP, BROKEN_DOWN
    }
}