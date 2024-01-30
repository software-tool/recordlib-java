package recordlib.json.write;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.json.TestUtil;
import recordlib.json.read.RecordReaderUsingSpec;

import java.io.IOException;
import java.util.Arrays;

public class ItemWriterJsonTest {

    @Test
    public void testWriteBinary() throws IOException {
        RecordDef spec = new RecordDef()
                .add("title")
                .addBinary("image");

        byte[] imgBytes = new byte[5];
        imgBytes[0] = 64;
        imgBytes[1] = 65;
        imgBytes[4] = 70;

        Record item = new Record();
        item.set("title", "Testing");
        item.set("image", imgBytes);

        String json = RecordWriterJson.writeToString(Arrays.asList(item), spec);

        Assertions.assertTrue(json.contains("QEEAAEY="));
        Assertions.assertTrue(json.contains("Testing"));

        RecordReaderUsingSpec itemReader = new RecordReaderUsingSpec(true, json, spec);
        Assertions.assertTrue(TestUtil.arraysAreEquals(imgBytes, itemReader.getItem().getItems().get(0).getBinary("image")));
    }


}
