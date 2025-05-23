package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller class for the setup settings menu screen.
 * Manages the user interface and interactions for setting up the player's name,
 * season length, and difficulty level before starting the game.
 * Validates the player's name and updates the game environment with the selected settings.
 *
 * @author sha378
 */
public class MenuSetupSettingsController extends ScreenController {

    // FXML UI components for displaying error messages and collecting user input.
    @FXML
    private Text incompatibleNameText;
    @FXML
    private TextField nameField;
    @FXML
    private Slider seasonLengthSlider;
    @FXML
    private ToggleButton normalDifficultyButton, hardDifficultyButton;
    @FXML
    private Button goButton;

    // Stores the selected difficulty level (0 for normal, 1 for hard).
    private int difficulty = 0;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    public MenuSetupSettingsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_setup_settings.fxml";
    }

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing | Setup";
    }

    /**
     * Handles the event when the "Go" button is clicked.
     * Validates the player's name and updates the game environment with the selected settings.
     * Displays an error message if the name is invalid.
     */
    private void onGoButtonClicked() {
        String name = nameField.getText().strip();
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        boolean isSpecialCharacterDetected = matcher.find();

        if (name.isEmpty()) {
            getGameEnvironment().onSetupComplete("Anonymous", (int) seasonLengthSlider.getValue(), difficulty + 1);
        } else if (isSpecialCharacterDetected || name.length() < 3 || name.length() > 15) {
            incompatibleNameText.setVisible(true);
        } else {
            getGameEnvironment().onSetupComplete(name, (int) seasonLengthSlider.getValue(), difficulty + 1);
        }
    }

    /**
     * Handles the event when a difficulty button is selected.
     * Updates the selected difficulty level and highlights the selected button.
     *
     * @param difficultyButtons The list of difficulty toggle buttons.
     * @param difficultyIndex   The index of the selected difficulty button.
     */
    private void onDifficultyButtonSelected(List<ToggleButton> difficultyButtons, int difficultyIndex) {
        difficulty = difficultyIndex;
        buttonSelector(difficultyButtons, difficultyIndex);
    }

    /**
     * Initializes the setup settings menu screen.
     * Configures event listeners for the difficulty buttons and the "Go" button.
     */
    public void initialize() {
        List<ToggleButton> difficultyButtons = List.of(normalDifficultyButton, hardDifficultyButton);

        for (int i = 0; i < difficultyButtons.size(); i++) {
            int difficultyIndex = i;
            difficultyButtons.get(i).setOnAction(event -> onDifficultyButtonSelected(difficultyButtons, difficultyIndex));
        }

        goButton.setOnAction(event -> onGoButtonClicked());
    }
}