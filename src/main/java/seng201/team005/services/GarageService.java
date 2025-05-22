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
        car.setSpeed(car.getSpeed() + part.getSpeed() > 0 ? car.getSpeed() + part.getSpeed() : 1);
        car.setHandling(car.getHandling() + part.getHandling() > 0 ? car.getHandling() + part.getHandling() : 1);
        car.setReliability(car.getReliability() + part.getReliability() > 0 ? car.getReliability() + part.getReliability() : 1);
        car.setFuelEconomy(car.getFuelEconomy() + part.getFuelEconomy() > 0 ? car.getFuelEconomy() + part.getFuelEconomy() : 1);
        car.setOverall(car.recalculateOverallStats());
        car.setName(car.getName() + "+");
    }
}
