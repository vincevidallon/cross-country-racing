package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Race;
import seng201.team005.models.Route;

/**
 * Controller for the race confirmation screen.
 * <p>
 *     Shows a final summary of the user's selected car, race, and route before beginning the race.
 *     Provides the user with an option to confirm their selections and proceed to the race or
 *     return to the route selection screen if they wish to change their choices.
 * </p>
 *
 * @author vvi29
 */
public class RaceConfirmController extends ScreenController {

    // FXML UI components for navigation buttons.
    @FXML
    private Button backButton, confirmButton;

    // FXML UI components for displaying selected car details.
    @FXML
    private Text selectedCarText, carSpeedLabelText, carSpeedText, carHandlingLabelText, carHandlingText,
            carReliabilityLabelText, carReliabilityText, carFuelEconomyLabelText, carFuelEconomyText,
            carOverallLabelText, carOverallText;

    // FXML UI components for displaying selected race details.
    @FXML
    private Text selectedRaceText, raceHoursLabelText, raceHoursText, raceEntriesLabelText, raceEntriesText,
            raceRoutesLabelText, raceRoutesText, racePrizeMoneyLabelText, racePrizeMoneyText;

    // FXML UI components for displaying selected route details.
    @FXML
    private Text selectedRouteText, routeDescriptionLabelText, routeDescriptionText,
            routeDistanceLabelText, routeDistanceText, routeFuelStopsLabelText, routeFuelStopsText,
            routeDifficultyLabelText, routeDifficultyText;

    // FXML UI components for background panes.
    @FXML
    private javafx.scene.shape.Rectangle carStatPane, raceDetailsPane, routeDetailsPane;

    /**
     * Constructs a RaceConfirmController with the specified game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public RaceConfirmController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Retrieves the FXML file path for the race confirmation screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/race_confirm.fxml";
    }

    /**
     * Retrieves the title for the race confirmation screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Pre-Race Confirmation";
    }

    /**
     * Configures the back button to navigate back to the route selection screen.
     */
    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new RouteSetupController(getGameEnvironment())));
    }

    /**
     * Displays the stats of the user's selected car in the UI.
     *
     * @param selectedCar The car chosen by the user for the race.
     */
    private void showSelectedCar(Car selectedCar) {
        selectedCarText.setText("Car: " + selectedCar.getName());
        carSpeedText.setText(String.valueOf(selectedCar.getSpeed()));
        carHandlingText.setText(String.valueOf(selectedCar.getHandling()));
        carReliabilityText.setText(String.valueOf(selectedCar.getReliability()));
        carFuelEconomyText.setText(String.valueOf(selectedCar.getFuelEconomy()));
        carOverallText.setText(String.valueOf(selectedCar.getOverall()));
    }

    /**
     * Displays the details of the user's selected {@link Race} in the UI.
     *
     * @param selectedRace The race selected by the user, including parameters such as duration,
     *                     number of entrants, available routes, and prize money.
     */
    private void showSelectedRace(Race selectedRace) {
        selectedRaceText.setText("Race:");
        raceHoursText.setText(String.valueOf(selectedRace.getMaxDuration()));
        raceEntriesText.setText(String.valueOf(selectedRace.getEntries()));
        raceRoutesText.setText(String.valueOf(selectedRace.getRouteList().size()));
        racePrizeMoneyText.setText("$" + selectedRace.getPrizeMoney());
    }

    /**
     * Displays the attributes of the selected {@link Route} in the UI.
     *
     * @param selectedRoute The route selected by the user, including its distance, fuel stops, and difficulty.
     */
    private void showSelectedRoute(Route selectedRoute) {
        selectedRouteText.setText("Route:");
        routeDescriptionText.setText(selectedRoute.getDescription());
        routeDistanceText.setText(selectedRoute.getDistance() + " km");
        routeFuelStopsText.setText(String.valueOf(selectedRoute.getFuelStops()));
        routeDifficultyText.setText(String.valueOf(selectedRoute.getDifficulty()));
    }

    /**
     * Proceeds to the race screen by transitioning to {@link MenuRaceController}.
     * This is triggered when the user confirms their selections.
     */
    private void onConfirmButtonClicked() {
        getGameEnvironment().launchScreen(new MenuRaceController(getGameEnvironment()));
    }

    /**
     * Initializes the pre-race confirmation screen. Retrieves the selected car, race, and route
     * from the {@link GameEnvironment} and displays their details in the UI.
     */
    @FXML
    public void initialize() {
        carStatPane.toBack();
        raceDetailsPane.toBack();
        routeDetailsPane.toBack();

        handleBackButton();
        confirmButton.setOnAction(event -> onConfirmButtonClicked());

        Car userCar = getGameEnvironment().getSelectedCar();
        Race selectedRace = getGameEnvironment().getSelectedRace();
        Route selectedRoute = getGameEnvironment().getSelectedRoute();

        showSelectedCar(userCar);
        showSelectedRace(selectedRace);
        showSelectedRoute(selectedRoute);
    }
}