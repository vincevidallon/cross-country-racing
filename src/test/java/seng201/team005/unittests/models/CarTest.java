package seng201.team005.unittests.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import seng201.team005.models.Car;

public class CarTest {
    @Test
    void testCarConstructorWithName() {
        Car car = new Car();
        assertTrue(car.getSpeed() >= 1 && car.getSpeed() < 6);
        assertTrue(car.getHandling() >= 1 && car.getHandling() < 6);
        assertTrue(car.getReliability() >= 1 && car.getReliability() < 6);
        assertTrue(car.getFuelEconomy() >= 1 && car.getFuelEconomy() < 6);
        assertEquals(car.getOverall(), (car.getSpeed() + car.getHandling() + car.getReliability() + car.getFuelEconomy()) / 4);
        assertTrue(car.getBuyValue() >= 1);
        assertTrue(car.getSellValue() >= 1);
    }

    @Test
    void testCarDefaultConstructor() {
        Car car = new Car();
        assertNotNull(car.getName());
        assertTrue(car.getName().startsWith("Car"));
        assertTrue(car.getSpeed() >= 1 && car.getSpeed() < 6);
        assertTrue(car.getHandling() >= 1 && car.getHandling() < 6);
        assertTrue(car.getReliability() >= 1 && car.getReliability() < 6);
        assertTrue(car.getFuelEconomy() >= 1 && car.getFuelEconomy() < 6);
    }

    @Test
    void testBuyAndSellValueLogic() {
        Car car = new Car();
        assertTrue(car.getBuyValue() >= 1);
        assertTrue(car.getSellValue() >= 1);
        assertTrue(car.getBuyValue() >= car.getSellValue());
    }
}
