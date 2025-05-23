package seng201.team005.unittests.services;


import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.GameEnvironment;
import seng201.team005.gui.ScreenController;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.models.Purchasable;
import seng201.team005.services.ShopService;
import seng201.team005.gui.ScreenNavigator;


import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


public class ShopServiceTest {
    private ShopService shopService;
    private GameEnvironment gameEnvironment;


    @BeforeEach
    void setUp() {
        ScreenNavigator screenNavigator = mock(ScreenNavigator.class);
        gameEnvironment = new GameEnvironment(screenNavigator);
        gameEnvironment.onSetupComplete("Tester", 7, 1);
        gameEnvironment.getOwnedCars().clear();
        gameEnvironment.getOwnedParts().clear();

        shopService = new ShopService();
    }

    @Test
    void generatePartsTest() {
        int count = 5;
        var parts = ShopService.generateParts(count);

        assertEquals(count, parts.size(), "should generate exactly " + count + " parts");
        assertTrue(parts.stream().allMatch(Objects::nonNull),
                "no null elements.");
        assertTrue(parts.stream().allMatch(part -> part instanceof Part),
                "all elements are Part objects");
    }


    @Test
    void generateCarsTest() {
        int count = 7;
        var cars = ShopService.generateCars(count);

        assertEquals(count, cars.size(), "should generate exactly " + count + " cars");
        assertTrue(cars.stream().allMatch(Objects::nonNull),
                "no null elements.");
        assertTrue(cars.stream().allMatch(car -> car instanceof Car),
                "all elements are Car objects");
    }


    static class StubItem extends Purchasable {
        private final int price;
        StubItem(int price) {
            super("Stub", 0, 1);
            this.price = price;
        }
        @Override
        public int getBuyValue() {
            return price;
        }
    }

    @Test
    void canAffordTest() {
        List<Purchasable> items = List.of(new StubItem(100), new StubItem(300));
        int costTotal = items.stream().mapToInt(Purchasable::getBuyValue).sum();

        gameEnvironment.setMoney(costTotal);
        assertTrue(shopService.canAfford(gameEnvironment, items),
                "Expected to afford " + costTotal + " when user money == " + costTotal);

        gameEnvironment.setMoney(costTotal + 200);
        assertTrue(shopService.canAfford(gameEnvironment, items),
                "Expected to afford " + costTotal + " when user money > " + costTotal);
    }

    @Test
    void purchaseItemTest() {
        Car testCar = new Car();
        Part testPart = new Part();
        int carCost = testCar.getBuyValue();
        int partCost = testPart.getBuyValue();
        int userMoney = gameEnvironment.getMoney();
        List<Purchasable> items = List.of(testCar, testPart);

        shopService.purchaseItem(gameEnvironment, items);

        assertEquals(userMoney - (carCost + partCost), gameEnvironment.getMoney(),
                "Method purchaseItem should deduct the sum of buy-values from the user's current balance");

        assertTrue(gameEnvironment.getOwnedCars().contains(testCar),
                "Purchased car should be added to ownedCars List");

        assertTrue(gameEnvironment.getOwnedParts().contains(testPart),
                "Purchased part should be added to ownedParts List");

        assertTrue(gameEnvironment.getOwnedParts().stream().allMatch(Part::isPurchased),
                "All purchased parts should be marked as purchased");
    }
}



