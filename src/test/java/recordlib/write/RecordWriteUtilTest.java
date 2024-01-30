package recordlib.write;

import recordlib.Record;
import recordlib.RecordFieldDef;
import recordlib.specification.RecordFieldType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecordWriteUtilTest {

    @Test
    public void testWriteBinary() {
        byte[] imgBytes = new byte[2];
        imgBytes[0] = 64;
        imgBytes[1] = 65;

        byte[] imgBytes2 = new byte[5];
        imgBytes2[0] = 64;
        imgBytes2[1] = 65;

        Record item = new Record();
        item.set("image", imgBytes);

        Record item2 = new Record();
        item2.set("image", imgBytes2);

        RecordFieldDef valueSpec = new RecordFieldDef("image", RecordFieldType.BINARY);

        Assertions.assertEquals("QEE=", RecordWriteUtil.getValueAsString(item, valueSpec));
        Assertions.assertEquals("QEEAAAA=", RecordWriteUtil.getValueAsString(item2, valueSpec));
    }
}
