package seng201.team005.models;


import seng201.team005.services.MenuService;

public class Car extends Purchasable {

    public Car(String name) {
        super(name, 1, 6);
    }

    public Car() {
        this("Car" + rng.nextInt(1, 10) + rng.nextInt(1, 10));
    }

    public String mainMenuString() {
        return name + " (" + (overall > 0 ? MenuService.convertStatToStars(overall) : "...") + ")";
    }

}
