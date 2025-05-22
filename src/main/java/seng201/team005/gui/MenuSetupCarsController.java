package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the menu_setup_cars.fxml window.
 * Handles the logic for selecting cars, updating UI elements, and managing the player's car selection.
 * Extends the ScreenController class to integrate with the game's screen management system.
 *
 * Features:
 * - Allows players to select cars from a shop.
 * - Ensures players cannot exceed the maximum number of selected cars.
 * - Updates UI elements dynamically based on player actions.
 * - Handles insufficient funds and other edge cases.
 * - Integrates with the GameEnvironment to manage game state.
 *
 * Author: sha378
 */
public class MenuSetupCarsController extends ScreenController {

    // List of cars currently selected by the player.
    private final ArrayList<Car> selectedCars = new ArrayList<>();

    // FXML-injected UI elements for car selection and display.
    @FXML
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5,
            selectedCarButton1, selectedCarButton2, selectedCarButton3;
    @FXML
    private Text statTooltipText2, errorText;
    @FXML
    private Button goButton;

    // Lists to manage shop car buttons, selected car buttons, and shop cars.
    private List<ToggleButton> shopCarButtons = List.of();
    private List<ToggleButton> selectedCarButtons = List.of();
    private List<Car> shopCars = List.of();

    /**
     * Constructor for the MenuSetupCarsController.
     *
     * @param gameEnvironment The GameEnvironment instance to manage game state and data.
     */
    public MenuSetupCarsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Returns the path to the FXML file associated with this controller.
     *
     * @return The FXML file path as a String.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_setup_cars.fxml";
    }

    /**
     * Returns the title of the window for this screen.
     *
     * @return The window title as a String.
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing | Cars Setup";
    }

    /**
     * Handles the logic when a shop car button is clicked.
     *
     * @param buttonIndex The index of the button clicked.
     * @param car The car associated with the clicked button.
     */
    private void onShopCarButtonClicked(int buttonIndex, Car car) {
        errorText.setText("");
        if (selectedCars.contains(car)) {
            removeCarFromSelected(car);
            statTooltipText2.setText("(Click to purchase)");
        } else if (selectedCars.size() == 3) {
            errorText.setText("You can't have more cars!");
        } else if (car.getBuyValue() > getGameEnvironment().getMoney()) {
            errorText.setText("You have insufficient funds!");
        } else {
            getGameEnvironment().setMoney(getGameEnvironment().getMoney() - car.getBuyValue());
            updatePlayerMoneyText();
            selectedCars.add(car);
            updateSelectedCarButtons();
            statTooltipText2.setText("(Click to sell)");
        }
        shopCarButtons.get(buttonIndex).setSelected(selectedCars.contains(car));
    }

    /**
     * Updates the state and text of the "Go" button based on the number of selected cars.
     */
    private void updateGoButton() {
        if (selectedCars.isEmpty()) {
            goButton.setDisable(true);
            goButton.setText("Wait...");
        } else {
            goButton.setDisable(false);
            goButton.setText("GO" + "!".repeat(selectedCars.size()));
        }
    }

    /**
     * Updates the selected car buttons to reflect the current selection.
     */
    private void updateSelectedCarButtons() {
        for (int i = 0; i < selectedCarButtons.size(); i++) {
            selectedCarButtons.get(i).setSelected(i >= selectedCars.size());
            selectedCarButtons.get(i).setText((i < selectedCars.size()) ? selectedCars.get(i).shopString() : "");
        }
        updateGoButton();
    }

    /**
     * Updates the shop car buttons to reflect the current selection.
     */
    private void updateShopCarButtons() {
        for (int i = 0; i < shopCarButtons.size(); i++) {
            shopCarButtons.get(i).setSelected(selectedCars.contains(shopCars.get(i)));
        }
    }

    /**
     * Handles the logic when a selected car button is clicked.
     *
     * @param buttonIndex The index of the button clicked.
     */
    private void onSelectedCarButtonClicked(int buttonIndex) {
        errorText.setText("");
        if (selectedCars.size() > buttonIndex) {
            removeCarFromSelected(selectedCars.get(buttonIndex));
        } else {
            selectedCarButtons.get(buttonIndex).setSelected(true);
        }
    }

    /**
     * Removes a car from the selected cars list and updates the UI.
     *
     * @param car The car to be removed.
     */
    private void removeCarFromSelected(Car car) {
        getGameEnvironment().setMoney(getGameEnvironment().getMoney() + car.getBuyValue());
        updatePlayerMoneyText();
        selectedCars.remove(car);
        updateSelectedCarButtons();
        updateShopCarButtons();
    }

    /**
     * Displays stats and updates the tooltip when a selected car button is hovered over.
     *
     * @param buttonIndex The index of the button hovered over.
     */
    private void onSelectedCarButtonHovered(int buttonIndex) {
        if (selectedCars.size() > buttonIndex) {
            displayStats(selectedCars.get(buttonIndex));
            statTooltipText2.setText("(Click to sell)");
        }
    }

    /**
     * Displays stats and updates the tooltip when a shop car button is hovered over.
     *
     * @param buttonIndex The index of the button hovered over.
     */
    private void onShopCarButtonHovered(int buttonIndex) {
        displayStats(shopCars.get(buttonIndex));
        if (selectedCars.contains(shopCars.get(buttonIndex))) {
            statTooltipText2.setText("(Click to sell)");
        } else {
            statTooltipText2.setText("(Click to purchase)");
        }
    }

    /**
     * Handles the logic when the "Go" button is clicked.
     * Sets the selected cars in the GameEnvironment and transitions to the main menu.
     */
    @FXML
    private void onGoButtonClicked() {
        getGameEnvironment().setOwnedCars(selectedCars);
        getGameEnvironment().setSelectedCar(selectedCars.getFirst());
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }

    /**
     * Initializes the controller by setting up shop cars, buttons, and event listeners.
     */
    public void initialize() {
        shopCars = List.of(new Car(5), new Car(5), new Car(5), new Car(5), new Car(5));
        shopCarButtons = List.of(carButton1, carButton2, carButton3, carButton4, carButton5);
        selectedCarButtons = List.of(selectedCarButton1, selectedCarButton2, selectedCarButton3);

        for (int i = 0; i < shopCarButtons.size(); i++) {
            int buttonIndex = i;
            shopCarButtons.get(i).setText(shopCars.get(i).shopString());
            shopCarButtons.get(i).setOnAction(event ->
                    onShopCarButtonClicked(buttonIndex, shopCars.get(buttonIndex)));
            shopCarButtons.get(i).hoverProperty().addListener((observable, oldValue, newValue) ->
                    onShopCarButtonHovered(buttonIndex));
        }

        for (int i = 0; i < selectedCarButtons.size(); i++) {
            int buttonIndex = i;
            selectedCarButtons.get(i).setOnAction(event -> onSelectedCarButtonClicked(buttonIndex));
            selectedCarButtons.get(i).hoverProperty().addListener((observable, oldValue, newValue) ->
                    onSelectedCarButtonHovered(buttonIndex));
        }

        updatePlayerMoneyText();
    }
}