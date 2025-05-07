package seng201.team005.gui;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;

import java.util.List;

/**
 * Controller for the menu_garage.fxml window
 *
 * @author sha378
 */
public class MenuGarageController extends ScreenController {

    @FXML
    private Text mustSelectCarText;

    @FXML
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5, selectedCarButton;

    @FXML
    private Button installPartButton, backButton;

    @FXML
    private ListView<Part> partListView;

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
        mustSelectCarText.setVisible(false);

        for (int i = 0; i < cars.size(); i++) {
            carButtons.get(i).setSelected(i == buttonIndex);
        }

        if (car == selectedCar) {
            removeCarFromSelected();
        } else {
            selectedCar = car;
            selectedCarButton.setText(car.garageString());
            selectedCarButton.setSelected(false);
        }
    }


    private void removeCarFromSelected() {
        selectedCar = null;
        selectedCarButton.setText("");
        selectedCarButton.setSelected(true);
        updateCarButtons();
    }


    private void onCarButtonHovered(int buttonIndex) {
        displayStats(cars.get(buttonIndex));
    }


    private void updateCarButtons() {
        for (int i = 0; i < cars.size(); i++) {
            carButtons.get(i).setSelected(cars.get(i) == selectedCar);
            carButtons.get(i).setText(cars.get(i).garageString());
        }
        if (selectedCar != null) {
            selectedCarButton.setText(selectedCar.garageString());
        }
    }

    private void onSelectedCarButtonHovered() {
        if (selectedCar != null) {
            displayStats(selectedCar);
        }
    }


    private void onBackButtonClicked() {
        if (selectedCar == null) {
            mustSelectCarText.setVisible(true);
        } else {
            getGameEnvironment().setSelectedCar(selectedCar);
            getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
        }
    }

    private void onPartSelected(Part part) {
        installPartButton.setDisable(part == null);
        selectedPart = part;
        if (part != null) displayStats(part);
    }

    private void onInstallPartButtonClicked() {
        if (selectedCar != null) {
            installPart(selectedCar, selectedPart);
        } else {
            mustSelectCarText.setVisible(true);
        }
        selectedPart = parts.isEmpty() ? null : partListView.getSelectionModel().getSelectedItem();
    }

    private void installPart(Car car, Part part) {
        car.setSpeed(car.getSpeed() + part.getSpeed());
        car.setHandling(car.getHandling() + part.getHandling());
        car.setReliability(car.getReliability() + part.getReliability());
        car.setFuelEconomy(car.getFuelEconomy() + part.getFuelEconomy());
        car.setOverall(car.recalculateOverall());
        parts.remove(part);
        car.setName(car.getName() + "+");
        updateCarButtons();
        displayStats(car);
    }

    public void initialize() {
        cars = getGameEnvironment().getPlayerCars();
        carButtons = List.of(carButton1, carButton2, carButton3, carButton4, carButton5);
        parts = FXCollections.observableArrayList(new Part(), new Part(), new Part(), new Part(), new Part());


        for (int i = 0; i < carButtons.size(); i++) {
            if (i < cars.size()) {
                int buttonIndex = i;
                carButtons.get(i).setOnAction(event ->
                        onCarButtonClicked(buttonIndex, cars.get(buttonIndex)));
                carButtons.get(i).hoverProperty().addListener((observable, oldValue, newValue) ->
                        onCarButtonHovered(buttonIndex));
            } else {
                carButtons.get(i).setText("");
                carButtons.get(i).setSelected(true);
                carButtons.get(i).setDisable(true);
            }

        }

        if (selectedCar != null) {
            selectedCarButton.setText(selectedCar.garageString());
            selectedCarButton.setSelected(false);
        }
        selectedCarButton.setOnAction(event -> removeCarFromSelected());
        selectedCarButton.hoverProperty().addListener((observable, oldValue, newValue) ->
                onSelectedCarButtonHovered());

        partListView.setItems(parts);
        partListView.getSelectionModel().getSelectedItems().addListener(
                (ListChangeListener<Part>) change -> onPartSelected(change.getList().isEmpty() ? null : change.getList().getFirst()));


        installPartButton.setOnAction(event -> onInstallPartButtonClicked());

        backButton.setOnAction(event -> onBackButtonClicked());

        updateCarButtons();
        updatePlayerMoneyText();
    }
}
