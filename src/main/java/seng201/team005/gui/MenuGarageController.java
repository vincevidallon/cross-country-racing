package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
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
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5, selectedCarButton;

    @FXML
    private Button installPartButton, backButton;

    @FXML
    private ListView<Part> partListView;

    private List<ToggleButton> carButtons = List.of();
    private List<Car> cars = List.of();
    private Car selectedCar;


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
        buttonSelector(carButtons, buttonIndex);

        selectedCar = car;
        selectedCarButton.setText(car.getName());
        selectedCarButton.setSelected(false);
    }


    private void onCarButtonHovered(int buttonIndex) {
        displayStats(cars.get(buttonIndex));
    }


    private void onSelectedCarButtonClicked() {
        selectedCar = null;
        selectedCarButton.setText("");
        selectedCarButton.setSelected(true);
    }


    private void onSelectedCarButtonHovered() {
        if (selectedCar != null) {
            displayStats(selectedCar);
        }
    }

    @FXML
    private void onBackButtonClicked() {
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }


    public void initialize() {
        cars = getGameEnvironment().getPlayerCars();
        carButtons = List.of(carButton1, carButton2, carButton3, carButton4, carButton5);
        selectedCar = getGameEnvironment().getSelectedCar();

        for (int i = 0; i < carButtons.size(); i++) {
            if (i < cars.size()) {
                int buttonIndex = i;
                carButtons.get(i).setText(cars.get(i).toString());
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

        selectedCarButton.setOnAction(event -> onSelectedCarButtonClicked());
        selectedCarButton.hoverProperty().addListener((observable, oldValue, newValue) ->
                onSelectedCarButtonHovered());

        updatePlayerMoneyText();
    }
}
