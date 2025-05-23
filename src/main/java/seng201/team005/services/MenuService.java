package seng201.team005.services;

/**
 * Abstract service class providing utility methods for converting numerical stats
 * into star-based representations and handling numeric signs.
 * <p>
 * This class includes methods to:
 * <ul>
 * <li>Convert numerical stats into a star string representation.</li>
 * <li>Adjust star representations based on thresholds.</li>
 * <li> Handle positive and negative signs for numeric values.</li>
 * </ul>
 * </p>
 *
 * @author sha378
 */
public abstract class MenuService {

    /**
     * Converts a number into a string of star symbols (✪), prefixed with a negative sign if the number is negative.
     *
     * @param num The number to convert.
     * @return A string of star symbols representing the number, with a negative sign if applicable.
     */
    private static String starString(int num) {
        return getNegativeSign(num) + "✪".repeat(Math.abs(num));
    }

    /**
     * Converts a numerical stat into a star-based representation, with a threshold to determine the format.
     *
     * @param num The numerical stat to convert.
     * @param threshold The threshold for determining the representation format.
     * @return A string representing the stat as stars, or the number followed by a star symbol if above the threshold.
     */
    public static String convertStatToStars(int num, int threshold) {
        return Math.abs(num) < threshold ? starString(num) : num + "✪";
    }

    /**
     * Converts a numerical stat into a star-based representation using a default threshold of 6.
     *
     * @param num The numerical stat to convert.
     * @return A string representing the stat as stars, or the number followed by a star symbol if above the threshold.
     */
    public static String convertStatToStars(int num) {
        return convertStatToStars(num, 6);
    }

    /**
     * Converts a numerical stat into a star-based representation, with an option to include the numeric value.
     *
     * @param num The numerical stat to convert.
     * @param isNumeric A flag indicating whether to include the numeric value in the representation.
     * @return A string representing the stat as stars, or the number followed by a star symbol if applicable.
     */
    public static String convertStatToStars(int num, boolean isNumeric) {
        return isNumeric && Math.abs(num) > 1 ? num + "✪" : starString(num);
    }

    /**
     * Adjusts the star representation of a car stat based on a part stat and a threshold.
     *
     * @param carStat The car stat to adjust.
     * @param partStat The part stat to adjust with.
     * @param threshold The threshold for determining the representation format.
     * @return A string combining the car stat and part stat as adjusted star representations.
     */
    public static String partAdjustStars(int carStat, int partStat, int threshold) {
        return convertStatToStars(carStat) + " " + getPositiveSign(partStat) +
                convertStatToStars(partStat, Math.abs(carStat) + Math.abs(partStat) > threshold);
    }

    /**
     * Returns a positive sign ("+") if the number is greater than zero.
     *
     * @param num The number to check.
     * @return A "+" sign if the number is positive, otherwise an empty string.
     */
    public static String getPositiveSign(int num) {
        return num > 0 ? "+" : "";
    }

    /**
     * Returns a negative sign ("-") if the number is less than zero.
     *
     * @param num The number to check.
     * @return A "-" sign if the number is negative, otherwise an empty string.
     */
    public static String getNegativeSign(int num) {
        return num < 0 ? "-" : "";
    }
}