package recordlib.util;

public class BooleanUtil {

    public static Boolean parse(String str) {
        if (str == null) {
            return null;
        }
        if ("null".equals(str)) {
            return null;
        }

        if (str.equalsIgnoreCase("yes")) {
            return true;
        }

        if (str.equalsIgnoreCase("no")) {
            return false;
        }

        if (str.equals("true")) {
            return true;
        }

        if (str.equals("false")) {
            return false;
        }

        return null;
    }

    public static boolean parseBooleanDefaultFalse(String str) {
        Boolean result = parse(str);
        if (result == null) {
            return false;
        }

        return result;
    }

    public static boolean parseBoolean(String str, boolean defaultValue) {
        Boolean result = parse(str);
        if (result == null) {
            return defaultValue;
        }

        return result;
    }

}
