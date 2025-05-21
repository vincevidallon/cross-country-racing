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
public class MenuResultsController extends ScreenController {

    @FXML
    private Text thanksText;

    @FXML
    private Button raceButton, shopButton, garageButton, quitButton, resultsButton;

    public MenuResultsController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_results.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing | Results"; }

    public void initialize() {

    }
}
