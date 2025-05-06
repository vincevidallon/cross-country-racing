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
 * Controller for the menu_setup_cars.fxml window
 *
 * @author sha378
 */
public class MenuSetupCarsController extends ScreenController {

    @FXML
    private ToggleButton carButton1, carButton2, carButton3, carButton4, carButton5,
            selectedCarButton1, selectedCarButton2, selectedCarButton3;

    @FXML
    private Text statTooltipText2;

    @FXML
    private Button goButton;

    private List<ToggleButton> shopCarButtons = List.of();
    private List<ToggleButton> selectedCarButtons = List.of();
    private List<Car> shopCars = List.of();
    private final ArrayList<Car> selectedCars = new ArrayList<>();

    public MenuSetupCarsController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_setup_cars.fxml";
    }

    @Override
    protected String getTitle() {
        return "Cross Country Racing | Cars Setup";
    }


    private void onShopCarButtonClicked(int buttonIndex, Car car) {
        if (selectedCars.contains(car)) {
            removeCarFromSelected(car);
            statTooltipText2.setText("(Click to purchase)");
        }
        else if (selectedCars.size() < 3 && car.getCost() <= getGameEnvironment().getMoney()) {
            getGameEnvironment().setMoney(getGameEnvironment().getMoney() - car.getCost());
            updatePlayerMoneyText();
            selectedCars.add(car);
            updateSelectedCarButtons();
            statTooltipText2.setText("(Click to sell)");
        }
        shopCarButtons.get(buttonIndex).setSelected(selectedCars.contains(car));
    }


    private void updateGoButton() {
        if (selectedCars.isEmpty()) {
            goButton.setDisable(true);
            goButton.setText("Wait...");
        } else {
            goButton.setDisable(false);
            goButton.setText("GO" + "!".repeat(selectedCars.size()));
        }
    }

    private void updateSelectedCarButtons() {
        for (int i = 0; i < selectedCarButtons.size(); i++) {
            selectedCarButtons.get(i).setSelected(i >= selectedCars.size());
            selectedCarButtons.get(i).setText((i < selectedCars.size()) ? selectedCars.get(i).toString() : "");
        }
        updateGoButton();
    }

    private void updateShopCarButtons() {
        for (int i = 0; i < shopCarButtons.size(); i++) {
            shopCarButtons.get(i).setSelected(selectedCars.contains(shopCars.get(i)));
        }
    }

    private void onSelectedCarButtonClicked(int buttonIndex) {
        if (selectedCars.size() > buttonIndex) {
            removeCarFromSelected(selectedCars.get(buttonIndex));
        } else {
            selectedCarButtons.get(buttonIndex).setSelected(true);
        }
    }

    private void removeCarFromSelected(Car car) {
        getGameEnvironment().setMoney(getGameEnvironment().getMoney() + car.getCost());
        updatePlayerMoneyText();
        selectedCars.remove(car);
        updateSelectedCarButtons();
        updateShopCarButtons();
    }

    private void onSelectedCarButtonHovered(int buttonIndex) {
        if (selectedCars.size() > buttonIndex) {
            displayStats(selectedCars.get(buttonIndex));
            statTooltipText2.setText("(Click to sell)");
        }
    }

    private void onShopCarButtonHovered(int buttonIndex) {
        displayStats(shopCars.get(buttonIndex));
        if (selectedCars.contains(shopCars.get(buttonIndex))) {
            statTooltipText2.setText("(Click to sell)");
        } else {
            statTooltipText2.setText("(Click to purchase)");
        }
    }

    @FXML
    private void onGoButtonClicked() {
        getGameEnvironment().setPlayerCars(selectedCars);
        getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment()));
    }

    public void initialize() {
        shopCars = List.of(new Car(), new Car(), new Car(), new Car(), new Car());
        shopCarButtons = List.of(carButton1, carButton2, carButton3, carButton4, carButton5);
        selectedCarButtons = List.of(selectedCarButton1, selectedCarButton2, selectedCarButton3);

        for (int i = 0; i < shopCarButtons.size(); i++) {
            int buttonIndex = i;
            shopCarButtons.get(i).setText(shopCars.get(i).toString());
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
