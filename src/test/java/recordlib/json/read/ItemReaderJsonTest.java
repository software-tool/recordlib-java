package recordlib.json.read;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recordlib.Record;
import recordlib.json.testdata.TestdataJson;
import recordlib.json.write.RecordWriterJson;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ItemReaderJsonTest {

    private String QUOTE = "\"";
    private String NEWLINE = "\n";

    @Test
    public void testReadSimple() {
        String json = TestdataJson.getKeyList();

        //System.out.println("JSON: " + json);

        RecordReaderJson reader = new RecordReaderJson(json);
        Record item = reader.getItem();

        //System.out.println("Item: " + item);

        List<Record> items = item.getItems();

        Assertions.assertEquals(1, items.size());
        Assertions.assertEquals("assignmentKeys", items.get(0).getName());

        List<Record> subitems = items.get(0).getItems();

        Assertions.assertEquals("Erloese", subitems.get(0).get("name"));
        Assertions.assertEquals("Sonstige", subitems.get(1).get("name"));
        Assertions.assertEquals("Kaution", subitems.get(2).get("name"));
    }

    @Test
    public void testReadDate_withoutSpec() throws IOException {
        Date createdDate = new Date(100, 0, 1);

        Record item = new Record();
        item.setStringValue("name", "Goose");
        item.setLongValue("count", 58l);
        item.setDateValue("created", createdDate);

        String jsonStr = RecordWriterJson.writeToString(Arrays.asList(item));
        System.out.println(jsonStr);

        // Parsing string to date, as format is known
        Record itemRead = new RecordReaderJson(jsonStr, true).getItem().getSubitem(0);
        Assertions.assertEquals(createdDate, itemRead.getDate("created"));
        Assertions.assertEquals(null, itemRead.get("created"));
        Assertions.assertEquals(null, itemRead.getString("created"));

        // Do not parse
        Record itemRead2 = new RecordReaderJson(jsonStr).getItem().getSubitem(0);
        Assertions.assertEquals(null, itemRead2.getDate("created"));
        Assertions.assertEquals("2000-01-01 00:00:00 +0100", itemRead2.get("created"));
        Assertions.assertEquals("2000-01-01 00:00:00 +0100", itemRead2.getString("created"));
    }

    @Test
    public void testReadJson() {
        String demoArr = """
[
  {
    "name": "red",
    "id": 0
  },
  {
    "name": "blue",
    "id": 1
  }
]                """;

        RecordReaderJson reader = new RecordReaderJson(demoArr);
        Record item = reader.getItem();

        List<Record> items = item.getItems();

        Assertions.assertEquals(2, items.size());
        Assertions.assertEquals("red", items.get(0).get("name"));
        Assertions.assertEquals("blue", items.get(1).get("name"));

        //System.out.println("Item: " + item);
    }

    @Test
    public void testReadFailed() {
        String json = "aaa";

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RecordReaderJson reader = new RecordReaderJson(json);
        });

        Assertions.assertTrue(thrown.getMessage().contains("Cannot read json, no valid content"));
    }

}
