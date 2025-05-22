package seng201.team005.models;


import seng201.team005.services.MenuService;

public class Car extends Purchasable {

    public Car(int statCap) {
        super("Car" + rng.nextInt(1, 10) + rng.nextInt(1, 10), 1, statCap);
    }

    public Car() {
        this(6);
    }

    public String mainMenuString() {
        return name + " (" + (overall > 0 ? MenuService.convertStatToStars(overall) : "...") + ")";
    }

}
