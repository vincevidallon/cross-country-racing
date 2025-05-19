package seng201.team005.services;

import seng201.team005.GameEnvironment;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShopService {
    private final Random random = new Random();

    public List<Part> generateParts(List<String> parts) {
        List<Part> partList = new ArrayList<>();
        for (String part : parts) {
            partList.add(new Part(part));
        }
        return partList;
    }

    public List<Car> generateCars(int count) {
        List<Car> carList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            carList.add(new Car());
        }
        Collections.shuffle(carList, random);
        return carList;
    }

    public boolean canAfford(GameEnvironment gameEnvironment, List<Purchasable> items) {
        int total = items.stream().mapToInt(Purchasable::getBuyValue).sum();
        return gameEnvironment.getMoney() >= total;
    }

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
        }
    }
}
