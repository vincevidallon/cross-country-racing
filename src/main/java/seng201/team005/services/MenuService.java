package seng201.team005.services;

public abstract class MenuService {
    public static String convertStatToStars(int num) {
        return num < 9 ? "✪".repeat(Math.abs(num)) : num + "✪";
    }

    public static String getSpacedSign(int num) {
        return num > 0 ? " +" : num < 0 ? " -" : "";
    }
}
