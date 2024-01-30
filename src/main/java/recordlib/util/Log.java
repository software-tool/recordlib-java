package recordlib.util;

public class Log {

    public static void warning(String text) {
        System.out.println("WARN: " + text);
    }

    public static void warning(String text, String details) {
        System.out.println("WARN: " + text + ", " + details);
    }

}
