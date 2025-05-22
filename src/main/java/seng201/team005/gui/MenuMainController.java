package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;

/**
 * Controller for the menu_main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuMainController extends ScreenController {
    @FXML
    private Text nameText, difficultyText, seasonLengthText, moneyText, selectedCarText, prepareForRaceText;

    @FXML
    private Button raceButton, shopButton, garageButton, quitButton, resultsButton;

    public MenuMainController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_main.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing"; }

    public void initialize() {
        nameText.setText(getGameEnvironment().getName() + "'s Stats:");
        difficultyText.setText(getGameEnvironment().getDifficulty() == 1 ? "Normal" : "Hard");
        seasonLengthText.setText(getGameEnvironment().getSeasonLength() + " races");
        moneyText.setText("$" + getGameEnvironment().getMoney());
        selectedCarText.setText(getGameEnvironment().getSelectedCar().mainMenuString());
        prepareForRaceText.setText("Prepare for race " + (getGameEnvironment().getNumberOfRacesPlayed() + 1) + " of " + getGameEnvironment().getSeasonLength() + "!");


        raceButton.setOnAction(event -> getGameEnvironment().launchScreen(new RaceSetupController(getGameEnvironment())));
        shopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
        garageButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuGarageController(getGameEnvironment())));
        quitButton.setOnAction(event -> onQuitRequested());

        resultsButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuResultsController(getGameEnvironment())));
    }
}
