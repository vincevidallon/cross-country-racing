package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import seng201.team0.GameEnvironment;

/**
 * Controller for the menu_main.fxml window
 *
 * @author seng201 teaching team
 */
public class MenuMainController extends ScreenController {

    @FXML
    private Text nameText, seasonLengthText, difficultyText;

    public MenuMainController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_main.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing"; }

    public void initialize() {
        nameText.setText(getGameEnvironment().getName());
        seasonLengthText.setText(String.valueOf(getGameEnvironment().getSeasonLength()));
        difficultyText.setText(String.valueOf(getGameEnvironment().getDifficulty()));
    }
}
