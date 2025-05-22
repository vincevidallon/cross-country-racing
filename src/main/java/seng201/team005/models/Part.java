package seng201.team005.models;


/**
 * A class which represents a tuning part that can be purchased and then installed
 * on a car.
 * <p>
 *     A {@code Part} extends the {@link Purchasable} class. It includes
 *     the logic for randomly generating a name for the part. Each part
 *     also has a base {@code buyValue} which slightly increases when its created.
 * </p>
 *
 * @author vvi29
 */
public class Part extends Purchasable {

    /**
     * Constructor for a {@code Part} with the specified name parameter.
     * Constructor sets the sell value to -1 and weight to 4, and then increases
     * the {@code buyValue} by 2.
     *
     * @param name the name of the part
     */
    public Part(String name) {
        super(name, -1, 4);
        buyValue++;
    }

    /**
     * Constructor for a {@code Part} with a randomly generated name.
     * The name of the part is in the form "PartXY", where X and Y are random
     * integers between 1 and 9.
     */
    public Part() {
        this("Part" + rng.nextInt(1, 10) + rng.nextInt(1, 10));
    }


    /**
     * Returns the part name.
     *
     * @return name of the part
     */
    @Override
    public String toString() {
        return this.name;
    }
}
