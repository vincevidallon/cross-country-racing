package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;

public class RouteSetupController extends ScreenController {

    @FXML
    private Button route1Button, route2Button, route3Button;

    @FXML
    private Button backToRacesButton, confirmRouteButton;

    @FXML
    private Text routeDescriptionLabelText, routeDistanceLabelText, routeFuelStopsLabelText, routeDifficultyLabelText;

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

    
}
