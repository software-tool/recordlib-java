package recordlib.util;

import java.util.Base64;

public class BinaryUtil {

    public static String asString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

}
