package seng201.team005.services;

public abstract class MenuService {
    private static String starString(int num) {
        return getNegativeSign(num) + "✪".repeat(Math.abs(num));
    }

    public static String convertStatToStars(int num, int threshold) {
        return Math.abs(num) < threshold ? starString(num) : num + "✪";
    }

    public static String convertStatToStars(int num) {
        return convertStatToStars(num, 6);
    }

    public static String convertStatToStars(int num, boolean isNumeric) {
        return isNumeric && Math.abs(num) > 1 ? num + "✪" : starString(num);
    }

    public static String partAdjustStars(int carStat, int partStat, int threshold) {
        return convertStatToStars(carStat) + " " + getPositiveSign(partStat) + convertStatToStars(partStat, Math.abs(carStat) + Math.abs(partStat) > threshold);
    }

    public static String getPositiveSign(int num) {
        return num > 0 ? "+" : "";
    }

    public static String getNegativeSign(int num) {
        return num < 0 ? "-" : "";
    }


}
