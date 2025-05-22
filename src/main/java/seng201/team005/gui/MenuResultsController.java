package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Entrant;

/**
 * Controller for the menu_main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuResultsController extends ScreenController {

    @FXML
    private Text nameText, difficultyText, seasonLengthText, racesCompetedInText, averagePlacingText, finalWinningsText;

    @FXML
    private Button playAgainButton, quitButton;

    public MenuResultsController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_results.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing | Results"; }

    public void initialize() {
        nameText.setText(getGameEnvironment().getName() + "'s stats:");
        difficultyText.setText(getGameEnvironment().getDifficulty() == 1 ? "Normal" : "Hard");
        seasonLengthText.setText(getGameEnvironment().getSeasonLength() + " races");
        racesCompetedInText.setText(getGameEnvironment().getNumberOfRacesPlayed() + " / " + getGameEnvironment().getSeasonLength());
        averagePlacingText.setText(getGameEnvironment().getNumberOfRacesPlayed() > 0
                ? Entrant.positionString(getGameEnvironment().getAverageRaceResult()) : "...");
        finalWinningsText.setText("$" + getGameEnvironment().getMoney());

        playAgainButton.setOnAction(event -> getGameEnvironment().resetGame());
        quitButton.setOnAction(event -> onQuitRequested());
    }
}
