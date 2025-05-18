package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Race;
import seng201.team005.models.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the race setup screen
 *
 * @author vvi29
 */


public class RaceSetupController extends ScreenController {
    @FXML
    private Button exitToMenuButton, confirmRaceButton;

    @FXML
    private Button raceButton1, raceButton2, raceButton3;

    @FXML
    private Text raceHoursLabelText, raceEntriesLabelText, raceRoutesLabelText, racePrizeMoneyLabelText;

    @FXML
    private Text raceHoursText, raceEntriesText, raceRoutesText, racePrizeMoneyText;

    @FXML
    private javafx.scene.shape.Rectangle raceDetailsPane;

    private List<Text> raceStats;

    private List<Button> raceButtons = List.of();
    private List<Race> races = new ArrayList<>();

    private Button selectedRaceButton = null;

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

    // Handling the confirm race button to proceed to the next screen
    private void handleConfirmRaceButton() {
        confirmRaceButton.setOnAction(event -> {
            if (selectedRaceButton == null) {
                return;
            }
            Race selectedRace = (Race) selectedRaceButton.getUserData();
            getGameEnvironment().setSelectedRace(selectedRace);

            getGameEnvironment().launchScreen(new RouteSetupController(getGameEnvironment()));
        });
    }


    // Method for generating races
    private void generateRaces(int difficulty) {
        for (int i = 0; i < raceButtons.size(); i++) {
            Race race = new Race(difficulty);
            races.add(race);
        }
    }

    // Method for handling the hover setup logic, similar to shop screen
    private void hoverClickSetup() {
        for (int i = 0; i < raceButtons.size(); i++) {
            Button button = raceButtons.get(i);
            Race race = races.get(i);
            button.setUserData(race);

            button.setOnMouseEntered(event -> {
                if (selectedRaceButton != button) {
                    displayRaceStats(race);
                    setStatVisibility(true);
                }
            });

            button.setOnMouseExited(event -> {
                if (selectedRaceButton == null) {
                    setStatVisibility(false);
                }
            });

            button.setOnAction(event -> {
                selectedRaceButton = button;
                displayRaceStats(race);
                setStatVisibility(true);
                confirmRaceButton.setDisable(false);
            });
        }

    }

    // Obtaining the race stats
    private void displayRaceStats(Race race) {
        raceHoursText.setText(String.valueOf(race.getMaxDuration()));
        raceEntriesText.setText(String.valueOf(race.getEntries()));
        raceRoutesText.setText(String.valueOf(race.getRouteList().size()));
        racePrizeMoneyText.setText("$" + race.getPrizeMoney());
    }

    private void setStatVisibility(boolean showing) {
        raceStats.forEach(text -> text.setVisible(showing));
    }

    @FXML
    public void initialize() {

        raceButtons = List.of(raceButton1, raceButton2, raceButton3);
        raceStats = List.of(raceHoursLabelText, raceEntriesLabelText, raceRoutesLabelText,
                racePrizeMoneyLabelText, raceHoursText, raceEntriesText, raceRoutesText, racePrizeMoneyText);

        confirmRaceButton.setDisable(true);
        raceDetailsPane.toBack();
        setStatVisibility(false);
        handleConfirmRaceButton();
        handleExitButton();
        generateRaces(getGameEnvironment().getDifficulty());
        hoverClickSetup();
    }
}
