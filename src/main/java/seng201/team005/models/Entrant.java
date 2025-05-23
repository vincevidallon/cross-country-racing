package seng201.team005.models;

/**
 * Represents an entrant in a race, extending the Car class and implementing the Comparable interface.
 * This class tracks the entrant's progress, fuel, position, and status during the race.
 */
public class Entrant extends Car implements Comparable<Entrant> {
    private int distance = 0; // The distance traveled by the entrant in kilometers.
    private int fuel = 100; // The current fuel level of the entrant.
    private int position = -1; // The position of the entrant in the race.
    private int fuelStopsPassed = 0; // The number of fuel stops passed by the entrant.
    private boolean isStopped = false; // Whether the entrant is currently stopped.
    private boolean isBrokenDown = false; // Whether the entrant is currently broken down.
    private boolean isFinished = false; // Whether the entrant has finished the race.

    /**
     * Default constructor for Entrant.
     */
    public Entrant(Route.Terrain terrain) {
        super();

        terrainStatModification(terrain);
    }

    public Entrant(Route.Terrain terrain, int difficulty) {
        super(difficulty);

        terrainStatModification(terrain);
    }

    /**
     * Constructs an Entrant based on an existing Car object.
     *
     * @param car The Car object to base the Entrant on.
     */
    public Entrant(Car car, Route.Terrain terrain) {
        this.name = car.getName();
        this.speed = car.getSpeed();
        this.handling = car.getHandling();
        this.reliability = car.getReliability();
        this.fuelEconomy = car.getFuelEconomy();
        this.overall = calculateOverall();

        terrainStatModification(terrain);
    }

    /**
     * Modifies the entrant's stats based on the terrain type.
     *
     * @param terrain The terrain type to modify stats for.
     */
    private void terrainStatModification(Route.Terrain terrain) {
        switch (terrain) {
            case HILLY -> speed = speed > 2 ? speed - 2 : 1;
            case WINDY -> fuelEconomy = fuelEconomy > 2 ? fuelEconomy - 2 : 1;
            case OFF_ROAD -> reliability = reliability > 2 ? reliability - 2 : 1;
        }
    }

    /**
     * Returns the position of the entrant as a formatted string (e.g., "1st", "2nd").
     *
     * @return The formatted position string.
     */
    public String positionString() {
        return positionString(position);
    }

    public static String positionString(int position) {
        if (position % 10 == 1 && position != 11) return position + "st";
        if (position % 10 == 2 && position != 12) return position + "nd";
        if (position % 10 == 3 && position != 13) return position + "rd";
        return position + "th";
    }


    /**
     * Returns a string representation of the entrant for the leaderboard.
     *
     * @return The leaderboard string containing position, name, and distance.
     */
    public String leaderboardString() {
        return positionString() + "\n" + name + "\n" + distance + " km";
    }

    /**
     * Compares this entrant to another entrant based on their race status and progress.
     * A finished entrant is always considered greater than a non-finished entrant.
     *
     * @param entrant The entrant to compare to.
     * @return A negative integer, zero, or a positive integer as this entrant is less than,
     * equal to, or greater than the specified entrant.
     */
    @Override
    public int compareTo(Entrant entrant) {
        if (this.isFinished && entrant.isFinished) {
            return entrant.position - this.position;
        }
        if (this.isFinished) {
            return 1;
        }
        if (entrant.isFinished) {
            return -1;
        }
        return this.distance - entrant.distance;
    }

    /**
     * Returns the distance traveled by the entrant.
     *
     * @return The distance traveled.
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Returns the fuel level of the entrant.
     *
     * @return The fuel level.
     */
    public int getFuel() {
        return fuel;
    }

    /**
     * Adds a specified distance to the entrant's total distance traveled.
     *
     * @param distance The distance to add.
     */
    public void addDistance(int distance) {
        this.distance += distance;
    }

    /**
     * Sets the fuel level of the entrant.
     *
     * @param fuel The fuel level to set.
     */
    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    /**
     * Returns the position of the entrant.
     *
     * @return The position of the entrant.
     */
    public int getPosition() {
        return position;
    }

    /**
     * Sets the position of the entrant.
     *
     * @param position The position to set.
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Returns whether the entrant is currently stopped.
     *
     * @return True if the entrant is stopped, false otherwise.
     */
    public boolean isStopped() {
        return isStopped;
    }

    /**
     * Sets whether the entrant is currently stopped.
     *
     * @param stopped True to indicate the entrant is stopped, false otherwise.
     */
    public void setStopped(boolean stopped) {
        isStopped = stopped;
    }

    /**
     * Returns whether the entrant is currently broken down.
     *
     * @return True if the entrant is broken down, false otherwise.
     */
    public boolean isBrokenDown() {
        return isBrokenDown;
    }

    /**
     * Sets whether the entrant is currently broken down.
     *
     * @param brokenDown True to indicate the entrant is broken down, false otherwise.
     */
    public void setBrokenDown(boolean brokenDown) {
        isBrokenDown = brokenDown;
    }

    /**
     * Returns whether the entrant has finished the race.
     *
     * @return True if the entrant has finished, false otherwise.
     */
    public boolean isFinished() {
        return isFinished;
    }

    /**
     * Sets the finished status of the entrant.
     *
     * @param finished True to indicate the entrant has finished, false otherwise.
     */
    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    /**
     * Returns the number of fuel stops passed by the entrant.
     *
     * @return The number of fuel stops passed.
     */
    public int getFuelStopsPassed() {
        return fuelStopsPassed;
    }

    /**
     * Increments the number of fuel stops passed by the entrant.
     */
    public void incrementFuelStopsPassed() {
        this.fuelStopsPassed++;
    }
}