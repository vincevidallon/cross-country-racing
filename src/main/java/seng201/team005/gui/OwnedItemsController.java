package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;

public class OwnedItemsController extends ScreenController {

    @FXML
    private Text userBalance;

    @FXML
    private ListView<Part> ownedPartsView;

    @FXML
    private ListView<Car> ownedCarsView;

    @FXML
    private Text carNameText, speedText, handlingText, reliabilityText, fuelEconomyText, overallText;

    @FXML
    private Button sellItemButton, backToShopButton;

    public OwnedItemsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/owned_items.fxml";
    }

    @Override
    protected String getTitle() {
        return "Owned Items:";
    }

    private void handleBackToShopButton() {
        backToShopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
    }

    private void setupUserBalance() {
        userBalance.setText("Money: $" + getGameEnvironment().getMoney());
    }


    @FXML
    public void initialize() {
        handleBackToShopButton();
        setupUserBalance();
    }
}
