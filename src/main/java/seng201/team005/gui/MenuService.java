package seng201.team005.gui;

public abstract class MenuService {
    public static String convertStatToStars(int num) {
        return "✪".repeat(Math.abs(num));
    }

    public static String getSpacedSign(int num) {
        return num > 0 ? " +" : num < 0 ? " -" : "";
    }
}
