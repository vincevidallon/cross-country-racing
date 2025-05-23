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
 * Controller class for the car setup menu screen.
 * Manages the user interface and interactions for selecting and purchasing cars
 * before starting the game. Handles car selection, purchase validation, and
 * updates to the player's money and list of cars.
 *
 * @author sha378
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
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    public MenuSetupCarsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_setup_cars.fxml";
    }

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen.
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing | Cars Setup";
    }

    /**
     * Handles the event when a shop car button is clicked.
     * Adds or removes the car from the selected list, validates purchase conditions,
     * and updates the player's money and UI.
     *
     * @param buttonIndex The index of the clicked button.
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
     * Updates the "Go" button based on the number of selected cars.
     * Disables the button if no cars are selected and adjusts the button text dynamically.
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
     * Updates the selected car buttons to reflect the current list of selected cars.
     * Adjusts the button text and selection state dynamically.
     */
    private void updateSelectedCarButtons() {
        for (int i = 0; i < selectedCarButtons.size(); i++) {
            selectedCarButtons.get(i).setSelected(i >= selectedCars.size());
            selectedCarButtons.get(i).setText((i < selectedCars.size()) ? selectedCars.get(i).shopString() : "");
        }
        updateGoButton();
    }

    /**
     * Updates the shop car buttons to reflect the current selection state of cars.
     * Highlights the buttons for cars that are already selected.
     */
    private void updateShopCarButtons() {
        for (int i = 0; i < shopCarButtons.size(); i++) {
            shopCarButtons.get(i).setSelected(selectedCars.contains(shopCars.get(i)));
        }
    }

    /**
     * Handles the event when a selected car button is clicked.
     * Removes the car from the selected list or ensures the button remains selected.
     *
     * @param buttonIndex The index of the clicked button.
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
     * Removes a car from the selected list, refunds the player's money,
     * and updates the UI to reflect the change.
     *
     * @param car The car to be removed from the selected list.
     */
    private void removeCarFromSelected(Car car) {
        getGameEnvironment().setMoney(getGameEnvironment().getMoney() + car.getBuyValue());
        updatePlayerMoneyText();
        selectedCars.remove(car);
        updateSelectedCarButtons();
        updateShopCarButtons();
    }

    /**
     * Handles the event when a selected car button is hovered over.
     * Displays the stats of the hovered car and updates the tooltip text.
     *
     * @param buttonIndex The index of the hovered button.
     */
    private void onSelectedCarButtonHovered(int buttonIndex) {
        if (selectedCars.size() > buttonIndex) {
            displayStats(selectedCars.get(buttonIndex));
            statTooltipText2.setText("(Click to sell)");
        }
    }

    /**
     * Handles the event when a shop car button is hovered over.
     * Displays the stats of the hovered car and updates the tooltip text
     * based on whether the car is selected or available for purchase.
     *
     * @param buttonIndex The index of the hovered button.
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
     * Handles the event when the "Go" button is clicked.
     * Finalizes the car selection, updates the game environment, and navigates to the main menu.
     */
    @FXML
    private void onGoButtonClicked() {
        getGameEnvironment().setOwnedCars(selectedCars);
        getGameEnvironment().setSelectedCar(selectedCars.getFirst());
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }

    /**
     * Initializes the car setup menu screen.
     * Configures the shop and selected car buttons, sets up event listeners,
     * and initializes the player's money and car stats display.
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