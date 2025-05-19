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
 *     Shows a final summary of the user's selected car, race and route before beginning the race.
 *     Provides the user with an option to confirm their selections and proceed to the race or
 *     return to the route selection screen if they wish to change their choices.
 * </p>
 *
 * @author vvi29
 */

public class RaceConfirmController extends ScreenController {

    @FXML
    private Button backButton, confirmButton;

    @FXML
    private Text selectedCarText, carSpeedLabelText, carSpeedText, carHandlingLabelText, carHandlingText,
    carReliabilityLabelText, carReliabilityText, carFuelEconomyLabelText, carFuelEconomyText,
    carOverallLabelText, carOverallText;

    @FXML
    private Text selectedRaceText, raceHoursLabelText, raceHoursText, raceEntriesLabelText, raceEntriesText,
    raceRoutesLabelText, raceRoutesText, racePrizeMoneyLabelText, racePrizeMoneyText;

    @FXML
    private Text selectedRouteText, routeDescriptionLabelText, routeDescriptionText,
    routeDistanceLabelText, routeDistanceText, routeFuelStopsLabelText, routeFuelStopsText,
    routeDifficultyLabelText, routeDifficultyText;

    @FXML
    private javafx.scene.shape.Rectangle carStatPane, raceDetailsPane, routeDetailsPane;


    public RaceConfirmController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }


    @Override
    protected String getFxmlFile() {
        return "/fxml/race_confirm.fxml";
    }


    @Override
    protected String getTitle() {
        return "Pre-Race Confirmation";
    }


    /**
     * A method for wiring up the back button to take the user back to the
     * Route Selection screen.
     */
    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new RouteSetupController(getGameEnvironment())));
    }


    /**
     * A method for showing the stats of the user's selected car.
     * @param selectedCar the car chosen by the user for the race
     */
    private void showSelectedCar(Car selectedCar) {
        carSpeedLabelText.setVisible(true);
        carHandlingLabelText.setVisible(true);
        carReliabilityLabelText.setVisible(true);
        carFuelEconomyLabelText.setVisible(true);
        carOverallLabelText.setVisible(true);

        carSpeedText.setVisible(true);
        carHandlingText.setVisible(true);
        carReliabilityText.setVisible(true);
        carFuelEconomyText.setVisible(true);
        carOverallText.setVisible(true);

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
     * @param selectedRace the race selected by the user, which has parameters such as the duration of the race,
     *                     the number of other entrants, the routes available and the prize money on offer.
     */
    private void showSelectedRace(Race selectedRace) {
        raceHoursLabelText.setVisible(true);
        raceEntriesLabelText.setVisible(true);
        raceRoutesLabelText.setVisible(true);
        racePrizeMoneyLabelText.setVisible(true);
        raceHoursText.setVisible(true);
        raceEntriesText.setVisible(true);
        raceRoutesText.setVisible(true);
        racePrizeMoneyText.setVisible(true);

        selectedRaceText.setText("Race:");
        raceHoursText.setText(String.valueOf(selectedRace.getMaxDuration()));
        raceEntriesText.setText(String.valueOf(selectedRace.getEntries()));
        raceRoutesText.setText(String.valueOf(selectedRace.getRouteList().size()));
        racePrizeMoneyText.setText("$" + selectedRace.getPrizeMoney());
    }


    /**
     * Shows the attributes of the selected {@link Route} in the UI.
     *
     * @param selectedRoute the route selected by the user, including its distance, fuel stops and
     *                      overall difficulty
     */
    private void showSelectedRoute(Route selectedRoute) {
        selectedRouteText.setVisible(true);
        routeDescriptionLabelText.setVisible(true);
        routeDistanceLabelText.setVisible(true);
        routeFuelStopsLabelText.setVisible(true);
        routeDifficultyLabelText.setVisible(true);

        routeDescriptionText.setVisible(true);
        routeDistanceText.setVisible(true);
        routeFuelStopsText.setVisible(true);
        routeDifficultyText.setVisible(true);

        selectedRouteText.setText("Route:");
        routeDescriptionText.setText(selectedRoute.getDescription());
        routeDistanceText.setText(selectedRoute.getDistance() + " km");
        routeFuelStopsText.setText(String.valueOf(selectedRoute.getFuelStops()));
        routeDifficultyText.setText(String.valueOf(selectedRoute.getDifficulty()));
    }

    /**
     * Proceeds to the race screen through a transition to {@link MenuRaceController}.
     * This is called when the user confirms their selections.
     */
    private void onConfirmButtonClicked() {
        getGameEnvironment().launchScreen(new MenuRaceController(getGameEnvironment()));
    }


    /**
     * Initializes the Pre-Race confirmation screen. All data required is obtained from the
     * {@link GameEnvironment}.
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


        if (userCar != null) showSelectedCar(userCar);
        if (selectedRace != null) showSelectedRace(selectedRace);
        if (selectedRoute != null) showSelectedRoute(selectedRoute);
    }

}
