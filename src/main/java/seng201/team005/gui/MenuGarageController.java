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
 * Controller for the menu_garage.fxml window
 *
 * @author sha378
 */
public class MenuGarageController extends ScreenController {

    @FXML
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5, selectedCarButton;

    @FXML
    private Button installPartButton, backButton;

    @FXML
    private ListView<Part> partListView;

    private final GarageService garageService = new GarageService(this);
    private List<ToggleButton> carButtons = List.of();
    private List<Car> cars = List.of();
    private Car selectedCar = getGameEnvironment().getSelectedCar();
    private ObservableList<Part> parts;
    private Part selectedPart;

    public MenuGarageController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_garage.fxml";
    }

    @Override
    protected String getTitle() {
        return "Cross Country Racing | Garage";
    }


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


    private void onCarButtonHovered(int buttonIndex, boolean isHovered) {
        if (selectedPart != null) {
            displayCarPlusPartStats(isHovered ? cars.get(buttonIndex) : selectedCar, selectedPart);
        } else {
            displayStats(isHovered ? cars.get(buttonIndex) : selectedCar);
        }
    }

    private void onSelectedCarButtonClicked() {
        selectedCarButton.setSelected(false);
    }

    private void updateCarButtons() {
        for (int i = 0; i < cars.size(); i++) {
            carButtons.get(i).setSelected(cars.get(i) == selectedCar);
            carButtons.get(i).setText(cars.get(i).garageString());
        }
        selectedCarButton.setText(selectedCar.garageString());
    }


    private void onBackButtonClicked() {
        getGameEnvironment().setSelectedCar(selectedCar);
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }

    private void onPartButtonClicked(Part part) {
        installPartButton.setDisable(part == null);
        selectedPart = part;
        if (part != null) {
            displayCarPlusPartStats(selectedCar, part);
        }
    }

    public void onPartButtonHovered(Part part, boolean isHovered) {
        if (isHovered) {
            displayCarPlusPartStats(selectedCar, part);
        } else if (selectedPart != null) {
            displayCarPlusPartStats(selectedCar, selectedPart);
        } else {
            displayStats(selectedCar);
        }
    }

    private void onInstallPartButtonClicked() {
        garageService.installPart(selectedCar, selectedPart);
        getGameEnvironment().removeOwnedPart(selectedPart);
        parts.remove(selectedPart);

        partListView.getSelectionModel().clearSelection();
        displayStats(selectedCar);
        updateCarButtons();
    }



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
