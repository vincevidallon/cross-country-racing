package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Race;
import seng201.team005.services.GenerateRaceService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the race setup screen
 *<p>
 *     Shows a list of races available to the player and allows the player to select
 *     a race, based on radnomly generated attributes. Race stats are shown on hover,
 *     and goes to the Route Selection screen once the user confirms a race.
 *</p>
 * The controller uses a service class {@link GenerateRaceService} for generating race options.
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
    private final GenerateRaceService raceService = new GenerateRaceService();


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


    /**
     * Sets up the exit button to return to the main menu.
     */
    private void handleExitButton() {
        exitToMenuButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }


    /**
     * Wires up the confirm race button, which stores the selected race in the game environment,
     * then transitions to the Route Selection screen.
     */
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


    /**
     * Sets up hover and click functionality for each available race button.
     * Hovering over one of the race buttons displays the race stats, clicking
     * a race button selects the race and makes the confirm button available
     * to the player.
     */
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

    /**
     * Displays the race statistics of the selected race in the UI.
     * @param race the Race object which should have its stats displayed.
     */
    private void displayRaceStats(Race race) {
        raceHoursText.setText(String.valueOf(race.getMaxDuration()));
        raceEntriesText.setText(String.valueOf(race.getEntries()));
        raceRoutesText.setText(String.valueOf(race.getRouteList().size()));
        racePrizeMoneyText.setText("$" + race.getPrizeMoney());
    }


    /**
     * Sets visibility of all the race stat UI elements.
     * @param showing true for showing stat labels and respective values, false when hiding them.
     */
    private void setStatVisibility(boolean showing) {
        raceStats.forEach(text -> text.setVisible(showing));
    }


    /**
     * Initializes the Race Setup screen by performing the following:
     * - Generates race options
     * - Wires up all button handlers
     * - Sets up stat display behaviour
     */
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

        races = raceService.generateRaces(getGameEnvironment().getDifficulty(), raceButtons.size());
        for (int i = 0; i < raceButtons.size(); i++) {
            raceButtons.get(i).setUserData(races.get(i));
        }

        hoverClickSetup();
    }
}
