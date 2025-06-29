package seng201.team005.services;

import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Service class for handling shop-related game logic
 * <p>
 *     Handles the generation of random parts and cars for purchase,
 *     checks if the player can afford selected items in the cart
 *     , performs purchase action by deducting money out of the user
 *     balance and updates the player's inventory.
 * </p>
 *
 * @author vvi29
 */

public class ShopService {
    private static final Random random = new Random();

    /**
     * Generates a list of randomly generated {@link Part} objects.
     *
     * @param count the number of Part objects to be generated
     * @return a shuffled list of Part instances
     */
    public static List<Part> generateParts(int count) {
        List<Part> partList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            partList.add(new Part());
        }
        Collections.shuffle(partList, random);
        return partList;
    }

    /**
     * Generates a shuffled list of randomly generated {@link Car} objects.
     *
     * @param count the number of Car objects to be generated
     * @return a shuffled list of Car instances
     */
    public static List<Car> generateCars(int count) {
        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            carList.add(new Car());
        }
        Collections.shuffle(carList, random);
        return carList;
    }

    /**
     * A method which checks whether the player has enough money to afford selected items
     * in the cart.
     * @param gameEnvironment the game environment which contains the player's current balance
     * @param items the list of items to be purchased
     * @return true if the player can afford the total cost of the items, false otherwise
     */
    public boolean canAfford(GameEnvironment gameEnvironment, List<Purchasable> items) {
        int total = items.stream().mapToInt(Purchasable::getBuyValue).sum();
        return gameEnvironment.getMoney() >= total;
    }

    /**
     * Processes purchase of items by deducting the total cost of the items
     * from the player's balance. Adds purchased items to the appropriate
     * inventory list.
     * @param gameEnvironment
     * @param items
     */
    public void purchaseItem(GameEnvironment gameEnvironment, List<Purchasable> items) {
        int totalCost = items.stream().mapToInt(Purchasable::getBuyValue).sum();
        gameEnvironment.setMoney(gameEnvironment.getMoney() - totalCost);

        for (Purchasable item : items) {
            if (item instanceof Car) {
                gameEnvironment.getOwnedCars().add((Car) item);
            }
            else if (item instanceof Part) {
                gameEnvironment.getOwnedParts().add((Part) item);
            }

            item.setPurchased(true);
        }
    }
}
