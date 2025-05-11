package seng201.team005.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import seng201.team005.GameEnvironment;
import javafx.scene.control.Button;

import java.util.List;

public class MenuShopController extends ScreenController {

    @FXML
    private Button upgradeButton1, upgradeButton2, upgradeButton3, upgradeButton4, upgradeButton5;

    @FXML
    private Button backButton, purchaseCarsButton;

    @FXML
    private Button selectedPart1, selectedPart2, selectedPart3;

    private List<Button> upgradeButtons;
    private List<Button> selectedPartButtons;
    private int nextSelectedIndex = 0;


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

    private void clickBackButton() {
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }

    private void onUpgradeButtonClicked(ActionEvent event) {
        if (nextSelectedIndex >= selectedPartButtons.size()) {
            return;
        }
        Button clicked = (Button) event.getSource();
        String partName = clicked.getText();
        Button slot = selectedPartButtons.get(nextSelectedIndex);
        slot.setText(partName);
        nextSelectedIndex++;
    }


    @FXML
    public void initialize() {
        updatePlayerMoneyText();

        upgradeButtons = List.of(upgradeButton1, upgradeButton2, upgradeButton3,
                                upgradeButton4, upgradeButton5);

        selectedPartButtons = List.of(selectedPart1, selectedPart2, selectedPart3);

        for (Button button: upgradeButtons) {
            button.setOnAction(this::onUpgradeButtonClicked);
        }
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    
}
