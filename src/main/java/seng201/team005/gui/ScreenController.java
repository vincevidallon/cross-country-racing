package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;
import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;

import java.util.List;

import static seng201.team005.services.MenuService.convertStatToStars;
import static seng201.team005.services.MenuService.partAdjustStars;

/**
 * Abstract parent class for all {@link GameEnvironment} UI controller classes.
 * <p>
 * This class provides common functionality and properties for managing the game's UI screens.
 * It includes methods for updating UI elements, handling button selections, and displaying
 * stats for various game objects.
 * </p>
 *
 * @author seng201 teaching team
 */
public abstract class ScreenController {

    // FXML UI components for displaying car stats and player money.
    @FXML
    private Text carSpeedText, carHandlingText, carReliabilityText, carFuelEconomyText, carOverallText, carNameText,
            carSpeedLabelText, carHandlingLabelText, carReliabilityLabelText, carFuelEconomyLabelText, carOverallLabelText,
            statTooltipText1, playerMoneyText;

    // Boolean flag to track whether the tooltip is currently showing.
    private Boolean isTooltipShowing = true;

    // Reference to the game environment, providing access to game state and logic.
    private final GameEnvironment gameEnvironment;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}.
     *
     * @param gameEnvironment The game environment used by this ScreenController.
     */
    protected ScreenController(final GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller.
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen.
     */
    protected abstract String getTitle();

    /**
     * Gets the game environment associated with this screen controller.
     *
     * @return The game environment for this controller.
     */
    protected GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    /**
     * Handles the quit request by exiting the application.
     */
    public void onQuitRequested() {
        System.exit(0);
    }

    /**
     * Updates the selection state of a list of toggle buttons.
     * <p>
     * The button at the specified index is selected, and all other buttons are deselected.
     * </p>
     *
     * @param buttonList  The list of toggle buttons to update.
     * @param buttonIndex The index of the button to select.
     */
    protected void buttonSelector(List<ToggleButton> buttonList, int buttonIndex) {
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setSelected(i == buttonIndex);
        }
    }

    /**
     * Updates the player's money display in the UI.
     */
    protected void updatePlayerMoneyText() {
        playerMoneyText.setText(String.format("Money: $%s", getGameEnvironment().getMoney()));
    }

    /**
     * Displays the stats of a given {@link Purchasable} object in the UI.
     * <p>
     * This method updates the UI elements to show the stats of the specified purchasable item,
     * such as speed, handling, reliability, fuel economy, and overall performance.
     * </p>
     *
     * @param purchasable The purchasable item whose stats are to be displayed.
     */
    protected void displayStats(Purchasable purchasable) {
        if (isTooltipShowing) {
            statTooltipText1.setVisible(false);
            carSpeedLabelText.setVisible(true);
            carHandlingLabelText.setVisible(true);
            carReliabilityLabelText.setVisible(true);
            carFuelEconomyLabelText.setVisible(true);
            carOverallLabelText.setVisible(true);
            isTooltipShowing = false;
        }
        carNameText.setText(String.format("%s stats:", purchasable.getName()));
        carSpeedText.setText(convertStatToStars(purchasable.getSpeed()));
        carHandlingText.setText(convertStatToStars(purchasable.getHandling()));
        carReliabilityText.setText(convertStatToStars(purchasable.getReliability()));
        carFuelEconomyText.setText(convertStatToStars(purchasable.getFuelEconomy()));
        carOverallText.setText(convertStatToStars(purchasable.getOverall()));
    }

    /**
     * Displays the combined stats of a {@link Car} and a {@link Part} in the UI.
     * <p>
     * This method calculates the adjusted stats of the car when the part is applied
     * and updates the UI elements to show the combined stats.
     * </p>
     *
     * @param car  The car whose stats are to be displayed.
     * @param part The part to be applied to the car.
     */
    protected void displayCarPlusPartStats(Car car, Part part) {
        if (!isTooltipShowing) {
            carNameText.setText(String.format("%s stats:", car.getName()));
            carSpeedText.setText(partAdjustStars(car.getSpeed(), part.getSpeed(), 6));
            carHandlingText.setText(partAdjustStars(car.getHandling(), part.getHandling(), 6));
            carReliabilityText.setText(partAdjustStars(car.getReliability(), part.getReliability(), 6));
            carFuelEconomyText.setText(partAdjustStars(car.getFuelEconomy(), part.getFuelEconomy(), 6));
            int overallAdjust = (car.getSpeed() + part.getSpeed() + car.getHandling() + part.getHandling() +
                    car.getReliability() + part.getReliability() + car.getFuelEconomy() + part.getFuelEconomy()) / 4 - car.getOverall();
            carOverallText.setText(partAdjustStars(car.getOverall(), overallAdjust, 6));
        }
    }

    /**
     * Adds the specified amount of money to the player's total.
     *
     * @param money The amount of money to add.
     */
    public void addMoney(int money) {
        getGameEnvironment().setMoney(gameEnvironment.getMoney() + money);
    }
}