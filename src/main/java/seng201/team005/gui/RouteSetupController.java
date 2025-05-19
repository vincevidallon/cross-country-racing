package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Route;
import seng201.team005.services.RouteService;

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
    private final RouteService routeService = new RouteService();
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

    /**
     * Wires up the back button for the user to return to the Race Setup screen.
     */
    private void handleBackToRaces() {
        backToRacesButton.setOnAction(event -> getGameEnvironment().launchScreen(new RaceSetupController(getGameEnvironment())));
    }

    /**
     * Displays the details of a {@link Route} on the screen.
     * @param route the race route whose details are to be displayed.
     */
    private void showRouteStats(Route route) {
        routeDescriptionText.setText(route.getDescription());
        routeDistanceText.setText(route.getDistance() + " km");
        routeFuelStopsText.setText(String.valueOf(route.getFuelStops()));
        routeDifficultyText.setText(String.valueOf(route.getDifficulty()));
    }

    /**
     * Clears all the route detail display fields.
     */
    private void hideRouteStats() {
        routeDescriptionText.setText("");
        routeDistanceText.setText("");
        routeFuelStopsText.setText("");
        routeDifficultyText.setText("");
    }

    /**
     * Sets the visibility of all route stats labels and their values.
     * @param visible whether the stats and labels should be shown to the user or hidden
     */
    private void showStatVisibility(boolean visible) {
        routeStats.forEach(stat -> stat.setVisible(visible));
    }

    /**
     * Wires the confirm button to store the user's selected route in the {@link GameEnvironment}
     * and then transitions to the race confirmation screen.
     */
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


    /**
     * Sets up the hover and clicking behaviour for the route selection buttons.
     * Hovering on one of the buttons shows the route stats, clicking on one of
     * the buttons selects the route and enables the confirm route button.
     */
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


    /**
     * Initializes the Route Selection screen. Generates race routes using
     * {@link RouteService}, and then binds the route data to UI buttons.
     * This method also sets up the hover and clicking behaviour for displaying
     * the route stats, as well as wiring up the navigation buttons.
     */
    @FXML
    public void initialize() {
        routeButtons = List.of(route1Button, route2Button, route3Button);
        routeStats = List.of(routeDescriptionLabelText, routeDistanceLabelText,
                routeFuelStopsLabelText, routeDifficultyLabelText);

        confirmRouteButton.setDisable(true);
        statRectangle.toBack();
        showStatVisibility(false);

        raceRoutes = routeService.generateRoutes(routeButtons.size());
        for (int i = 0; i < routeButtons.size(); i++) {
            routeButtons.get(i).setUserData(raceRoutes.get(i));
        }

        hoverAndClickSetup();
        handleBackToRaces();
        handleConfirmRoute();


    }

    
}
