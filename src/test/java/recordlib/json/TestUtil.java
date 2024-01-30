package recordlib.json;

public class TestUtil {

    public static boolean arraysAreEquals(byte[] arr1, byte[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }

        for (int i=0; i<arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }

        return true;
    }

}
