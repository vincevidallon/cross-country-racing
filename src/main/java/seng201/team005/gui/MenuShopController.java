package seng201.team005.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;
import seng201.team005.services.ShopService;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller class for the shop screen.
 * <p>
 *     This controller class handles the user interactions for purchasing
 *     cars and parts. It manages the UI events, displays information about
 *     available items, as well as interfacing with {@link ShopService} to
 *     allow for shop transactions.
 * </p>
 *
 * @author vvi29
 */


public class MenuShopController extends ScreenController {

    @FXML
    private Label availableLabel;

    @FXML
    private Text carNameText;

    @FXML
    private Button itemButton1, itemButton2, itemButton3, itemButton4, itemButton5;

    @FXML
    private Button backButton, purchaseCarsButton, buyButton, ownedItemsButton;

    @FXML
    private Text errorText;

    @FXML
    private ToggleButton selectedItem1, selectedItem2, selectedItem3;

    private List<Button> itemButtons = List.of();
    private List<ToggleButton> selectedItems = List.of();
    private int nextSlotidx;
    private boolean showCars = false;

    private final List<Part> parts = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();

    private List<Purchasable> selectedPurchasableItems = new ArrayList<>();

    private final ShopService shopService = new ShopService();


    public MenuShopController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }


    @Override
    protected String getFxmlFile() {
        return "/fxml/shop_menu.fxml";
    }


    @Override
    protected String getTitle() {
        return "Cross Country Racing | Shop";
    }


    /**
     * Method for handling when an item button is clicked so that it can be
     * added to the next available cart slot.
     * @param event the button click event
     */
    private void onUpgradeButtonClicked(ActionEvent event) {
        if (nextSlotidx >= selectedItems.size()) {
            return;
        }

        Button clicked = (Button) event.getSource();
        Purchasable item = (Purchasable) clicked.getUserData();
        selectedPurchasableItems.add(item);
        clicked.setDisable(true);
        updateSelectedPurchasableButtons();
    }


    /**
     * Updates the selected item buttons with the selected items from the cart.
     */
    private void updateSelectedPurchasableButtons() {
        for (int i = 0; i < selectedItems.size(); i++) {
            ToggleButton toggleButton = selectedItems.get(i);
            if (i < selectedPurchasableItems.size()) {
                Purchasable item = selectedPurchasableItems.get(i);
                toggleButton.setText(item.shopString());
                toggleButton.setUserData(item);
                toggleButton.setSelected(true);
            }
            else {
                toggleButton.setText("");
                toggleButton.setUserData(null);
                toggleButton.setSelected(false);
            }
        }
    }


    /**
     * Updates all item buttons with cars or parts, depending on which option
     * is currently presented to the user.
     */
    private void itemstoItemButtons() {
        List<? extends Purchasable> items = showCars ? cars : parts;
        for (int i = 0; i < itemButtons.size(); i++) {
            Button button = itemButtons.get(i);
            Purchasable item = items.get(i);
            button.setText(item.shopString());
            button.setUserData(item);
        }
        errorText.setVisible(false);
    }


    /**
     * Handles up the click and hover functionality for each item button.
     */
    private void selectedItemSetup() {
        for (Button button : itemButtons) {
            button.setOnAction(this::onUpgradeButtonClicked);
            button.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) button.getUserData();
                if (item != null) displayStats(item);
            });
        }
    }


    /**
     * Sets up click and hover functionality for the toggle buttons, which represent
     * selected items currently in the cart.
     */
    private void toggleButtonSetup() {
        for (int i = 0; i < selectedItems.size(); i++) {
            final int index = i;
            ToggleButton button = selectedItems.get(i);
            button.setOnAction(event -> toggleClick(index, button));
            button.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) button.getUserData();
                if (item != null) displayStats(item);
            });
        }
    }


    /**
     * Reenables the item button for a {@link Purchasable} item
     * that has been removed from the cart.
     * @param item
     */
    private void reenableItemButton(Purchasable item) {
        for (Button button : itemButtons) {
            if (item.equals(button.getUserData())) {
                button.setDisable(false);
                break;
            }
        }
    }


    /**
     * Handles when a user selects or deselects one of the cart toggle buttons.
     * @param slotIdx the index of the toggle button slot
     * @param button the toggle button clicked by the user
     */
    private void toggleClick(int slotIdx, ToggleButton button) {
        if (!button.isSelected()) {
            if (slotIdx < selectedPurchasableItems.size()) {
                Purchasable deselectedItem = selectedPurchasableItems.remove(slotIdx);
                reenableItemButton(deselectedItem);
                updateSelectedPurchasableButtons();
            }
        }
        else {
            Purchasable item = (Purchasable) button.getUserData();
            if (item != null) displayStats(item);
        }
    }


    /**
     * Method for switching between displaying available cars or parts
     * to the user. The UI text is updated to reflect this.
     */
    private void purchaseSwitch() {
        purchaseCarsButton.setOnAction(event -> {
            showCars = !showCars;
            availableLabel.setText(showCars ? "Available Cars:" : "Available Parts:");
            carNameText.setText(showCars ? "Car Stats:" : "Part Stats:");
            purchaseCarsButton.setText(showCars ? "Purchase Parts:" : "Purchase Cars:");
            itemstoItemButtons();
        });
    }


    /**
     * Refreshes shop buttons with correct item text and data.
     */
    private void refreshShopButtons() {
        List<? extends Purchasable> items = showCars ? cars : parts;
        for (int i = 0; i < itemButtons.size(); i++) {
            Purchasable item = items.get(i);
            Button button = itemButtons.get(i);
            button.setText(item.shopString());
            button.setUserData(item);
            button.setDisable(false);
        }
        errorText.setVisible(false);
    }


    /**
     * Programs the back button to return the user to the main menu.
     */
    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }


    /**
     * Sets up the owned items button to allow the user to view items that they have purchased from the shop.
     */
    private void handleOwnedItemsButton() {
        ownedItemsButton.setOnAction(event -> getGameEnvironment().launchScreen(new OwnedItemsController(getGameEnvironment())));
    }


    /**
     * Clears all the selected items in the cart, resets slot index.
     */
    private void clearSelectedItems() {
        selectedPurchasableItems.clear();
        for (ToggleButton item : selectedItems) {
            item.setSelected(false);
            item.setText("");
            item.setUserData(null);
        }
        for (Button button : itemButtons) {
            button.setDisable(false);
        }
        errorText.setVisible(false);
    }


    /**
     * Sets up buy button behaviour so that the user can purchase all selected items.
     * If the player cannot afford the items, an error message is displayed.
     */
    private void buyButtonSetup() {
        buyButton.setOnAction(event -> {
            if (!shopService.canAfford(getGameEnvironment(), selectedPurchasableItems)) {
                errorText.setText("You have insufficient funds!");
                errorText.setVisible(true);
                return;
            }
            errorText.setVisible(false);
            shopService.purchaseItem(getGameEnvironment(), selectedPurchasableItems);
            updatePlayerMoneyText();
            clearSelectedItems();
        });
    }


    @FXML
    public void initialize() {
        itemButtons = List.of(itemButton1, itemButton2, itemButton3, itemButton4, itemButton5);
        selectedItems = List.of(selectedItem1, selectedItem2, selectedItem3);

        parts.addAll(getGameEnvironment().getShopParts());
        cars.addAll(getGameEnvironment().getShopCars());

        selectedItemSetup();
        toggleButtonSetup();
        purchaseSwitch();
        handleBackButton();
        buyButtonSetup();
        handleOwnedItemsButton();


        showCars = false;
        refreshShopButtons();
        errorText.toFront();
        errorText.setVisible(false);
        updatePlayerMoneyText();
    }
}

