package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Entrant;

/**
 * Controller class for the results menu screen.
 * Displays the player's performance statistics at the end of the game season,
 * including difficulty, season length, races competed in, average placing, and final winnings.
 * Provides options to play again or quit the game.
 *
 * @author sha378
 */
public class MenuResultsController extends ScreenController {

    // FXML UI components for displaying player statistics.
    @FXML
    private Text nameText, difficultyText, seasonLengthText, racesCompetedInText, averagePlacingText, finalWinningsText;

    // FXML UI components for user actions.
    @FXML
    private Button playAgainButton, quitButton;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    public MenuResultsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_results.fxml";
    }

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing | Results";
    }

    /**
     * Initializes the results menu screen, setting up UI components with the player's statistics.
     * Configures event listeners for the "Play Again" and "Quit" buttons.
     */
    public void initialize() {
        // Set the player's name and stats.
        nameText.setText(getGameEnvironment().getName() + "'s stats:");
        difficultyText.setText(getGameEnvironment().getDifficulty() == 1 ? "Normal" : "Hard");
        seasonLengthText.setText(getGameEnvironment().getSeasonLength() + " races");
        racesCompetedInText.setText(getGameEnvironment().getNumberOfRacesPlayed() + " / " + getGameEnvironment().getSeasonLength());
        averagePlacingText.setText(getGameEnvironment().getNumberOfRacesPlayed() > 0
                ? Entrant.positionString(getGameEnvironment().getAverageRaceResult()) : "...");
        finalWinningsText.setText("$" + getGameEnvironment().getMoney());

        // Configure button actions.
        playAgainButton.setOnAction(event -> getGameEnvironment().resetGame());
        quitButton.setOnAction(event -> onQuitRequested());
    }
}