package seng201.team005.services;

import seng201.team005.gui.MenuGarageController;
import seng201.team005.models.Car;
import seng201.team005.models.Part;

/**
 * Service class for managing the garage functionality in the game.
 * Provides methods to interact with the garage controller and manage car upgrades.
 *
 * @author sha378
 */
public class GarageService extends MenuService {
    // The controller for the garage menu.
    private final MenuGarageController garageController;

    /**
     * Constructor for the GarageService class.
     * Initializes the service with the provided garage controller.
     *
     * @param garageController The controller for the garage menu.
     */
    public GarageService(MenuGarageController garageController) {
        this.garageController = garageController;
    }

    /**
     * Retrieves the garage controller associated with this service.
     *
     * @return The MenuGarageController instance.
     */
    public MenuGarageController getGarageController() {
        return garageController;
    }

    /**
     * Installs a part on a car, updating the car's stats accordingly.
     * Ensures that no stat falls below a minimum value of 1.
     * Updates the car's name to indicate the upgrade.
     *
     * @param car The car to which the part will be installed.
     * @param part The part to be installed on the car.
     */
    public void installPart(Car car, Part part) {
        car.setSpeed(car.getSpeed() + part.getSpeed() > 0 ? car.getSpeed() + part.getSpeed() : 1);
        car.setHandling(car.getHandling() + part.getHandling() > 0 ? car.getHandling() + part.getHandling() : 1);
        car.setReliability(car.getReliability() + part.getReliability() > 0 ? car.getReliability() + part.getReliability() : 1);
        car.setFuelEconomy(car.getFuelEconomy() + part.getFuelEconomy() > 0 ? car.getFuelEconomy() + part.getFuelEconomy() : 1);
        car.setOverall(car.recalculateOverallStats());
        car.setName(car.getName() + "+");
    }
}