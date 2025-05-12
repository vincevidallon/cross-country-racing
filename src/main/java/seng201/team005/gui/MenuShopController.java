package seng201.team005.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import seng201.team005.GameEnvironment;
import javafx.scene.control.Button;

import java.util.List;

public class MenuShopController extends ScreenController {

    @FXML
    private Button upgradeButton1, upgradeButton2, upgradeButton3, upgradeButton4, upgradeButton5;

    @FXML
    private Button backButton, purchaseCarsButton, buyButton;

    @FXML
    private ToggleButton selectedPart1, selectedPart2, selectedPart3;

    private List<Button> upgradeButtons = List.of();
    private List<ToggleButton> selectedParts = List.of();
    int nextSlotidx = 0;


    public MenuShopController(GameEnvironment gameEnvironment) {
        super (gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/shop_menu.fxml";
    }

    @Override
    protected String getTitle() {
        return "Shop";
    }


    @FXML
    public void initialize() {
        updatePlayerMoneyText();

        upgradeButtons = List.of(upgradeButton1);
        selectedParts = List.of(selectedPart1, selectedPart2, selectedPart3);
        nextSlotidx = 0;


        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    
}
