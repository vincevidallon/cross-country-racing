package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Route;
import seng201.team005.services.MenuService;
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

    // FXML UI components for route selection buttons.
    @FXML
    private Button route1Button, route2Button, route3Button;

    // FXML UI components for navigation buttons.
    @FXML
    private Button backToRacesButton, confirmRouteButton;

    // FXML UI components for displaying route statistics.
    @FXML
    private Text routeDescriptionLabelText, routeDistanceLabelText, routeFuelStopsLabelText, routeDifficultyLabelText;

    @FXML
    private Text routeDescriptionText, routeDistanceText, routeFuelStopsText, routeDifficultyText;

    // FXML UI component for the background rectangle of route stats.
    @FXML
    private Rectangle statRectangle;

    // List of Text nodes used to display route statistics.
    private List<Text> routeStats;

    // List of buttons representing available routes.
    private List<Button> routeButtons;

    // List of routes available for selection.
    private List<Route> raceRoutes = new ArrayList<>();

    // Button representing the currently selected route.
    private Button selectedRoute = null;

    /**
     * Constructs a RouteSetupController with the specified game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public RouteSetupController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Retrieves the FXML file path for the route setup screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/route_setup.fxml";
    }

    /**
     * Retrieves the title for the route setup screen.
     *
     * @return The screen title.
     */
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
     *
     * @param route The race route whose details are to be displayed.
     */
    private void showRouteStats(Route route) {
        routeDescriptionText.setText(route.getDescription());
        routeDistanceText.setText(route.getDistance() + " km");
        routeFuelStopsText.setText(String.valueOf(route.getFuelStops()));
        routeDifficultyText.setText(MenuService.convertStatToStars(route.getDifficulty() + 1));
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
     *
     * @param visible Whether the stats and labels should be shown to the user or hidden.
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
        });
    }

    /**
     * Sets up the hover and clicking behavior for the route selection buttons.
     * <p>
     *     Hovering on one of the buttons shows the route stats, and clicking on one of
     *     the buttons selects the route and enables the confirm route button.
     * </p>
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
     * Initializes the Route Selection screen.
     * <p>
     *     Gets race routes from the selected race and binds the route data to UI buttons.
     *     This method also sets up the hover and clicking behavior for displaying
     *     the route stats, as well as wiring up the navigation buttons.
     * </p>
     */
    @FXML
    public void initialize() {
        routeButtons = List.of(route1Button, route2Button, route3Button);
        routeStats = List.of(routeDescriptionLabelText, routeDistanceLabelText,
                routeFuelStopsLabelText, routeDifficultyLabelText);

        confirmRouteButton.setDisable(true);
        statRectangle.toBack();
        showStatVisibility(false);

        raceRoutes = getGameEnvironment().getSelectedRace().getRouteList();
        for (int i = 0; i < routeButtons.size(); i++) {
            routeButtons.get(i).setUserData(raceRoutes.get(i));
        }

        hoverAndClickSetup();
        handleBackToRaces();
        handleConfirmRoute();
    }
}