package seng201.team0.gui;

import javafx.scene.control.ToggleButton;
import seng201.team0.GameEnvironment;

import java.util.List;

/**
 * Abstract parent class for all {@link GameEnvironment} UI controller classes.
 * @author seng201 teaching team
 */
public abstract class ScreenController {

    private final GameEnvironment gameEnvironment;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected ScreenController(final GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    protected abstract String getTitle();

    /**
     * Gets the game environment associated with this screen controller.
     * @return The game environment for this controller
     */
    protected GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    protected void buttonSelector(List<ToggleButton> buttonList, int buttonIndex) {
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setSelected(i == buttonIndex);
        }
    }
}

