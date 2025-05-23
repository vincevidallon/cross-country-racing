package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;

/**
 * Controller class for the main menu screen.
 * Manages the user interface and interactions for the main menu, including navigation
 * to other screens such as the race setup, shop, garage, and results.
 *
 * @author sha378
 */
public class MenuMainController extends ScreenController {

    // FXML UI components for displaying player stats and navigation buttons.
    @FXML
    private Text nameText, difficultyText, seasonLengthText, moneyText, selectedCarText, prepareForRaceText;

    @FXML
    private Button raceButton, shopButton, garageButton, quitButton, resultsButton;

    /**
     * Constructs a MenuMainController with the specified game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public MenuMainController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Retrieves the FXML file path for the main menu screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_main.fxml";
    }

    /**
     * Retrieves the title for the main menu screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing";
    }

    /**
     * Initializes the main menu screen, setting up UI components with player stats
     * and configuring button actions for navigation.
     */
    public void initialize() {
        // Set player stats in the UI.
        nameText.setText(getGameEnvironment().getName() + "'s Stats:");
        difficultyText.setText(getGameEnvironment().getDifficulty() == 1 ? "Normal" : "Hard");
        seasonLengthText.setText(getGameEnvironment().getSeasonLength() + " races");
        moneyText.setText("$" + getGameEnvironment().getMoney());
        selectedCarText.setText(getGameEnvironment().getSelectedCar().mainMenuString());
        prepareForRaceText.setText("Prepare for race " + (getGameEnvironment().getNumberOfRacesPlayed() + 1) + " of " + getGameEnvironment().getSeasonLength() + "!");

        // Configure button actions for navigation.
        raceButton.setOnAction(event -> getGameEnvironment().launchScreen(new RaceSetupController(getGameEnvironment())));
        shopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
        garageButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuGarageController(getGameEnvironment())));
        quitButton.setOnAction(event -> onQuitRequested());
        resultsButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuResultsController(getGameEnvironment())));
    }
}