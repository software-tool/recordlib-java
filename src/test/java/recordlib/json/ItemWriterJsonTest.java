package recordlib.json;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.json.write.RecordWriterJson;
import recordlib.specification.RecordFieldType;

import java.util.Arrays;
import java.util.Date;

public class ItemWriterJsonTest {

    @Test
    public void testWriteItem() {
        RecordDef itemSpec = new RecordDef("Animal");
        itemSpec.add("name", RecordFieldType.STRING);
        itemSpec.add("count", RecordFieldType.LONG);
        itemSpec.add("price", RecordFieldType.DOUBLE);
        itemSpec.add("created", RecordFieldType.DATE);

        Record item = new Record();
        item.setStringValue("name", "Goose");
        item.setLongValue("count", 58l);
        item.setDoubleValue("price", 25.8d);
        item.setDateValue("created", new Date(100, 0, 1));

        JSONArray arr = new RecordWriterJson().writeItems(Arrays.asList(item), itemSpec);
        JSONObject first = (JSONObject) arr.get(0);

        Assertions.assertEquals("Goose", first.getString("name"));
        Assertions.assertEquals(58l, first.getLong("count"));
        Assertions.assertEquals(25.8, first.getDouble("price"));

        // Dates are stored as string
        Assertions.assertEquals("2000-01-01 00:00:00 +0100", first.get("created"));
    }

}
