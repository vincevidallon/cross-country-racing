package seng201.team0.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import seng201.team0.GameEnvironment;
import seng201.team0.models.Car;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the menu_setup_cars.fxml window
 *
 * @author sha378
 */
public class MenuSetupCarsController extends ScreenController {

    @FXML
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5,
            selectedCarButton1, selectedCarButton2, selectedCarButton3;

    @FXML
    private Text carSpeedText, carHandlingText, carReliabilityText, carFuelEconomyText, carOverallText, carNameText,
            playerMoneyText;

    private List<ToggleButton> shopCarButtons = List.of();
    private List<ToggleButton> selectedCarButtons = List.of();
    private ArrayList<Car> selectedCars = new ArrayList<>();

    private int preSelectIndex = -1;

    public MenuSetupCarsController(GameEnvironment gameEnvironment) { super(gameEnvironment); }

    @Override
    protected String getFxmlFile() { return "/fxml/menu_setup_cars.fxml"; }

    @Override
    protected String getTitle() { return "Cross Country Racing | Cars Setup"; }


    private void displayStats(Car car) {
        carNameText.setText(String.format("%s stats:", car.getName()));
        carSpeedText.setText(String.format("Speed: %s", car.getSpeed()));
        carHandlingText.setText(String.format("Handling: %s", car.getHandling()));
        carReliabilityText.setText(String.format("Reliability: %s", car.getReliability()));
        carFuelEconomyText.setText(String.format("FuelEconomy: %s", car.getFuelEconomy()));
        carOverallText.setText(String.format("Overall: %s", car.getOverall()));
    }

    private void onShopCarButtonClicked(int buttonIndex, Car car) {
        displayStats(car);
        if (preSelectIndex == buttonIndex) {
            for (ToggleButton selectedCarButton : selectedCarButtons) {
                if (selectedCarButton.getText().isEmpty()) {
                    selectedCars.add(car);
                    selectedCarButton.setText(car.getName());
                    selectedCarButton.setSelected(false);
                    break;
                } else if (selectedCarButton.getText().equals(car.getName())) {
                    break;
                }
            }
        } else preSelectIndex = buttonIndex;
        shopCarButtons.get(buttonIndex).setSelected(selectedCars.contains(car));
    }

    private void onSelectedCarButtonClicked(int buttonIndex) {
        System.out.print(5);
    }

    public void initialize() {
        shopCarButtons = List.of(carButton1, carButton2, carButton3, carButton4, carButton5);
        selectedCarButtons = List.of(selectedCarButton1, selectedCarButton2, selectedCarButton3);

        for (int i = 0; i < shopCarButtons.size(); i++) {
            int buttonIndex = i;
            Car car = new Car();
            shopCarButtons.get(i).setText(car.getName());
            shopCarButtons.get(i).setOnAction(event -> onShopCarButtonClicked(buttonIndex, car));
        }

        for (int i = 0; i < selectedCarButtons.size(); i++) {
            int buttonIndex = i;
            selectedCarButtons.get(i).setOnAction(event -> onSelectedCarButtonClicked(buttonIndex));
        }

        playerMoneyText.setText(String.format("Money: %s", getGameEnvironment().getMoney()));
    }
}
