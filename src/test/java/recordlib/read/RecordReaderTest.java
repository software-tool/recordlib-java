package recordlib.read;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recordlib.Record;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecordReaderTest {

    @Test
    public void readFromFile() throws IOException {
        List<Record> records = RecordReader.readItemsFromFile(new File(ClassLoader.getSystemResource("recordlib/read/planets.json").getFile()));

        Assertions.assertEquals(2, records.size());

        Record record1 = records.get(0);
        Record record2 = records.get(1);

        Assertions.assertEquals(record1.get("title"), "Planet 1");
        Assertions.assertEquals(record2.get("title"), "Planet 2");
        Assertions.assertEquals(record1.getDouble("age"), 19.11d);
        Assertions.assertEquals(record2.getDouble("age"), 12.12d);
    }

}
