package seng201.team005.models;

public class Entrant extends Car implements Comparable<Entrant> {
    private int distance = 0;
    private int fuel = 100;
    private int position = -1;
    private int fuelStopsPassed = 0;
    private boolean isStopped = false;
    private boolean isBrokenDown = false;
    private boolean isFinished = false;


    public Entrant() {
        super();
    }

    public Entrant(Car car) {
        this.name = car.getName();
        this.speed = car.getSpeed();
        this.handling = car.getHandling();
        this.reliability = car.getReliability();
        this.fuelEconomy = car.getFuelEconomy();
        this.overall = calculateOverall();
    }

    public String positionString() {
        if (position % 10 == 1 && position != 11) return position + "st";
        if (position % 10 == 2 && position != 12) return position + "nd";
        if (position % 10 == 3 && position != 13) return position + "rd";
        return position + "th";
    }

    public String leaderboardString() {
        return positionString() + "\n" + name + "\n" + distance + " km";
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

    public boolean isStopped() {
        return isStopped;
    }

    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    public boolean isBrokenDown() {
        return isBrokenDown;
    }

    public void setBrokenDown(boolean brokenDown) {
        isBrokenDown = brokenDown;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getFuelStopsPassed() {
        return fuelStopsPassed;
    }

    public void setFuelStopsPassed(int fuelStopsPassed) {
        this.fuelStopsPassed = fuelStopsPassed;
    }
}
