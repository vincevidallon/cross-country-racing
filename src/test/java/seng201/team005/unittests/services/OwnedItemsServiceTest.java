package seng201.team005.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.GameEnvironment;
import seng201.team005.gui.ScreenNavigator;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.services.OwnedItemsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

public class OwnedItemsServiceTest {

    private OwnedItemsService ownedItemsService;
    private GameEnvironment gameEnvironment;

    private Car testCar;
    private Part testPart;

    @BeforeEach
    void setUp() {
        ScreenNavigator screenNavigator = mock(ScreenNavigator.class);

        gameEnvironment = new GameEnvironment(screenNavigator);

        gameEnvironment.setMoney(2500);

        testCar = new Car("TesterCar");
        testCar.setSellValue(500);
        gameEnvironment.getOwnedCars().add(testCar);

        testPart = new Part("TesterPart");
        testPart.setSellValue(300);
        gameEnvironment.getOwnedParts().add(testPart);

        ownedItemsService = new OwnedItemsService();
    }

    @Test
    void testSellCar() {
        int initialMoney = gameEnvironment.getMoney();
        ownedItemsService.sellItem(gameEnvironment, testCar);
        assertFalse(gameEnvironment.getOwnedCars().contains(testCar),
                "Car should be sold and not in owned cars");

        assertEquals(initialMoney + testPart.getSellValue(), gameEnvironment.getMoney());
    }
}
