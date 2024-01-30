package recordlib.json.testdata;

import java.util.Arrays;
import java.util.List;

public class TestdataJson {

    private static String QUOTE = "\"";
    private static String NEWLINE = "\n";

    public static String getKeyList() {
        StringBuilder json = new StringBuilder();

        json.append("{");
        // Named array
        json.append(String.format("%s%s%s: [", QUOTE, "assignmentKeys", QUOTE));

        addArrayItemObj(json, Arrays.asList("enabled", "name", "key"), Arrays.asList(true, "Erloese", 1022));
        json.append("," + NEWLINE);
        addArrayItemObj(json, Arrays.asList("enabled", "name", "key"), Arrays.asList(true, "Sonstige", 1023));
        json.append("," + NEWLINE);
        addArrayItemObj(json, Arrays.asList("enabled", "name", "key"), Arrays.asList(false, "Kaution", 1024));

        json.append("]");

        json.append("}");

        return json.toString();
    }

    private static void addArrayItemObj(StringBuilder json, List<String> names, List<Object> values) {
        json.append("{ ");

        int last = names.size();
        int i=0;
        for(String name : names) {
            Object value = values.get(i);

            json.append(String.format("%s%s%s: ", QUOTE, name, QUOTE));

            if (value instanceof String) {
                json.append(String.format("%s%s%s", QUOTE, value, QUOTE));
            } else if (value instanceof Integer) {
                json.append(String.format("%d", value));
            } else if (value instanceof Boolean) {
                json.append(String.format("%s", value));
            }

            if (i != (last-1)) {
                json.append(",");
            }

            i++;
        }

        json.append(" }");
    }
}
