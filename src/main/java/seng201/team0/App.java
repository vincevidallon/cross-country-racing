package seng201.team0;

import seng201.team0.gui.FXAppEntry;

/**
 * Default entry point class
 * @author seng202 teaching team
 */
public class App {

    /**
     * Entry point which runs the javaFX application
     * Due to how JavaFX works we must call FXAppEntry.launch() from here,
     * trying to run FXAppEntry itself will cause an error.
     *
     * @param args program arguments from command line
     */
    public static void main(String[] args) {
        FXAppEntry.launch(FXAppEntry.class, args);
    }
}
