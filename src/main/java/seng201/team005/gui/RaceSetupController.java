package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng201.team005.GameEnvironment;

public class RaceSetupController extends ScreenController {
    @FXML
    private Button exitToMenuButton;

    @FXML
    private Button raceButton1, raceButton2, raceButton3;

    public RaceSetupController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }


    @Override
    protected String getFxmlFile() {
        return "/fxml/race_setup.fxml";
    }


    @Override
    protected String getTitle() {
        return "Race Setup:";
    }

    // Handling the exit to menu button to return to main menu
    private void handleExitButton() {
        exitToMenuButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    @FXML
    public void initialize() {
        handleExitButton();
    }
}
