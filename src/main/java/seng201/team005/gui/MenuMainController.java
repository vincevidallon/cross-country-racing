package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import seng201.team005.GameEnvironment;

/**
 * Controller for the menu_main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuMainController extends ScreenController {

    @FXML
    private Button raceButton, shopButton, garageButton, quitButton;

    public MenuMainController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_main.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing"; }

    public void initialize() {
        //raceButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuSetupRaceController(getGameEnvironment())));
        shopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
        garageButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuGarageController(getGameEnvironment())));
        quitButton.setOnAction(event -> onQuitRequested());
    }
}
