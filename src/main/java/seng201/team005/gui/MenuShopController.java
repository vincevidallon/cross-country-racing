package seng201.team005.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import javafx.scene.control.Button;
import seng201.team005.models.Part;
import seng201.team005.models.Car;
import seng201.team005.models.Purchasable;

import java.util.*;

/**
 * Controller class for the shop menu
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
    private final Random random = new Random();
    private boolean showCars = false;

    private final List<Integer> partCosts = new ArrayList<>();
    private final List<Integer> carCosts = new ArrayList<>();
    private final List<Part> parts = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();


    public MenuShopController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/shop_menu.fxml";
    }

    @Override
    protected String getTitle() {
        return "Shop";
    }

    // Clicking on an item in the button and adding to the next
    // available slot in the cart
    private void onUpgradeButtonClicked(ActionEvent event) {
        if (nextSlotidx >= selectedItems.size()) {
            return;
        }

        Button clicked = (Button) event.getSource();
        Purchasable item = (Purchasable) clicked.getUserData();
        ToggleButton slot = selectedItems.get(nextSlotidx);
        slot.setText(clicked.getText());
        slot.setUserData(item);
        slot.setSelected(true);
        nextSlotidx++;
    }

    // Randomly generating parts and cars for the shop.
    // The shop refreshes every iteration, so a new set of
    // cars are available when relaunching the screen
    private void generatePartsandCars() {
        for (Button button : itemButtons) {
            parts.add(new Part(button.getText()));
        }
        for (int i = 0; i < itemButtons.size(); i++) {
            cars.add(new Car());
        }
        Collections.shuffle(cars, random);
    }

    // Click and hover functionality for item buttons
    private void selectedItemSetup() {
        for (int i = 0; i < itemButtons.size(); i++) {
            final int index = i;
            Button button = itemButtons.get(i);
            button.setOnAction(this::onUpgradeButtonClicked);
            button.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) button.getUserData();
                if (item != null) displayStats(item);
            });
        }
    }

    // Click and hover functionality for selected item toggle buttons
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

    // Selection and deselection logic for toggle buttons
    private void toggleClick(int slotIdx, ToggleButton button) {
        if (!button.isSelected()) {
            button.setText("");
            button.setUserData(null);
            nextSlotidx = slotIdx;
        }
        else {
            Purchasable item = (Purchasable) button.getUserData();
            if (item != null) displayStats(item);
        }
    }

    // Managing the purchase cars button to toggle between purchasing
    // cars and purchasing parts
    private void purchaseSwitch() {
        purchaseCarsButton.setOnAction(event -> {
            showCars = !showCars;
            availableLabel.setText(showCars ? "Available Cars:" : "Available Parts:");
            carNameText.setText(showCars ? "Car Stats:" : "Part Stats:");
            purchaseCarsButton.setText(showCars ? "Purchase Parts:" : "Purchase Cars:");
            refreshShopButtons();
        });
    }


    // Updating the shop with
    private void refreshShopButtons() {
        List<? extends Purchasable> items = showCars ? cars : parts;
        for (int i = 0; i < itemButtons.size(); i++) {
            Purchasable item = items.get(i);
            Button button = itemButtons.get(i);
            button.setText(item.shopString());
            button.setUserData(item);
        }
        errorText.setVisible(false);
    }

    // Wiring up the back button to go back to main menu
    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    // Action for clicking the view owned items button
    private void handleOwnedItemsButton() {
        ownedItemsButton.setOnAction(event -> getGameEnvironment().launchScreen(new OwnedItemsController(getGameEnvironment())));
    }

    // Clearing the selected items and resetting insertion index
    private void clearSelectedItems() {
        selectedItems.forEach(item -> {
            item.setSelected(false);
            item.setText("");
            item.setUserData(null);
        });
        nextSlotidx = 0;
        errorText.setVisible(false);
    }

    // Wiring up the buy button for purchasing selected items in the cart
    private void buyButtonSetup() {
        buyButton.setOnAction(event -> {
            List<Purchasable> toBuy = new ArrayList<>();
            for (ToggleButton button : selectedItems) {
                if (button.isSelected()) {
                    Purchasable item = (Purchasable) button.getUserData();
                    if (item != null) toBuy.add(item);
                }
            }
            int costTotal = toBuy.stream().mapToInt(Purchasable::getBuyValue).sum();
            int balance = getGameEnvironment().getMoney();

            if (balance < costTotal) {
                errorText.setText("You have insufficient funds!");
                errorText.setVisible(true);
                return;
            }
            errorText.setVisible(false);
            getGameEnvironment().setMoney(balance - costTotal);
            updatePlayerMoneyText();
            clearSelectedItems();
            recordPurchasedItems(toBuy);
        });
    }

    // When items are successfully purchased from shop, transfer
    // over to the owned items screen
    private void recordPurchasedItems(List<Purchasable> bought) {
        if (showCars) {
            List<Car> ownedCars = getGameEnvironment().getOwnedCars();
            bought.stream().map(purchased ->(Car) purchased).forEach(ownedCars::add);
        }
        else {
            List<Part> ownedParts = getGameEnvironment().getOwnedParts();
            bought.stream().map(purchased -> (Part) purchased).forEach(ownedParts::add);
        }
    }


    @FXML
    public void initialize() {
        itemButtons = List.of(itemButton1, itemButton2, itemButton3, itemButton4, itemButton5);
        selectedItems = List.of(selectedItem1, selectedItem2, selectedItem3);

        generatePartsandCars();
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

