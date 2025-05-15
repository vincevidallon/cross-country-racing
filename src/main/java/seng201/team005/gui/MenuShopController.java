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
    private Button backButton, purchaseCarsButton, buyButton;

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
        ToggleButton slot = selectedItems.get(nextSlotidx);
        slot.setText(clicked.getText());
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
    }

    // Wiring up the back button to go back to main menu
    private void handleBackButton() {
        backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

    // Clearing the selected items and resetting insertion index
    private void clearSelectedItems() {
        selectedItems.forEach(item -> {
            item.setSelected(false);
            item.setText("");
            item.setUserData(null);
        });
        nextSlotidx = 0;
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
                return;
            }
            getGameEnvironment().setMoney(balance - costTotal);
            updatePlayerMoneyText();
            clearSelectedItems();
        });
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


        showCars = false;
        refreshShopButtons();
        updatePlayerMoneyText();
    }
}

        // Mapping the five part names from FXML buttons in the same order as it appears in SceneBuilder
        // as well as generating a random cost for each part
        /*for (Button button : itemButtons) {
            String text = button.getText();
            Part part = new Part(text);
            parts.add(part);
            partCosts.add(random.nextInt(10, 20));
        }

        // Shuffling cars only once, stay the same for the entire iteration
        for (int i = 0; i < itemButtons.size(); i++) {
            cars.add(new Car());
            carCosts.add(random.nextInt(20, 40));
        }
        Collections.shuffle(cars, random);
        Collections.shuffle(carCosts, random);

        // Action for adding a part to the cart when clicked
        itemButtons.forEach(button -> button.setOnAction(this::onUpgradeButtonClicked));

        // Hover functionality for showing item stats in the stats grid pane, not finished yet
        itemButtons.forEach(button -> {
            button.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) button.getUserData();
                if (item != null) displayStats(item);
            });
        });

        for (int i = 0; i < selectedItems.size(); i++) {
            int index = i;
            ToggleButton toggleButton = selectedItems.get(i);
            toggleButton.setOnAction(event -> {
                if (!toggleButton.isSelected()) {
                    toggleButton.setText("");
                    toggleButton.setUserData(null);
                    nextSlotidx = index;
                }
                else {
                    Purchasable purchased = (Purchasable) toggleButton.getUserData();
                    displayStats(purchased);
                }
            });

            toggleButton.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) toggleButton.getUserData();
                if (item != null) displayStats(item);
            });
        }

            // Toggle functionality for switching between purchasing cars and parts
            purchaseCarsButton.setOnAction(event -> {
                if (!showCars) {
                    showCars = true;
                    purchaseCarsButton.setText("Switch to Parts");
                    statsLabel.setText("Car Stats:");
                    availableLabel.setText("Available Cars:");
                    for (int idx = 0; idx < itemButtons.size(); idx++) {
                        Button button = itemButtons.get(idx);
                        Car car = cars.get(idx);
                        button.setText(car.getName());
                        button.setUserData(car);
                    }
                }

                else {
                    showCars = false;
                    purchaseCarsButton.setText("Switch to Cars");
                    statsLabel.setText("Part Stats:");
                    availableLabel.setText("Available Parts:");
                    for (int idx = 0; idx < itemButtons.size(); idx++) {
                        Button button = itemButtons.get(idx);
                        Part part = parts.get(idx);
                        button.setText(part.getName());
                        button.setUserData(part);
                    }
                }
            });

            // Functionality for the buy button
            buyButton.setOnAction(event -> {
                int costTotal = 0;
                List<Purchasable> toBuy = new ArrayList<>();
                for (int i = 0; i < selectedItems.size(); i++) {
                    ToggleButton button = selectedItems.get(i);
                    Purchasable item = (Purchasable) button.getUserData();
                    if (item != null) {
                        toBuy.add(item);
                        costTotal += showCars ? carCosts.get(i) : partCosts.get(i);
                    }
                }
                if (toBuy.isEmpty()) {
                    return;
                }

                int balance = getGameEnvironment().getMoney();
                if (balance < costTotal) {
                    // Warning message here
                    return;
                }

                getGameEnvironment().setMoney(balance - costTotal);
                updatePlayerMoneyText();

            });

            showCars = true;
            purchaseCarsButton.fire();
            backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
    }

}

*/

