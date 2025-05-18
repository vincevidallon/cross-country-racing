package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Race;
import seng201.team005.models.Route;

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

    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new RouteSetupController(getGameEnvironment())));
    }


    private void showSelectedCar(Car selectedCar) {
        selectedCarText.setText("Car: " + selectedCar.getName());
        carSpeedText.setText(String.valueOf(selectedCar.getSpeed()));
        carHandlingText.setText(String.valueOf(selectedCar.getHandling()));
        carReliabilityText.setText(String.valueOf(selectedCar.getReliability()));
        carFuelEconomyText.setText(String.valueOf(selectedCar.getFuelEconomy()));
        carOverallText.setText(String.valueOf(selectedCar.getOverall()));
    }

    private void showSelectedRace(Race selectedRace) {
        selectedRaceText.setText("Race:");
        raceHoursText.setText(String.valueOf(selectedRace.getMaxDuration()));
        raceEntriesText.setText(String.valueOf(selectedRace.getEntries()));
        raceRoutesText.setText(String.valueOf(selectedRace.getRouteList().size()));
        racePrizeMoneyText.setText("$" + selectedRace.getPrizeMoney());
    }

    private void showSelectedRoute(Route selectedRoute) {
        routeDescriptionText.setText(selectedRoute.getDescription());
        routeDistanceText.setText(selectedRoute.getDistance() + " km");
        routeFuelStopsText.setText(String.valueOf(selectedRoute.getFuelStops()));
        routeDifficultyText.setText(String.valueOf(selectedRoute.getDifficulty()));
    }

    @FXML
    public void initialize() {
        handleBackButton();

        Car userCar = getGameEnvironment().getSelectedCar();
        Race selectedRace = getGameEnvironment().getSelectedRace();
        Route selectedRoute = getGameEnvironment().getSelectedRoute();
        showSelectedCar(userCar);
        showSelectedRace(selectedRace);
        showSelectedRoute(selectedRoute);
    }

}
