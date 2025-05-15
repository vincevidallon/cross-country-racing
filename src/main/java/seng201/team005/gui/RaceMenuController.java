package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng201.team005.GameEnvironment;
import java.util.List;

/**
 * Controller class for race menu screen
 *
 * @author vvi29
 */

public class RaceMenuController extends ScreenController {

    @FXML
    private Button race1Button, race2Button, race3Button;

    @FXML
    private Button exitToMenuButton, confirmRaceButton;

    private List<Button> raceButtons = List.of();

    public RaceMenuController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/race_setup.fxml";
    }

    @Override
    protected String getTitle() {
        return "Race Menu";
    }

    public void initialize() {

        exitToMenuButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
        // confirmRaceButton.setOnAction(event -> getGameEnvironment().launchScreen(new ...)
    }
}
