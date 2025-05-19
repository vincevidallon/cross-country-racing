package seng201.team005.models;

public class Entrant extends Car implements Comparable<Entrant> {
    private int distance = 0;
    private int fuel = 100;
    private int position = -1;

    public Entrant() {
        super();
    }

    public Entrant(Car car) {
        super();
        this.name = car.getName();
        this.speed = car.getSpeed();
        this.handling = car.getHandling();
        this.reliability = car.getReliability();
        this.overall = calculateOverall();
    }

    @Override
    public int compareTo(Entrant entrant) {
        return this.distance - entrant.distance;
    }

    @Override
    public String toString() {
        return "Entrant{" +
                "distance=" + distance +
                ", fuel=" + fuel +
                ", position=" + position +
                ", name='" + name + '\'' +
                '}';
    }

    public int getDistance() {
        return distance;
    }

    public int getFuel() {
        return fuel;
    }

    public void addDistance(int distance) {
        this.distance += distance;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
