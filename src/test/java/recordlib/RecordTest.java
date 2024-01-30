package recordlib;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;

public class RecordTest {

    @Test
    public void testCreate() {
        Record record1 = new Record();
        Record record2 = new Record(11, "animal");

        Record record3 = new Record("animal", "bear");
        Record record4 = new Record("price", 22.9);

        Assertions.assertNull(record1.getName());
        Assertions.assertNull(record1.getValue());

        Assertions.assertEquals(11, record2.getId());

        Assertions.assertEquals("animal", record3.getName());
        Assertions.assertEquals("bear", record3.getStringValue());

        Assertions.assertEquals(22.9, record4.getDoubleValue());
    }

    @Test
    public void testCreateBuilder() {
        double weight = 291.9;
        long cm = 280l;
        Date dateNow = new Date();

        byte[] imgBytes = new byte[2];
        imgBytes[0] = 64;
        imgBytes[1] = 65;

        Record item1 = new Record("animal")
                .add("size", "big")
                .setLongValue("cm", cm);
        item1.setDoubleValue("weight", 291.9);
        item1.set("date", dateNow);
        item1.setBinaryValue("image", imgBytes);

        Assertions.assertEquals("animal", item1.getName());

        Assertions.assertEquals("big", item1.get("size"));
        Assertions.assertEquals("big", item1.getValue("size"));
        Assertions.assertEquals("big", item1.getStringValue("size"));

        Assertions.assertEquals(null, item1.get("cm"));
        Assertions.assertEquals(cm, item1.getValue("cm"));
        Assertions.assertEquals(cm, item1.getLong("cm"));
        Assertions.assertEquals(cm, item1.getLongValue("cm"));

        Assertions.assertEquals(null, item1.get("weight"));
        Assertions.assertEquals(weight, item1.getValue("weight"));
        Assertions.assertEquals(weight, item1.getDouble("weight"));
        Assertions.assertEquals(weight, item1.getDoubleValue("weight"));

        Assertions.assertEquals(null, item1.get("date"));
        Assertions.assertEquals(dateNow, item1.getValue("date"));
        Assertions.assertEquals(dateNow, item1.getDate("date"));
        Assertions.assertEquals(dateNow, item1.getDateValue("date"));

        Assertions.assertEquals(null, item1.get("image"));
        Assertions.assertEquals(imgBytes, item1.getBinary("image"));
        Assertions.assertEquals(imgBytes, item1.getBinaryValue("image"));
    }

    @Test
    public void testCreate_WithEncoding() {
        Record record1 = new Record();
        record1.set("name", "Música");

        Assertions.assertEquals("Música", record1.getStringValue("name"));
    }

    @Test
    public void testToString() {
        Record parentItem = getAnimalItems();

        String str = parentItem.toString();
        //System.out.println(str);

        Assertions.assertTrue(str.contains("title=\"cat\""));
        Assertions.assertTrue(str.contains("age=3"));
    }

    @Test
    public void testToStringSingleLine() {
        Record parentItem = getAnimalItems();

        String str = parentItem.toStringSingleLine();
        //System.out.println(str);

        Assertions.assertTrue(str.contains("title=\"cat\", age=3"));
    }

    private Record getAnimalItems() {
        Record record1 = new Record("animal", "title", "lion");
        Record record2 = new Record(2, "animal");
        Record record3 = new Record("animal", "title", "bear");

        record1.add("age", 9);

        record2.add("title", "cat");
        record2.add("age", 3);

        record3.add("age", 14);

        Record parentItem = new Record("animals");
        parentItem.addContent(record1, record2, record3);

        return parentItem;
    }
}
