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

import static seng201.team005.services.MenuService.convertStatToStars;


/**
 * Controller for the owned items screen, which displays the user's
 * items after successfully purchasing from the shop. The controller
 * handles UI interactions for viewing purchased item stats, selling them
 * and updating the user balance.
 *
 * @author vvi29
 */
public class OwnedItemsController extends ScreenController {

    @FXML
    private Text userBalance, playerOwnedItemsText;

    @FXML
    private ListView<Part> ownedPartsView;

    @FXML
    private ListView<Car> ownedCarsView;

    @FXML
    private Text carNameText, speedText, handlingText, reliabilityText, fuelEconomyText, overallText, sellValueText, errorText;

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
        return "Cross Country Racing | Owned Items";
    }


    /**
     * Method to set the player name label to "<PlayerName>'s Owned Items.
     */
    private void setupPlayerNameText() {
        String name = getGameEnvironment().getName();
        playerOwnedItemsText.setText(name + "'s Owned Items");
    }


    /**
     * Method to wire up the button for sending the player back to the shop screen.
     */
    private void handleBackToShopButton() {
        backToShopButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuShopController(getGameEnvironment())));
    }


    /**
     * Updates the user balance text to be reflective of the current money in the
     * game environment.
     */
    private void setupUserBalance() {
        userBalance.setText("Money: $" + getGameEnvironment().getMoney());
    }


    /**
     * Populates the part and car ListViews with the user's currently owned items.
     */
    private void displayOwnedLists() {
        ownedPartsView.setItems(FXCollections.observableArrayList(getGameEnvironment().getOwnedParts()));
        ownedCarsView.setItems(FXCollections.observableArrayList(getGameEnvironment().getOwnedCars()));
    }


    /**
     * Sets up the hover functionality for displaying the item stats when the mouse
     * is hovered over a cell.
     * @param listView the ListView to be configured
     * @param <T> the type of Purchasable item
     */
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


    /**
     * Toggles the visibility of all stat nodes.
     * @param visible whether the stat nodes should be visible or invisible
     */
    private void setStatVisibility(boolean visible) {
        itemStats.forEach(text -> text.setVisible(visible));
    }


    /**
     * Shows the detailed stats for the selected item.
     * @param item the {@link Purchasable} item whose stats are to be shown
     */
    private void displayOwnedItemStats(Purchasable item) {
        carNameText.setText(item.getName());
        speedText.setText(convertStatToStars(item.getSpeed()));
        handlingText.setText(convertStatToStars(item.getHandling()));
        reliabilityText.setText(convertStatToStars(item.getReliability()));
        fuelEconomyText.setText(convertStatToStars(item.getFuelEconomy()));
        overallText.setText(convertStatToStars(item.getOverall()));
        sellValueText.setText("$" + item.getSellValue());

        setStatVisibility(true);
    }


    /**
     * Configures the sell button for the user to be able to sell any of
     * their selected items. This updates their balance to reflect the
     * sell value of the item.
     */
    private void handleSellButton() {
        sellItemButton.setOnAction(event -> {
            errorText.setVisible(false);
            Part selectedPart = ownedPartsView.getSelectionModel().getSelectedItem();
            Car selectedCar = ownedCarsView.getSelectionModel().getSelectedItem();

            Purchasable itemToSell = (selectedCar != null) ? selectedCar : selectedPart;
            if (itemToSell == null) return;


            if (itemToSell instanceof Car && getGameEnvironment().getOwnedCars().size() == 1) {
                errorText.setVisible(true);
            } else {
                ownedItemService.sellItem(getGameEnvironment(), itemToSell);
                userBalance.setText("Money: $" + getGameEnvironment().getMoney());
                displayOwnedLists();
                setStatVisibility(false);
                }
        });
    }


    /**
     * Sets up the Text nodes which will be used to display item stats
     * when the stats rectangle is set up.
     */
    @FXML
    private void setupStatsRectangle() {
        itemStats = List.of(carNameText, speedText, carSpeedLabelText, handlingText,
                carHandlingLabelText, reliabilityText, carReliabilityLabelText,
                fuelEconomyText, carFuelEconomyLabelText, overallText, carOverallLabelText,
                sellValueText, sellValueLabelText);
    }


    /**
     * Initialize method, sets up UI components as well as event handlers.
     */
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
