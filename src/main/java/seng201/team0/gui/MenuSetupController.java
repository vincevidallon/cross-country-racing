package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import seng201.team0.GameEnvironment;

import java.util.List;

/**
 * Controller for the main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuSetupController extends ScreenController {

    public MenuSetupController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_setup.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing | Setup"; }

    @FXML
    private TextField nameField;

    @FXML
    private Slider seasonLengthSlider;

    @FXML
    private ToggleButton easyDifficultyButton, normalDifficultyButton, hardDifficultyButton;

    private int difficulty = -1;

    private void onDifficultyButtonSelected(List<ToggleButton> difficultyButtons, int difficultyIndex) {
        difficulty = difficultyIndex;
        for (int i = 0; i < difficultyButtons.size(); i++) {
            difficultyButtons.get(i).setSelected(i == difficultyIndex);
        }
    }

    @FXML
    private void onGoButtonClicked() {
        System.out.printf("Name: %s\nSeason Length: %s\nDifficulty: %s\nLet's GO!\n",
                nameField.getText().isBlank() ? "Anonymous" : nameField.getText().strip(), (int) seasonLengthSlider.getValue(), difficulty);

        getGameEnvironment().onSetupComplete(nameField.getText().strip(), (int) seasonLengthSlider.getValue(), difficulty);
    }


    public void initialize() {
        List<ToggleButton> difficultyButtons = List.of(easyDifficultyButton, normalDifficultyButton, hardDifficultyButton);

        for (int i = 0; i < difficultyButtons.size(); i++) {
            int difficultyIndex = i;
            difficultyButtons.get(i).setOnAction(event -> onDifficultyButtonSelected(difficultyButtons, difficultyIndex));
        }
    }
}
