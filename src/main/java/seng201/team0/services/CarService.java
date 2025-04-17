package seng201.team0.services;


import seng201.team0.models.Car;


public class CarService {
    private final Car car;

    public CarService() { car = new Car(); }

    public Car getCar() { return car; }

    public static void main(String[] args) {
        CarService carService = new CarService();
        System.out.println(carService.getCar());
    }
}
