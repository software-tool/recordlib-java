package recordlib.json.read;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import recordlib.Record;
import recordlib.json.JsonUtils;
import recordlib.json.spec.RecordDefHelperJson;
import recordlib.util.EqualsUtil;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordReaderJson {

    private static final int LEVEL_ROOT = 0;
    private static final int LEVEL_ITEMS = 1;

    private JSONObject rootObj;
    private JSONArray rootArr;

    private static final boolean DEBUG = false;

    private Record root = new Record();

    private final boolean parseToDate;

    public RecordReaderJson(String jsonText) {
        this(jsonText, false);
    }

    public RecordReaderJson(String jsonText, boolean parseToDate) {
        this.parseToDate = parseToDate;

        read(jsonText);
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
        JSONArray names = obj.names();
        if (names == null) {
            // Is empty
            return;
        }

        for (Object nameObj : names) {
            String name = (String)nameObj;
            Object value = obj.get(name);

            String idName = RecordDefHelperJson.getInstance().getIdName();
            if (level == LEVEL_ITEMS) {
                // Id

                if (EqualsUtil.isEqual(name, idName)) {
                    boolean applied = applyId(item, value);
                    if (applied) {
                        continue;
                    }
                }
            }

            //System.out.println("Apply value: " + value + ", " + name + ", " + level + ", idName=" + idName);

            boolean isValue = applyValue(item, name, value);
            if (isValue) {
                continue;
            }

            if (value instanceof JSONObject) {
                Record newItem = new Record(name);
                item.add(newItem);

                readObject(newItem, (JSONObject) value, level + 1);
            }

            if (value instanceof JSONArray) {
                Record newItem = new Record(name);
                item.add(newItem);

                readArray(newItem, (JSONArray) value, level + 1);
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

    private void readArray(Record item, JSONArray arr, int level) {
        int len = arr.length();

        for (int i=0; i<len; i++) {
            Object value = arr.get(i);

            if (value instanceof JSONObject) {
                Record newItem = new Record();
                item.add(newItem);

                readObject(newItem, (JSONObject) value, level + 1);
            }

            if (value instanceof JSONArray) {
                Record newItem = new Record();
                item.add(newItem);

                readArray(newItem, (JSONArray) value, level + 1);
            }
        }
    }

    private boolean applyValue(Record item, String name, Object value) {
        if (DEBUG) {
            if (value == null) {
                System.out.println(" Value null: " + name);
            } else {
                System.out.println(" Class: " + value.getClass() + " " + name + " = " + value);
            }
        }

        if (value instanceof JSONString) {
            String str = ((JSONString) value).toJSONString();
            if (parseToDate) {
                Date date = asDate(str);
                if (date != null) {
                    item.setDateValue(name, date);
                } else {
                    item.setStringValue(name, str);
                }
            } else {
                item.setStringValue(name, str);
            }
            return true;
        }

        if (value instanceof String) {
            String str = ((String) value);
            if (parseToDate) {
                Date date = asDate(str);
                if (date != null) {
                    item.setDateValue(name, date);
                } else {
                    item.setStringValue(name, str);
                }
            } else {
                item.setStringValue(name, str);
            }
            return true;
        }

        if (value instanceof BigInteger) {
            BigInteger bigIntegerValue = ((BigInteger) value);

            item.setLongValue(name, bigIntegerValue.longValue());
            return true;
        }

        if (value instanceof Double) {
            Double doubleValue = ((Double) value);

            item.setDoubleValue(name, doubleValue.doubleValue());
            return true;
        }

        if (value instanceof BigDecimal) {
            BigDecimal bigDecimalValue = ((BigDecimal) value);

            item.setDoubleValue(name, bigDecimalValue.doubleValue());
            return true;
        }

        if (value instanceof Integer) {
            Integer intValue = ((Integer) value);

            item.setLongValue(name, intValue.longValue());
            return true;
        }

        if (value instanceof Boolean) {
            item.setBooleanValue(name, ((Boolean) value));
            return true;
        }

        return false;
    }

    public void read(String jsonText) {
        rootObj = null;
        rootArr = null;

        String messageObj = null;
        String messageArr = null;

        try {
            if (jsonText != null) {
                rootObj = new JSONObject(jsonText);
            }
        } catch (JSONException e) {
            // Ignore
            messageObj = e.getMessage();
        }

        try {
            if (jsonText != null) {
                rootArr = new JSONArray(jsonText);
            }
        } catch (JSONException e) {
            // Ignore
            messageArr = e.getMessage();
        }

        if (rootObj == null && rootArr == null) {
            throw new IllegalArgumentException("Cannot read json, no valid content: " + jsonText + ", ex1=" + messageObj + ", " + messageArr);
        }
    }

    private Date asDate(String text) {
        if (!JsonUtils.canBeDate(text)) {
            return null;
        }

        Date date = JsonUtils.stringToDate(text);
        return date;
    }

    public Record getItem() {
        return root;
    }

    public static Record readItemFromFile(File file) throws IOException {
        String filetext = new String(Files.readAllBytes(Paths.get(file.toURI())), JsonUtils.UTF8_CHARSET);
        RecordReaderJson reader = new RecordReaderJson(filetext);

        Record root = reader.getItem();
        return root;
    }

    public static List<Record> readItemsFromFile(File file) throws IOException {
        if (!file.exists()) {
            return new ArrayList();
        }

        Path path = Paths.get(file.toURI());
        byte[] bytes = Files.readAllBytes(path);

        String filetext = new String(bytes, JsonUtils.UTF8_CHARSET);
        RecordReaderJson reader = new RecordReaderJson(filetext);

        Record root = reader.getItem();
        return root.getItems();
    }
}
