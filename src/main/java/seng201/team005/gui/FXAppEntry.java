package seng201.team005.gui;

import javafx.application.Application;
import javafx.stage.Stage;
import seng201.team005.GameEnvironment;

/**
 * Class that starts the JavaFX application thread.
 * @author seng201 teaching team
 */
public class FXAppEntry extends Application {

    /**
     * Creates the {@link GameEnvironment} with a {@link ScreenNavigator} for the given {@link Stage}
     * @param primaryStage The current fxml stage, handled by this JavaFX Application class
     */
    @Override
    public void start(Stage primaryStage) {
        new GameEnvironment(new ScreenNavigator(primaryStage));
    }
}
