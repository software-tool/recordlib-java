package recordlib.util;

public class LongUtil {

    public static Long parse(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        if ("null".equals(str)) {
            return null;
        }

        Long result = null;
        try {
            result = Long.parseLong(str);
        } catch (NumberFormatException e) {
            // Ignore
        }

        return result;
    }

    public static Long parse(String str, long defaultValue) {
        Long result = parse(str);

        if (result == null) {
            return defaultValue;
        }

        return result;
    }

}
