package recordlib.util;

public class EqualsUtil {

    public static boolean isEqual(String value1, String value2) {
        if (value1 == null && value2 == null) {
            return true;
        }

        if (value1 == null) {
            return false;
        }
        if (value2 == null) {
            return false;
        }

        return value1.equals(value2);
    }

    public static boolean isEqual(Object value1, Object value2) {
        if (value1 == null && value2 == null) {
            return true;
        }

        if (value1 == null) {
            return false;
        }
        if (value2 == null) {
            return false;
        }

        return value1.equals(value2);
    }
}
