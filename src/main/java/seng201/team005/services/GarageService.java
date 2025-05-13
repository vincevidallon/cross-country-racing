package seng201.team005.services;

import seng201.team005.gui.MenuGarageController;
import seng201.team005.models.Car;
import seng201.team005.models.Part;

public class GarageService extends MenuService {
    private final MenuGarageController garageController;

    public GarageService(MenuGarageController garageController) {
        this.garageController = garageController;
    }

    public MenuGarageController getGarageController() {
        return garageController;
    }

    public void installPart(Car car, Part part) {
        car.setSpeed(car.getSpeed() + part.getSpeed());
        car.setHandling(car.getHandling() + part.getHandling());
        car.setReliability(car.getReliability() + part.getReliability());
        car.setFuelEconomy(car.getFuelEconomy() + part.getFuelEconomy());
        car.setOverall(car.recalculateOverall());
        car.setName(car.getName() + "+");
    }
}
