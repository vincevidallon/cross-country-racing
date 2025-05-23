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
 * Controller for the race setup screen.
 * <p>
 *     Displays a list of races available to the player and allows the player to select
 *     a race based on randomly generated attributes. Race stats are shown on hover,
 *     and the screen transitions to the Route Selection screen once the user confirms a race.
 * </p>
 *
 * @author vvi29
 */
public class RaceSetupController extends ScreenController {

    // FXML UI components for navigation and race selection.
    @FXML
    private Button exitToMenuButton, confirmRaceButton;

    @FXML
    private Button raceButton1, raceButton2, raceButton3;

    // FXML UI components for displaying race statistics.
    @FXML
    private Text raceHoursLabelText, raceEntriesLabelText, racePrizeMoneyLabelText;

    @FXML
    private Text raceHoursText, raceEntriesText, racePrizeMoneyText;

    // FXML UI component for the background pane of race details.
    @FXML
    private javafx.scene.shape.Rectangle raceDetailsPane;

    // List of Text nodes used to display race statistics.
    private List<Text> raceStats;

    // List of buttons representing available races.
    private List<Button> raceButtons = List.of();

    // List of races available for selection.
    private List<Race> races = new ArrayList<>();

    // Button representing the currently selected race.
    private Button selectedRaceButton = null;

    /**
     * Constructs a RaceSetupController with the specified game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public RaceSetupController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Retrieves the FXML file path for the race setup screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/race_setup.fxml";
    }

    /**
     * Retrieves the title for the race setup screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Race Setup:";
    }

    /**
     * Configures the exit button to return to the main menu.
     */
    private void handleExitButton() {
        exitToMenuButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    /**
     * Configures the confirm race button to store the selected race in the game environment
     * and transition to the Route Selection screen.
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
     * <p>
     *     Hovering over a race button displays the race stats, and clicking a race button
     *     selects the race and enables the confirm button.
     * </p>
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
     * Displays the statistics of the specified race in the UI.
     *
     * @param race The Race object whose stats should be displayed.
     */
    private void displayRaceStats(Race race) {
        raceHoursText.setText(String.valueOf(race.getMaxDuration()));
        raceEntriesText.setText(String.valueOf(race.getEntries()));
        racePrizeMoneyText.setText("$" + race.getPrizeMoney());
    }

    /**
     * Sets the visibility of all race stat UI elements.
     *
     * @param showing True to show the stat labels and values, false to hide them.
     */
    private void setStatVisibility(boolean showing) {
        raceStats.forEach(text -> text.setVisible(showing));
    }

    /**
     * Initializes the Race Setup screen by performing the following:
     * <ul>
     *     <li>Generates race options</li>
     *     <li>Wires up all button handlers</li>
     *     <li>Sets up stat display behavior</li>
     * </ul>
     */
    public void initialize() {
        raceButtons = List.of(raceButton1, raceButton2, raceButton3);
        raceStats = List.of(raceHoursLabelText, raceEntriesLabelText,
                racePrizeMoneyLabelText, raceHoursText, raceEntriesText, racePrizeMoneyText);

        confirmRaceButton.setDisable(true);
        raceDetailsPane.toBack();
        setStatVisibility(false);
        handleConfirmRaceButton();
        handleExitButton();

        races = getGameEnvironment().getRaceList();
        for (int i = 0; i < raceButtons.size(); i++) {
            raceButtons.get(i).setUserData(races.get(i));
        }

        hoverClickSetup();
    }
}