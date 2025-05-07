package seng201.team005.models;


public class Car extends Purchasable {

    public Car(String name) {
        super(name, 1, 6);
    }

    public Car() {
        this("Car" + rng.nextInt(1, 10) + rng.nextInt(1, 10));
    }

}
