package seng201.team005;

import seng201.team005.gui.MenuSetupCarsController;
import seng201.team005.gui.MenuSetupSettingsController;
import seng201.team005.gui.ScreenController;
import seng201.team005.gui.ScreenNavigator;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Race;
import seng201.team005.models.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the game environment, managing the state and flow of the game.
 * <p>
 * This class handles the player's progress, game settings, and interactions
 * with various game components such as cars, races, and routes.
 * </p>
 */
public class GameEnvironment {
    // Navigator for managing screen transitions in the game.
    private final ScreenNavigator navigator;

    // List of parts owned by the player.
    private final List<Part> ownedParts = new ArrayList<>();

    // List of cars owned by the player.
    private final List<Car> ownedCars = new ArrayList<>();

    // Name of the player.
    private String name;

    // Length of the game season.
    private int seasonLength;

    // Difficulty level of the game.
    private int difficulty;

    // Amount of money the player has.
    private int money;

    // List of cars selected by the player for the current season.
    private List<Car> playerCars = List.of();

    // The car currently selected by the player.
    private Car selectedCar;

    // The race currently selected by the player.
    private Race selectedRace;

    // The route currently selected by the player.
    private Route selectedRoute;

    // The current season number.
    private int currentSeason = 1;

    /**
     * Constructs a GameEnvironment with the specified screen navigator.
     *
     * @param navigator the screen navigator for managing screen transitions.
     */
    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchScreen(new MenuSetupSettingsController(this));
    }

    /**
     * Completes the setup process by initializing game settings.
     *
     * @param name         the player's name.
     * @param seasonLength the length of the game season.
     * @param difficulty   the difficulty level of the game.
     */
    public void onSetupComplete(String name, int seasonLength, int difficulty) {
        this.name = name;
        this.seasonLength = seasonLength;
        this.difficulty = difficulty;
        this.money = 9 - 3 * difficulty;
        navigator.launchScreen(new MenuSetupCarsController(this));
    }

    /**
     * Launches a new screen in the game.
     *
     * @param screenController the screen controller to be launched.
     */
    public void launchScreen(ScreenController screenController) {
        navigator.launchScreen(screenController);
    }

    /**
     * Returns the player's name.
     *
     * @return the player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the length of the game season.
     *
     * @return the season length.
     */
    public int getSeasonLength() {
        return seasonLength;
    }

    /**
     * Returns the difficulty level of the game.
     *
     * @return the difficulty level.
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the amount of money the player has.
     *
     * @return the player's money.
     */
    public int getMoney() {
        return money;
    }

    /**
     * Sets the amount of money the player has.
     *
     * @param money the new amount of money.
     */
    public void setMoney(int money) {
        this.money = money;
    }

    /**
     * Returns the list of cars selected by the player for the current season.
     *
     * @return the list of player cars.
     */
    public List<Car> getPlayerCars() {
        return playerCars;
    }

    /**
     * Sets the list of cars selected by the player for the current season.
     *
     * @param playerCars the new list of player cars.
     */
    public void setPlayerCars(List<Car> playerCars) {
        this.playerCars = playerCars;
    }

    /**
     * Returns the car currently selected by the player.
     *
     * @return the selected car.
     */
    public Car getSelectedCar() {
        return selectedCar;
    }

    /**
     * Sets the car currently selected by the player.
     *
     * @param selectedCar the new selected car.
     */
    public void setSelectedCar(Car selectedCar) {
        this.selectedCar = selectedCar;
    }

    /**
     * Returns the list of parts owned by the player.
     *
     * @return the list of owned parts.
     */
    public List<Part> getOwnedParts() {
        return ownedParts;
    }

    /**
     * Returns the list of cars owned by the player.
     *
     * @return the list of owned cars.
     */
    public List<Car> getOwnedCars() {
        return ownedCars;
    }

    /**
     * Returns the race currently selected by the player.
     *
     * @return the selected race.
     */
    public Race getSelectedRace() {
        return selectedRace;
    }

    /**
     * Sets the race currently selected by the player.
     *
     * @param selectedRace the new selected race.
     */
    public void setSelectedRace(Race selectedRace) {
        this.selectedRace = selectedRace;
    }

    /**
     * Returns the route currently selected by the player.
     *
     * @return the selected route.
     */
    public Route getSelectedRoute() {
        return selectedRoute;
    }

    /**
     * Sets the route currently selected by the player.
     *
     * @param selectedRoute the new selected route.
     */
    public void setSelectedRoute(Route selectedRoute) {
        this.selectedRoute = selectedRoute;
    }

    /**
     * Returns the current season number.
     *
     * @return the current season.
     */
    public int getCurrentSeason() {
        return currentSeason;
    }

    /**
     * Sets the current season number.
     *
     * @param currentSeason the new current season.
     */
    public void setCurrentSeason(int currentSeason) {
        this.currentSeason = currentSeason;
    }
}