package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Route;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Route Selection screen.
 * <p>
 *     Allows the user to choose a route they want to do within their selected race through UI buttons.
 *     Displays attributes about the selected route on hover and manages navigating to
 *     other screens based on user selection.
 * </p>
 *
 * @author vvi29
 */

public class RouteSetupController extends ScreenController {

    @FXML
    private Button route1Button, route2Button, route3Button;

    @FXML
    private Button backToRacesButton, confirmRouteButton;

    @FXML
    private Text routeDescriptionLabelText, routeDistanceLabelText, routeFuelStopsLabelText, routeDifficultyLabelText;

    @FXML
    private Text routeDescriptionText, routeDistanceText, routeFuelStopsText, routeDifficultyText;

    @FXML
    private Rectangle statRectangle;

    private List<Text> routeStats;
    private List<Button> routeButtons;
    private List<Route> raceRoutes = new ArrayList<>();
    private Button selectedRoute = null;

    public RouteSetupController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/route_setup.fxml";
    }

    @Override
    protected String getTitle() {
        return "Route Setup";
    }

    private void generateRaceRoutes() {
        for (Button routeButton : routeButtons) {
            Route route = new Route();
            raceRoutes.add(route);
            routeButton.setUserData(route);
        }
    }


    private void handleBackToRaces() {
        backToRacesButton.setOnAction(event -> getGameEnvironment().launchScreen(new RaceSetupController(getGameEnvironment())));
    }


    private void showRouteStats(Route route) {
        routeDescriptionText.setText(route.getDescription());
        routeDistanceText.setText(route.getDistance() + " km");
        routeFuelStopsText.setText(String.valueOf(route.getFuelStops()));
        routeDifficultyText.setText(String.valueOf(route.getDifficulty()));
    }

    private void hideRouteStats() {
        routeDescriptionText.setText("");
        routeDistanceText.setText("");
        routeFuelStopsText.setText("");
        routeDifficultyText.setText("");
    }

    private void showStatVisibility(boolean visible) {
        routeStats.forEach(stat -> stat.setVisible(visible));
    }

    private void handleConfirmRoute() {
        confirmRouteButton.setOnAction(event -> {
            if (selectedRoute == null) {
                return;
            }
            Route chosenRoute = (Route) selectedRoute.getUserData();
            getGameEnvironment().setSelectedRoute(chosenRoute);
            getGameEnvironment().launchScreen(new RaceConfirmController(getGameEnvironment()));
        }
    );
    }


    private void hoverAndClickSetup() {
        for (int i = 0; i < routeButtons.size(); i++) {
            Button routeButton = routeButtons.get(i);
            Route raceRoute = raceRoutes.get(i);


            routeButton.setOnMouseEntered(event -> {
                if (selectedRoute != routeButton) {
                    showRouteStats(raceRoute);
                    showStatVisibility(true);
                }
            });

            routeButton.setOnMouseExited(event -> {
                if (selectedRoute == null) {
                    hideRouteStats();
                    showStatVisibility(false);
                }
            });

            routeButton.setOnAction(event -> {
                selectedRoute = routeButton;
                showRouteStats(raceRoute);
                showStatVisibility(true);
                confirmRouteButton.setDisable(false);
            });
        }
    }


    @FXML
    public void initialize() {
        routeButtons = List.of(route1Button, route2Button, route3Button);
        routeStats = List.of(routeDescriptionLabelText, routeDistanceLabelText,
                routeFuelStopsLabelText, routeDifficultyLabelText);

        confirmRouteButton.setDisable(true);
        statRectangle.toBack();
        showStatVisibility(false);
        generateRaceRoutes();
        hoverAndClickSetup();
        handleBackToRaces();
        handleConfirmRoute();


    }

    
}
