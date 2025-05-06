package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;

import java.util.ArrayList;
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

    }


    private void onCarButtonHovered(int buttonIndex) {

    }


    private void onSelectedCarButtonClicked() {

    }

    private void onSelectedCarButtonHovered() {

    }


    public void initialize() {
        cars = getGameEnvironment().getPlayerCars();
        selectedCar = getGameEnvironment().getSelectedCar();

        for (int i = 0; i < carButtons.size(); i++) {
            int buttonIndex = i;
            carButtons.get(i).setText(cars.get(i).toString());
            carButtons.get(i).setOnAction(event ->
                    onCarButtonClicked(buttonIndex, cars.get(buttonIndex)));
            carButtons.get(i).hoverProperty().addListener((observable, oldValue, newValue) ->
                    onCarButtonHovered(buttonIndex));
        }

        selectedCarButton.setOnAction(event -> onSelectedCarButtonClicked());
        selectedCarButton.hoverProperty().addListener((observable, oldValue, newValue) ->
                onSelectedCarButtonHovered());

        updatePlayerMoneyText();
    }
}
