package ua.edu.nulp.kava.util;

public class Utils {
    public static int strToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (Exception ignored) {
            return 0;
        }
    }

    public static double strToDouble(String str) {
        try {
            return Double.parseDouble(str.replace(",", "."));
        } catch (Exception ignored) {
            return 0;
        }
    }
}
