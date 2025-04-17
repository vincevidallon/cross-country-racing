package seng201.team0;

import seng201.team0.gui.ScreenNavigator;

public class GameEnvironment {
    private String name;
    private int seasonLength;
    private int difficulty;
    private final ScreenNavigator navigator;

    public GameEnvironment(ScreenNavigator navigator) {
        this.navigator = navigator;
        navigator.launchMenuSetup(this);
    }

    public void onSetupComplete(String name, int seasonLength, int difficulty) {
        this.name = name;
        this.seasonLength = seasonLength;
        this.difficulty = difficulty;
        navigator.launchMenuMain(this);
    }

    public String getName() { return name; }

    public int getSeasonLength() { return seasonLength; }

    public int getDifficulty() { return difficulty; }

    public void onQuitRequested() {
        System.exit(0);
    }

}
