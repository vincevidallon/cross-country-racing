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

    // Label to display the currently available items (cars or parts).
    @FXML
    private Label availableLabel;

    // Text field to display the name of the selected car.
    @FXML
    private Text carNameText;

    // Buttons for displaying shop items.
    @FXML
    private Button itemButton1, itemButton2, itemButton3, itemButton4, itemButton5;

    // Buttons for navigation and actions in the shop.
    @FXML
    private Button backButton, purchaseCarsButton, buyButton, ownedItemsButton;

    // Text field to display error messages.
    @FXML
    private Text errorText;

    // Toggle buttons for selected items in the cart.
    @FXML
    private ToggleButton selectedItem1, selectedItem2, selectedItem3;

    // List of buttons representing shop items.
    private List<Button> itemButtons = List.of();

    // List of toggle buttons representing selected items in the cart.
    private List<ToggleButton> selectedItems = List.of();

    // Boolean flag to indicate whether cars or parts are being displayed.
    private boolean showCars = false;

    // List of parts available in the shop.
    private final List<Part> parts = new ArrayList<>();

    // List of cars available in the shop.
    private final List<Car> cars = new ArrayList<>();

    // List of items currently selected for purchase.
    private final List<Purchasable> selectedPurchasableItems = new ArrayList<>();

    // Service for handling shop-related functionality.
    private final ShopService shopService = new ShopService();

    /**
     * Constructs a MenuShopController with the specified game environment.
     *
     * @param gameEnvironment The game environment instance.
     */
    public MenuShopController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Retrieves the FXML file path for the shop screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/shop_menu.fxml";
    }

    /**
     * Retrieves the title for the shop screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing | Shop";
    }

    /**
     * Handles the event when a shop item button is clicked.
     * Adds the item to the cart and disables the button.
     *
     * @param event The button click event.
     */
    private void onShopButtonClicked(ActionEvent event) {
        Button clicked = (Button) event.getSource();
        Purchasable item = (Purchasable) clicked.getUserData();
        selectedPurchasableItems.add(item);
        clicked.setDisable(true);
        updateSelectedPurchasableButtons();
    }

    /**
     * Updates the toggle buttons to reflect the items currently selected in the cart.
     */
    private void updateSelectedPurchasableButtons() {
        for (int i = 0; i < selectedItems.size(); i++) {
            ToggleButton toggleButton = selectedItems.get(i);
            if (i < selectedPurchasableItems.size()) {
                Purchasable item = selectedPurchasableItems.get(i);
                toggleButton.setText(item.shopString());
                toggleButton.setUserData(item);
                toggleButton.setSelected(true);
            } else {
                toggleButton.setText("");
                toggleButton.setUserData(null);
                toggleButton.setSelected(false);
            }
        }
    }

    /**
     * Updates the shop item buttons to display either cars or parts,
     * depending on the current selection.
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
     * Sets up click and hover functionality for the shop item buttons.
     */
    private void selectedItemSetup() {
        for (Button button : itemButtons) {
            button.setOnAction(this::onShopButtonClicked);
            button.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) button.getUserData();
                if (item != null) displayStats(item);
            });
        }
    }

    /**
     * Marks a shop item button as "SOLD OUT" if the item has already been purchased.
     *
     * @param button The button to update.
     */
    private void setSoldOut(Button button) {
        Purchasable item = (Purchasable) button.getUserData();
        if (item.isPurchased()) {
            button.setText("SOLD OUT");
            button.setDisable(true);
        }
    }

    /**
     * Sets up click and hover functionality for the toggle buttons representing
     * selected items in the cart.
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
     * Re-enables the shop item button for a deselected item.
     *
     * @param item The item to re-enable.
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
     * Handles the event when a toggle button in the cart is clicked.
     * Removes the item from the cart if deselected.
     *
     * @param slotIdx The index of the toggle button.
     * @param button  The toggle button clicked.
     */
    private void toggleClick(int slotIdx, ToggleButton button) {
        if (!button.isSelected()) {
            if (slotIdx < selectedPurchasableItems.size()) {
                Purchasable deselectedItem = selectedPurchasableItems.remove(slotIdx);
                reenableItemButton(deselectedItem);
                updateSelectedPurchasableButtons();
            }
        } else {
            Purchasable item = (Purchasable) button.getUserData();
            if (item != null) displayStats(item);
        }
    }

    /**
     * Toggles between displaying cars or parts in the shop.
     * Updates the UI text and buttons accordingly.
     */
    private void purchaseSwitch() {
        purchaseCarsButton.setOnAction(event -> {
            showCars = !showCars;
            availableLabel.setText(showCars ? "Available Cars:" : "Available Parts:");
            carNameText.setText(showCars ? "Car Stats:" : "Part Stats:");
            purchaseCarsButton.setText(showCars ? "Purchase Parts:" : "Purchase Cars:");
            itemstoItemButtons();
            for (Button button : itemButtons) {
                button.setDisable(selectedPurchasableItems.contains((Purchasable) button.getUserData()));
                setSoldOut(button);
            }
        });
    }

    /**
     * Refreshes the shop buttons to display the correct items and their states.
     */
    private void refreshShopButtons() {
        List<? extends Purchasable> items = showCars ? cars : parts;
        for (int i = 0; i < itemButtons.size(); i++) {
            Purchasable item = items.get(i);
            Button button = itemButtons.get(i);
            button.setText(item.shopString());
            button.setUserData(item);
            setSoldOut(button);
        }
        errorText.setVisible(false);
    }

    /**
     * Configures the back button to navigate back to the main menu.
     */
    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    /**
     * Configures the owned items button to navigate to the owned items screen.
     */
    private void handleOwnedItemsButton() {
        ownedItemsButton.setOnAction(event -> getGameEnvironment().launchScreen(new OwnedItemsController(getGameEnvironment())));
    }

    /**
     * Clears all selected items from the cart.
     */
    private void clearSelectedItems() {
        selectedPurchasableItems.clear();
        for (ToggleButton item : selectedItems) {
            item.setSelected(false);
            item.setText("");
            item.setUserData(null);
        }
        for (Button button : itemButtons) {
            setSoldOut(button);
        }
        errorText.setVisible(false);
    }

    /**
     * Configures the buy button to allow the user to purchase selected items.
     * Displays an error message if the user cannot afford the items or exceeds the car limit.
     */
    private void buyButtonSetup() {
        buyButton.setOnAction(event -> {
            if (!shopService.canAfford(getGameEnvironment(), selectedPurchasableItems)) {
                errorText.setText("You have insufficient funds!");
                errorText.setVisible(true);
                return;
            }
            if (getGameEnvironment().getOwnedCars().size() + selectedPurchasableItems.stream()
                    .filter(item -> item instanceof Car).toList().size() > 5) {
                errorText.setText("You can only own 5 cars!");
                errorText.setVisible(true);
                return;
            }

            errorText.setVisible(false);
            shopService.purchaseItem(getGameEnvironment(), selectedPurchasableItems);
            updatePlayerMoneyText();
            clearSelectedItems();
        });
    }

    /**
     * Initializes the shop screen, setting up buttons, event listeners, and UI elements.
     */
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