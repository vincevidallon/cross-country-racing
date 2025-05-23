package seng201.team005.gui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.services.GarageService;

import java.util.List;

/**
 * Controller class for the garage menu screen.
 * Manages the user interface and interactions for the garage, including car selection,
 * part installation, and displaying car stats.
 * <p>
 * This class interacts with the 'GarageService' to handle business logic and updates
 * the game environment as needed.
 * </p>
 *
 * @author sha378
 */
public class MenuGarageController extends ScreenController {

    // FXML UI components for car selection and part installation.
    @FXML
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5, selectedCarButton;

    @FXML
    private Button installPartButton, backButton;

    @FXML
    private ListView<Part> partListView;

    // Service for managing garage-related functionality.
    private final GarageService garageService = new GarageService(this);

    // List of toggle buttons representing owned cars.
    private List<ToggleButton> carButtons = List.of();

    // List of cars owned by the player.
    private List<Car> cars = List.of();

    // The currently selected car.
    private Car selectedCar = getGameEnvironment().getSelectedCar();

    // Observable list of parts owned by the player.
    private ObservableList<Part> parts;

    // The currently selected part.
    private Part selectedPart;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    public MenuGarageController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    /**
     * Retrieves the FXML file path for the garage menu screen.
     *
     * @return The FXML file path.
     */
    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_garage.fxml";
    }

    /**
     * Retrieves the title for the garage menu screen.
     *
     * @return The screen title.
     */
    @Override
    protected String getTitle() {
        return "Cross Country Racing | Garage";
    }

    /**
     * Handles the event when a car button is clicked.
     * Updates the selected car and its display.
     *
     * @param buttonIndex The index of the clicked button.
     * @param car The car associated with the clicked button.
     */
    private void onCarButtonClicked(int buttonIndex, Car car) {
        for (int i = 0; i < cars.size(); i++) {
            carButtons.get(i).setSelected(i == buttonIndex);
        }

        if (car == selectedCar) {
            onSelectedCarButtonClicked();
        } else {
            selectedCar = car;
            selectedCarButton.setText(car.garageString());
            selectedCarButton.setSelected(false);
        }
    }

    /**
     * Handles the event when a car button is hovered over.
     * Displays the stats of the hovered car, optionally combined with the selected part.
     *
     * @param buttonIndex The index of the hovered button.
     * @param isHovered Whether the button is being hovered over.
     */
    private void onCarButtonHovered(int buttonIndex, boolean isHovered) {
        if (selectedPart != null) {
            displayCarPlusPartStats(isHovered ? cars.get(buttonIndex) : selectedCar, selectedPart);
        } else {
            displayStats(isHovered ? cars.get(buttonIndex) : selectedCar);
        }
    }

    /**
     * Handles the event when the selected car button is clicked.
     * This overrides the default {@link ToggleButton} click functionality,
     * forcing the selectedCarButton to remain unselected.
     */
    private void onSelectedCarButtonClicked() {
        selectedCarButton.setSelected(false);
    }

    /**
     * Updates the car buttons to reflect the current list of owned cars.
     * Selects the button corresponding to the selected car.
     */
    private void updateCarButtons() {
        for (int i = 0; i < cars.size(); i++) {
            carButtons.get(i).setSelected(cars.get(i) == selectedCar);
            carButtons.get(i).setText(cars.get(i).garageString());
        }
        selectedCarButton.setText(selectedCar.garageString());
    }

    /**
     * Handles the event when the back button is clicked.
     * Saves the selected car and navigates back to the main menu.
     */
    private void onBackButtonClicked() {
        getGameEnvironment().setSelectedCar(selectedCar);
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }

    /**
     * Handles the event when a part is clicked in the part list.
     * Updates the selected part and displays its stats combined with the selected car.
     *
     * @param part The selected part.
     */
    private void onPartButtonClicked(Part part) {
        installPartButton.setDisable(part == null);
        selectedPart = part;
        if (part != null) {
            displayCarPlusPartStats(selectedCar, part);
        }
    }

    /**
     * Handles the event when a part is hovered over in the part list.
     * Displays the stats of the hovered part combined with the selected car.
     *
     * @param part The hovered part.
     * @param isHovered Whether the part is being hovered over.
     */
    public void onPartButtonHovered(Part part, boolean isHovered) {
        if (isHovered) {
            displayCarPlusPartStats(selectedCar, part);
        } else if (selectedPart != null) {
            displayCarPlusPartStats(selectedCar, selectedPart);
        } else {
            displayStats(selectedCar);
        }
    }

    /**
     * Handles the event when the 'Install part' button is clicked.
     * Installs the selected part on the selected car and updates the UI to reflect the change.
     */
    private void onInstallPartButtonClicked() {
        garageService.installPart(selectedCar, selectedPart);
        getGameEnvironment().removeOwnedPart(selectedPart);
        parts.remove(selectedPart);

        partListView.getSelectionModel().clearSelection();
        displayStats(selectedCar);
        updateCarButtons();
    }

    /**
     * Initializes the garage menu screen, setting up UI components and data bindings.
     * Configures event listeners for car buttons, part list, and other UI elements.
     */
    public void initialize() {
        cars = getGameEnvironment().getOwnedCars();
        parts = FXCollections.observableArrayList(getGameEnvironment().getOwnedParts());
        carButtons = List.of(carButton1, carButton2, carButton3, carButton4, carButton5);

        for (int i = 0; i < carButtons.size(); i++) {
            if (i < cars.size()) {
                int buttonIndex = i;
                carButtons.get(i).setOnAction(event ->
                        onCarButtonClicked(buttonIndex, cars.get(buttonIndex)));
                carButtons.get(i).hoverProperty().addListener((observable, oldValue, newValue) ->
                        onCarButtonHovered(buttonIndex, newValue));
            } else {
                carButtons.get(i).setText("");
                carButtons.get(i).setSelected(true);
                carButtons.get(i).setDisable(true);
            }
        }

        selectedCarButton.setText(selectedCar.garageString());
        selectedCarButton.setSelected(false);

        selectedCarButton.setOnAction(event -> onSelectedCarButtonClicked());
        selectedCarButton.hoverProperty().addListener((observable, oldValue, newValue) ->
                onCarButtonHovered(cars.indexOf(selectedCar), newValue));

        partListView.setCellFactory(new PartCellFactory(garageService));
        partListView.setItems(parts);

        partListView.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Part>) change -> onPartButtonClicked(change.getList().isEmpty() ? null : change.getList().getFirst()));

        installPartButton.setOnAction(event -> onInstallPartButtonClicked());

        backButton.setOnAction(event -> onBackButtonClicked());

        updateCarButtons();
        updatePlayerMoneyText();
        displayStats(selectedCar);
    }
}