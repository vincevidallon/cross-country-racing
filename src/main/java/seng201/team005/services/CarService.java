package seng201.team005.services;

import seng201.team005.models.Car;

/**
 * Service class for managing a single car instance.
 * Provides functionality to retrieve the car and includes a main method for demonstration purposes.
 */
public class CarService {
    // The car instance managed by this service.
    private final Car car;

    /**
     * Constructor for the CarService class.
     * Initializes the service with a new Car instance.
     */
    public CarService() {
        car = new Car();
    }

    /**
     * Retrieves the car instance managed by this service.
     *
     * @return The car instance.
     */
    public Car getCar() {
        return car;
    }

    /**
     * Main method for demonstrating the functionality of the CarService class.
     * Creates a CarService instance and prints the car instance to the console.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        CarService carService = new CarService();
        System.out.println(carService.getCar());
    }
}