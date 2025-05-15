package seng201.team005.models;


public class Part extends Purchasable {

    public Part(String name) {
        super(name, -1, 4);
        buyValue += 2;
    }

    public Part() {
        this("Part" + rng.nextInt(1, 10) + rng.nextInt(1, 10));
    }

    @Override
    public String toString() {
        return this.name;
    }
}
