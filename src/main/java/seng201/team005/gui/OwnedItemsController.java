package seng201.team005.gui;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;
import seng201.team005.services.OwnedItemsService;

import java.util.List;

public class OwnedItemsController extends ScreenController {

    @FXML
    private Text userBalance, playerOwnedItemsText;

    @FXML
    private ListView<Part> ownedPartsView;

    @FXML
    private ListView<Car> ownedCarsView;

    @FXML
    private Text carNameText, speedText, handlingText, reliabilityText, fuelEconomyText, overallText, sellValueText;

    @FXML
    private Button sellItemButton, backToShopButton;

    @FXML
    private Text carSpeedLabelText, carHandlingLabelText, carReliabilityLabelText, carFuelEconomyLabelText, carOverallLabelText,
    sellValueLabelText;

    private List<Text> itemStats;
    private final OwnedItemsService ownedItemService = new OwnedItemsService();

    @FXML
    private Rectangle statsRectangle;

    public OwnedItemsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/owned_items.fxml";
    }

    @Override
    protected String getTitle() {
        return "Owned Items";
    }

    private void setupPlayerNameText() {
        String name = getGameEnvironment().getName();
        playerOwnedItemsText.setText(name + "'s Owned Items");
    }

    private void handleBackToShopButton() {
        backToShopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
    }

    private void setupUserBalance() {
        userBalance.setText("Money: $" + getGameEnvironment().getMoney());
    }

    // Functionality for showing the owned items into the ListView
    private void displayOwnedLists() {
        ownedPartsView.setItems(FXCollections.observableArrayList(getGameEnvironment().getOwnedParts()));
        ownedCarsView.setItems(FXCollections.observableArrayList(getGameEnvironment().getOwnedCars()));
    }

    private <T extends Purchasable> void hoverSetup(ListView<T> listView) {
        listView.setCellFactory(list -> {
            ListCell<T> cell = new ListCell<>() {
                @Override
                protected void updateItem(T item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item.getName());
                }
            };
            cell.setOnMouseEntered(event -> {
                T item = cell.getItem();
                if (item != null) displayOwnedItemStats(item);
            });
            return cell;
        });
    }

    private void setStatVisibility(boolean visible) {
        itemStats.forEach(text -> text.setVisible(visible));
    }

    private void displayOwnedItemStats(Purchasable item) {
        carNameText.setText(item.getName());
        speedText.setText(String.valueOf(item.getSpeed()));
        handlingText.setText(String.valueOf(item.getHandling()));
        reliabilityText.setText(String.valueOf(item.getReliability()));
        fuelEconomyText.setText(String.valueOf(item.getFuelEconomy()));
        overallText.setText(String.valueOf(item.getOverall()));
        sellValueText.setText("$" + item.getSellValue());

        setStatVisibility(true);
    }

    private void handleSellButton() {
        sellItemButton.setOnAction(event -> {
            Part selectedPart = ownedPartsView.getSelectionModel().getSelectedItem();
            Car selectedCar = ownedCarsView.getSelectionModel().getSelectedItem();

            Purchasable itemToSell = (selectedCar != null) ? selectedCar : selectedPart;
            if (itemToSell == null) return;

            ownedItemService.sellItem(getGameEnvironment(), itemToSell);
            userBalance.setText("Money: $" + getGameEnvironment().getMoney());
            displayOwnedLists();
            setStatVisibility(false);
        });
    }

    @FXML
    private void setupStatsRectangle() {
        itemStats = List.of(carNameText, speedText, carSpeedLabelText, handlingText,
                carHandlingLabelText, reliabilityText, carReliabilityLabelText,
                fuelEconomyText, carFuelEconomyLabelText, overallText, carOverallLabelText,
                sellValueText, sellValueLabelText);
    }


    @FXML
    public void initialize() {
        setupPlayerNameText();
        setupStatsRectangle();
        statsRectangle.toBack();
        setStatVisibility(false);
        handleBackToShopButton();
        setupUserBalance();
        displayOwnedLists();
        hoverSetup(ownedPartsView);
        hoverSetup(ownedCarsView);
        handleSellButton();
    }
}
