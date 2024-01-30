package recordlib.json.read;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.json.JsonUtils;
import recordlib.json.spec.RecordDefHelperJson;
import recordlib.specification.RecordFieldType;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordReaderUsingSpec {

    private static final int LEVEL_ROOT = 0;
    private static final int LEVEL_ITEMS = 1;

    private JSONObject rootObj;
    private JSONArray rootArr;

    private static final boolean DEBUG = false;

    private Record root = new Record();

    private RecordDef itemSpec;

    public RecordReaderUsingSpec(boolean isList, String jsonText, RecordDef itemSpec) {
        this.itemSpec = itemSpec;

        read(isList, jsonText);

        convert();
    }

    private void convert() {
        if (rootObj != null) {
            readObject(root, rootObj, LEVEL_ROOT);
        }
        if (rootArr != null) {
            readArray(root, rootArr, LEVEL_ROOT);
        }
    }

    private void readObject(Record item, JSONObject obj, int level) {
        String idName = RecordDefHelperJson.getInstance().getIdName();
        if (level == LEVEL_ITEMS) {
            // Id

            if (obj.has(idName)) {
                boolean applied = applyId(item, obj.get(idName));
                if (applied) {
                }
            }
        }

        for (RecordFieldDef valueSpec : itemSpec.getSpec()) {
            String name = valueSpec.getName();
            RecordFieldType type = valueSpec.getType();

            Object value = null;
            if (obj.has(name)) {
                value = obj.get(name);
            }

            boolean isValue = applyValue(item, name, value, type);
            if (isValue) {
            }
        }
    }

    private void readArray(Record item, JSONArray arr, int level) {
        int len = arr.length();

        for (int i=0; i<len; i++) {
            Object value = arr.get(i);

            if (value instanceof JSONObject) {
                Record newItem = new Record();
                item.add(newItem);

                readObject(newItem, (JSONObject) value, level + 1);
            }
        }
    }

    private boolean applyId(Record item, Object value) {
        //System.out.println("Apply ID: " + value + ", " + (value != null ? value.getClass() : "x"));

        if (value instanceof Integer) {
            Integer intValue = ((Integer) value);

            item.setId(intValue.longValue());
            return true;
        }
        if (value instanceof Long) {
            Long longValue = ((Long) value);

            item.setId(longValue);
            return true;
        }

        return false;
    }

    private boolean applyValue(Record item, String name, Object value, RecordFieldType type) {
        if (DEBUG) {
            if (value == null) {
                System.out.println(" Value null: " + name);
            } else {
                System.out.println(" Class: " + value.getClass() + " " + name + " = " + value);
            }
        }

        if (type.isString() && (value instanceof JSONString || value instanceof String)) {
            String stringValue = null;
            if (value instanceof JSONString) {
                stringValue = ((JSONString) value).toJSONString();
            } else if (value instanceof String) {
                stringValue = (String)value;
            }

            item.setStringValue(name, stringValue);
            return true;
        }

        if (type.isLong() &&  (value instanceof Integer || value instanceof Long)) {
            Long longValue = null;
            if (value instanceof Integer) {
                longValue = (((Integer) value).longValue());
            } else if (value instanceof Long) {
                longValue = (Long) value;
            }

            item.setLongValue(name, longValue);
            return true;
        }

        if (type.isDouble() && (value instanceof Double || value instanceof BigDecimal)) {
            Double doubleValue = null;
            if (value instanceof BigDecimal) {
                doubleValue = ((BigDecimal) value).doubleValue();
            } else if (value instanceof Double) {
                doubleValue = (Double) value;
            }

            item.setDoubleValue(name, doubleValue);
            return true;
        }

        if (type.isBoolean() && value instanceof Boolean) {
            item.setBooleanValue(name, ((Boolean) value));
            return true;
        }

        if (type.isDate() && (value instanceof Date || value instanceof String)) {
            Date date = null;
            if (value instanceof Date) {
                date = (Date)value;
            } else if (value instanceof String) {
                date = JsonUtils.stringToDate((String)value);
            }

            item.setDateValue(name, date);
            return true;
        }

        if (type.isBinary() && (value instanceof String)) {
            byte[] parsed = JsonUtils.stringToBytes((String)value);

            item.setBinaryValue(name, parsed);
            return true;
        }

        return false;
    }

    private void read(boolean isList, String jsonText) {
        rootObj = null;
        rootArr = null;

        String messageObj = null;
        String messageArr = null;

        if (isList) {
            try {
                rootArr = new JSONArray(jsonText);
            } catch (JSONException e) {
                // Ignore
                messageArr = e.getMessage();
            }
        } else {
            try {
                rootObj = new JSONObject(jsonText);
            } catch (JSONException e) {
                // Ignore
                messageObj = e.getMessage();
            }
        }

        if (rootObj == null && rootArr == null) {
            throw new IllegalArgumentException("Cannot read json, no content: " + jsonText + ", ex1=" + messageObj + ", " + messageArr);
        }
    }

    public Record getItem() {
        return root;
    }

    public static List<Record> readItemsFromFile(File file, RecordDef itemSpec) throws IOException {
        if (!file.exists()) {
            return new ArrayList();
        }

        Path path = Paths.get(file.toURI());
        byte[] bytes = Files.readAllBytes(path);

        String filetext = new String(bytes, JsonUtils.UTF8_CHARSET);
        RecordReaderUsingSpec reader = new RecordReaderUsingSpec(true, filetext, itemSpec);

        Record root = reader.getItem();
        return root.getItems();
    }
}
