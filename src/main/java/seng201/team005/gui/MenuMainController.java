package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;

/**
 * Controller for the menu_main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuMainController extends ScreenController {
    // TODO: Implement new functionality (stats window, results screen)
    @FXML
    private Text thanksText;

    @FXML
    private Button raceButton, shopButton, garageButton, quitButton, resultsButton;

    public MenuMainController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_main.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing"; }

    public void initialize() {
        if (getGameEnvironment().getCurrentSeason() == getGameEnvironment().getSeasonLength()) {
            raceButton.setVisible(false);
            shopButton.setVisible(false);
            garageButton.setVisible(false);
            quitButton.setVisible(false);

            thanksText.setVisible(true);
            resultsButton.setVisible(true);
            resultsButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuResultsController(getGameEnvironment())));
        }
        raceButton.setOnAction(event -> getGameEnvironment().launchScreen(new RaceSetupController(getGameEnvironment())));
        shopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
        garageButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuGarageController(getGameEnvironment())));
        quitButton.setOnAction(event -> onQuitRequested());
    }
}
