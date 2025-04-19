package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import seng201.team005.GameEnvironment;

import java.util.List;

/**
 * Controller for the setup_main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuSetupSettingsController extends ScreenController {

    @FXML
    private TextField nameField;
    @FXML
    private Slider seasonLengthSlider;
    @FXML
    private ToggleButton easyDifficultyButton, normalDifficultyButton, hardDifficultyButton;
    private int difficulty = 1;

    public MenuSetupSettingsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_setup_settings.fxml";
    }

    @Override
    protected String getTitle() {
        return "Cross Country Racing | Setup";
    }

    @FXML
    private void onGoButtonClicked() {
        getGameEnvironment().onSetupComplete(nameField.getText().strip(), (int) seasonLengthSlider.getValue(), difficulty);
    }


    private void onDifficultyButtonSelected(List<ToggleButton> difficultyButtons, int difficultyIndex) {
        difficulty = difficultyIndex;
        buttonSelector(difficultyButtons, difficultyIndex);
    }

    public void initialize() {
        List<ToggleButton> difficultyButtons = List.of(easyDifficultyButton, normalDifficultyButton, hardDifficultyButton);

        for (int i = 0; i < difficultyButtons.size(); i++) {
            int difficultyIndex = i;
            difficultyButtons.get(i).setOnAction(event -> onDifficultyButtonSelected(difficultyButtons, difficultyIndex));
        }
    }
}
