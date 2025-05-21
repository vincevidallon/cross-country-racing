package seng201.team005.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.gui.MenuGarageController;
import seng201.team005.models.Car;
import seng201.team005.models.Part;
import seng201.team005.services.GarageService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GarageServiceTest {

    private GarageService garageService;
    private MenuGarageController menuGarageController;

    @BeforeEach
    public void setUp() {
        menuGarageController = mock(MenuGarageController.class);
        garageService = new GarageService(menuGarageController);
    }

    @Test
    void testInstallPartUpdatesCarStats() {
        Car testCar = new Car("Tester");
        testCar.setSpeed(100);
        testCar.setHandling(70);
        testCar.setReliability(80);
        testCar.setFuelEconomy(65);
        testCar.setOverall(testCar.recalculateOverallStats());

        Part testPart = new Part("TestPart");
        testPart.setSpeed(10);
        testPart.setHandling(3);
        testPart.setReliability(5);
        testPart.setFuelEconomy(7);

        garageService.installPart(testCar, testPart);

        assertEquals(110, testCar.getSpeed());
        assertEquals(73, testCar.getHandling());
        assertEquals(85, testCar.getReliability());
        assertEquals(72, testCar.getFuelEconomy());
        assertEquals("Tester+", testCar.getName());
    }
}
