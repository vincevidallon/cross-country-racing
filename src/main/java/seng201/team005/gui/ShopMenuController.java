package seng201.team005.gui;

import javafx.fxml.FXML;
import seng201.team005.GameEnvironment;
import java.util.List;
import java.awt.*;

public class ShopMenuController extends ScreenController {

    @FXML
    private Label shopBalanceLabel;

    @FXML
    private Button upgradeButton1, upgradeButton2, upgradeButton3;

    @FXML
    private Button upgradeButton4, upgradeButton5;


    public ShopMenuController(GameEnvironment gameEnvironment) {
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
}
