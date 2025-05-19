package seng201.team005.services;

import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;

import java.util.List;

public class OwnedItemsService {

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

    public List<Car> getOwnedCars(GameEnvironment gameEnvironment) {
        return gameEnvironment.getOwnedCars();
    }

    public List<Part> getOwnedParts(GameEnvironment gameEnvironment) {
        return gameEnvironment.getOwnedParts();
    }
}
