package seng201.team005.unittests.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import seng201.team005.models.Car;
import seng201.team005.services.CarService;

import static org.junit.jupiter.api.Assertions.*;

public class CarServiceTest {

    private CarService carService;

    @BeforeEach
    void setUp() {
        carService = new CarService();
    }

    @Test
    void getCarTestReturnsNonNullCar() {
        Car testCar = carService.getCar();
        assertNotNull(testCar);
    }

    @Test
    void getCarReturnsCarInstance() {
        Car testCar1 = carService.getCar();
        Car testCar2 = carService.getCar();
        assertSame(testCar1, testCar2);
    }

    @Test
    void carHasGeneratedName() {
        Car testCar = carService.getCar();
        assertNotNull(testCar.getName());
        assertTrue(testCar.getName().startsWith("Car"));
        }
}

