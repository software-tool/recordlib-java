package recordlib.json.read;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.json.write.RecordWriterJson;
import recordlib.specification.RecordFieldType;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class ItemReaderUsingSpecTest {

    @Test
    public void testWriteToString() throws IOException {
        RecordDef itemSpec = new RecordDef("Animal");
        itemSpec.add("name", RecordFieldType.STRING);
        itemSpec.add("count", RecordFieldType.LONG);
        itemSpec.add("price", RecordFieldType.DOUBLE);
        itemSpec.add("created", RecordFieldType.DATE);

        Record item = new Record();
        item.setId(22l);
        item.setStringValue("name", "Goose");
        item.setLongValue("count", 58l);
        item.setDoubleValue("price", 25.8d);
        item.setDateValue("created", new Date(100, 0, 1));

        String jsonStr = RecordWriterJson.writeToString(Arrays.asList(item), itemSpec);
        //System.out.println(jsonStr);

        Assertions.assertTrue(jsonStr.contains("\"2000-01-01 00:00:00 +0100\""));

        Record itemRead = new RecordReaderUsingSpec(true, jsonStr, itemSpec).getItem();
        Assertions.assertEquals(22l, itemRead.getItems().get(0).getId());
        Assertions.assertEquals(new Date(100, 0, 1), itemRead.getItems().get(0).getDate("created"));
    }

    @Test
    public void testReadDate_havingSpec() throws IOException {
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

        String jsonStr = RecordWriterJson.writeToString(Arrays.asList(item), itemSpec);
        //System.out.println(jsonStr);

        // Cannot be solved: Without spec the date format is not read at all
        Record itemRead = new RecordReaderUsingSpec(true, jsonStr, itemSpec).getItem();
        Assertions.assertEquals(null, itemRead.getDate("created"));
        Assertions.assertEquals(null, itemRead.get("created"));
        Assertions.assertEquals(null, itemRead.getString("created"));
    }

}
