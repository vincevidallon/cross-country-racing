package seng201.team005.gui;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import seng201.team005.GameEnvironment;
import javafx.scene.text.Text;
import seng201.team005.models.Car;
import seng201.team005.models.Purchasable;

import java.util.List;

/**
 * Abstract parent class for all {@link GameEnvironment} UI controller classes.
 * @author seng201 teaching team
 */
public abstract class ScreenController {

    @FXML
    private Text carSpeedText, carHandlingText, carReliabilityText, carFuelEconomyText, carOverallText, carNameText,
            carSpeedLabelText, carHandlingLabelText, carReliabilityLabelText, carFuelEconomyLabelText, carOverallLabelText,
            statTooltipText1, playerMoneyText;

    private Boolean isTooltipShowing = true;


    private final GameEnvironment gameEnvironment;

    /**
     * Creates an instance of a ScreenController with the given {@link GameEnvironment}
     * @param gameEnvironment The game environment used by this ScreenController
     */
    protected ScreenController(final GameEnvironment gameEnvironment) {
        this.gameEnvironment = gameEnvironment;
    }

    /**
     * Gets the FXML file that will be loaded for this controller.
     *
     * @return The full path to the FXML file for this controller
     */
    protected abstract String getFxmlFile();

    /**
     * Gets the screen title for this controller.
     *
     * @return The title to be displayed for this screen
     */
    protected abstract String getTitle();

    /**
     * Gets the game environment associated with this screen controller.
     * @return The game environment for this controller
     */
    protected GameEnvironment getGameEnvironment() {
        return gameEnvironment;
    }

    protected void buttonSelector(List<ToggleButton> buttonList, int buttonIndex) {
        for (int i = 0; i < buttonList.size(); i++) {
            buttonList.get(i).setSelected(i == buttonIndex);
        }
    }

    protected static String convertStatToStars(int num) {
        return (num >= 0 ? "" : "-") + "✪".repeat(Math.abs(num));
    }

    protected void updatePlayerMoneyText() {
        playerMoneyText.setText(String.format("Money: $%s", getGameEnvironment().getMoney()));
    }

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
}

