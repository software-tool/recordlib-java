package recordlib.json.write;

import org.json.JSONArray;
import org.json.JSONObject;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.json.JsonUtils;
import recordlib.json.spec.RecordDefHelperJson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RecordWriterJson {

    // If true, e.g.
    // "image": [
    //    64,
    //    65,
    //    0,
    //    0,
    //    70
    //  ],
    //
    // Else: Base64, e.g. QEEAAEY=
    private boolean writeBytesInArray = false;

    // Without Spec

    /**
     * Write items without spec
     */
    public JSONArray writeItems(List<Record> items) {
        JSONArray jsonArray = new JSONArray();

        assignMissingIds(items, null);

        for (Record item : items) {
            writeObjectItem(jsonArray, item);
        }

        return jsonArray;
    }

    // With Spec

    public JSONArray writeItems(List<Record> items, RecordDef itemSpec) {
        JSONArray jsonArray = new JSONArray();

        assignMissingIds(items, itemSpec);

        for (Record item : items) {
            writeObjectItem(jsonArray, item, itemSpec);
        }

        return jsonArray;
    }

    private JSONObject writeObjectItem(JSONArray jsonArray, Record item, RecordDef itemSpec) {
        JSONObject jsonObject = new JSONObject();

        applyId(item, jsonObject);

        for (RecordFieldDef itemValue : itemSpec.getSpec()) {
            String name = itemValue.getName();
            //String asString = ItemWriteUtil.getValueAsString(item, itemValue);

            Object value = item.getValue(name);
            value = getValueForObject(value);

            jsonObject.put(name, value);
        }

        jsonArray.putAll(Arrays.asList(jsonObject));

        return jsonObject;
    }

    private JSONObject writeItem(Record item, RecordDef itemSpec) {
        JSONObject jsonObject = new JSONObject();

        applyId(item, jsonObject);

        for (RecordFieldDef itemValue : itemSpec.getSpec()) {
            String name = itemValue.getName();
            Object value = item.getValue(name);

            value = getValueForObject(value);

            jsonObject.put(name, value);
        }

        return jsonObject;
    }

    private JSONObject writeObjectItem(JSONArray jsonArray, Record parent) {
        JSONObject jsonObject = new JSONObject();

        applyId(parent, jsonObject);

        List<Record> items = parent.getRecords();
        for (Record item : items) {
            String name = item.getName();
            Object value = item.getValue();

            value = getValueForObject(value);

            jsonObject.put(name, value);
        }

        jsonArray.put(jsonObject);

        return jsonObject;
    }

    private Object getValueForObject(Object value) {
        if (value instanceof Date) {
            // Ensure reliable storage of dates
            return JsonUtils.dateToString((Date)value);
        }

        if (!writeBytesInArray && value instanceof byte[]) {
            return JsonUtils.bytesToString((byte[]) value);
        }

        return value;
    }

    private boolean applyId(Record item, JSONObject jsonObject) {
        Long id = item.getId();
        if (id != null) {
            jsonObject.put(RecordDefHelperJson.getInstance().getIdName(), id);
            return true;
        }

        return false;
    }

    private void assignMissingIds(List<Record> items, RecordDef itemSpec) {
        if (itemSpec != null && !itemSpec.getAutoApplyId()) {
            return;
        }

        Long maxId = -1l;
        List<Record> missingId = new ArrayList<>();

        for (Record item : items) {
            Long id = item.getId();
            if (id == null) {
                missingId.add(item);
            } else {
                maxId = Math.max(maxId, id);
            }
        }

        maxId++;
        for (Record missing : missingId) {
            missing.setId(maxId);

            maxId++;
        }
    }

    private String getName(RecordDef spec, Record item) {
        String name = spec.getName();
        if (name == null && item != null) {
            // Find name in item
            name = item.getName();
        }

        return name;
    }

    private String getName(RecordDef spec, List<Record> items) {
        String name = spec.getName();
        if (name == null) {
            // Find name in items

            for(Record item : items) {
                String nameFound = item.getName();
                if (nameFound != null) {
                    name = nameFound;
                    break;
                }
            }
        }

        return name;
    }

    public static void writeToFile(File file, List<Record> items, RecordDef itemSpec) throws IOException {
        JSONArray arr = new RecordWriterJson().writeItems(items, itemSpec);
        JsonUtils.write(file, arr);
    }

    public static String writeToString(List<Record> items, RecordDef itemSpec) throws IOException {
        JSONArray arr = new RecordWriterJson().writeItems(items, itemSpec);
        return arr.toString(2);
    }

    public static String writeToString(Record item, RecordDef itemSpec) throws IOException {
        JSONObject obj = new RecordWriterJson().writeItem(item, itemSpec);
        return obj.toString(2);
    }

    public static String writeToString(List<Record> items) throws IOException {
        JSONArray arr = new RecordWriterJson().writeItems(items);
        return arr.toString(2);
    }
}
