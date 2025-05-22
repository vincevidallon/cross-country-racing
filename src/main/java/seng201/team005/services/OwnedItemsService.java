package seng201.team005.services;

import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;

import java.util.List;

/**
 * Service class for managing owned items in the game.
 * Provides functionality to sell items and retrieve owned cars and parts.
 */
public class OwnedItemsService {

    /**
     * Sells an item owned by the player and updates the player's balance.
     * Removes the item from the appropriate owned items list in the game environment.
     *
     * @param gameEnvironment The current game environment containing the player's data.
     * @param item The item to be sold, which must implement the Purchasable interface.
     */
    public void sellItem(GameEnvironment gameEnvironment, Purchasable item) {
        if (item instanceof Car) {
            gameEnvironment.getOwnedCars().remove(item);
        }
        else if (item instanceof Part) {
            gameEnvironment.getOwnedParts().remove(item);
        }

        int newBalance = gameEnvironment.getMoney() + item.getSellValue();
        gameEnvironment.setMoney(newBalance);
    }

    /**
     * Retrieves the list of cars owned by the player.
     *
     * @param gameEnvironment The current game environment containing the player's data.
     * @return A list of owned cars.
     */
    public List<Car> getOwnedCars(GameEnvironment gameEnvironment) {
        return gameEnvironment.getOwnedCars();
    }

    /**
     * Retrieves the list of parts owned by the player.
     *
     * @param gameEnvironment The current game environment containing the player's data.
     * @return A list of owned parts.
     */
    public List<Part> getOwnedParts(GameEnvironment gameEnvironment) {
        return gameEnvironment.getOwnedParts();
    }
}