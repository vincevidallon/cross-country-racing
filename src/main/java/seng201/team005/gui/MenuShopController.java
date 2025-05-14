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
    private Label statsLabel;

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

    private final List<Part> parts = new ArrayList<>();
    private final List<Car> cars = new ArrayList<>();


    public MenuShopController(GameEnvironment gameEnvironment) {
        super(gameEnvironment);
    }

    @Override
    protected String getFxmlFile() {
        return "/fxml/menu_shop.fxml";
    }

    @Override
    protected String getTitle() {
        return "Shop";
    }


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


    @FXML
    public void initialize() {
        updatePlayerMoneyText();

        itemButtons = List.of(itemButton1, itemButton2, itemButton3, itemButton4, itemButton5);
        selectedItems = List.of(selectedItem1, selectedItem2, selectedItem3);
        nextSlotidx = 0;

        for (Button button : itemButtons) {
            String text = button.getText();
            Part part = new Part(text);
            parts.add(part);
            button.setUserData(part);
        }

        for (int i = 0; i < itemButtons.size(); i++) {
            cars.add(new Car());
        }
        Collections.shuffle(cars, random);


        itemButtons.forEach(button -> button.setOnAction(this::onUpgradeButtonClicked));

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
                } else {
                    Purchasable purchased = (Purchasable) toggleButton.getUserData();
                    displayStats(purchased);
                }
            });

            toggleButton.setOnMouseEntered(event -> {
                Purchasable item = (Purchasable) toggleButton.getUserData();
                if (item != null) displayStats(item);
            });
        }


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

            showCars = true;
            purchaseCarsButton.fire();
            backButton.setOnAction(event -> getGameEnvironment().launchScreen(new MenuMainController(getGameEnvironment())));
        }

}