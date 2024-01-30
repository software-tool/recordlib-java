package recordlib.json;

import org.json.JSONArray;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Locale;

public class JsonUtils {

    public static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    private static final String dateFormatPattern = "yyyy-MM-dd HH:mm:ss Z";
    private static final String jsonDefaultFormatPattern = "EEE MMM dd HH:mm:ss zzz yyyy";

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat(dateFormatPattern, Locale.ENGLISH);
    private static final SimpleDateFormat jsonDefaultDateFormat = new SimpleDateFormat(jsonDefaultFormatPattern, Locale.ENGLISH);

    public static void write(File file, JSONArray arr) throws IOException {
        try (FileWriter fileWriter = new FileWriter(file, UTF8_CHARSET)) {
            arr.write(fileWriter);
        }
    }

    public static String dateToString(Date date) {
        return dateFormat.format(date);
    }

    public static String bytesToString(byte[] bytes) {
        if (bytes == null) return null;
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static boolean canBeDate(String text) {
        if (text.length() < 20 || text.length() > 35) {
            return false;
        }
        return true;
    }

    public static Date stringToDate(String dateStr) {
        Date parsed = null;
        try {
            parsed = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            // Ignore
        }

        // Fallback: JSON default format: Sat Jan 01 00:00:00 CET 2000
        try {
            parsed = jsonDefaultDateFormat.parse(dateStr);
        } catch (ParseException e) {
            // Ignore
        }

        return parsed;
    }

    public static byte[] stringToBytes(String bytesStr) {
        byte[] parsed = Base64.getDecoder().decode(bytesStr.getBytes(StandardCharsets.UTF_8));
        return parsed;
    }
}
