package recordlib.util;

public class DoubleUtil {

    public static Double parse(String str) {
        return parse(str, null);
    }

    public static Double parse(String str, Double defaultValue) {
        if (str == null || str.isEmpty()) {
            return defaultValue;
        }

        Double result = null;
        try {
            result = Double.parseDouble(str);
        } catch (NumberFormatException e) {
            // Ignore
            return defaultValue;
        }

        return result;
    }

}
