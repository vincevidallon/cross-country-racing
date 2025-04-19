package seng201.team005;

import seng201.team005.gui.ScreenNavigator;
import seng201.team005.models.Car;

import java.util.List;

public class GameEnvironment {
    private String name;
    private int seasonLength;
    private int difficulty;
    private int money;
    private List<Car> playerCars = List.of();
    private final ScreenNavigator navigator;

    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchMenuSetupSettings(this);
    }

    public void onSetupComplete(String name, int seasonLength, int difficulty) {
        this.name = name;
        this.seasonLength = seasonLength;
        this.difficulty = difficulty;
        this.money = 10 / difficulty;
        navigator.launchMenuSetupCars(this);
    }

    public String getName() {
        return name;
    }

    public int getSeasonLength() {
        return seasonLength;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getMoney() {
        return money;
    }

    public List<Car> getPlayerCars() {
        return playerCars;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setPlayerCars(List<Car> playerCars) {
        this.playerCars = playerCars;
    }

    public void onQuitRequested() {
        System.exit(0);
    }

}
