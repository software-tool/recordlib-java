package recordlib;

import recordlib.specification.RecordFieldType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class RecordDefTest {

    @Test
    public void testBuilder() {
        RecordDef.create("notes").add("title").add("description").addDouble("value");

        RecordDef created = RecordDef.get("notes");

        Assertions.assertEquals(3, created.getSpec().size());
        Assertions.assertEquals("title", created.getSpec().get(0).getName());
        Assertions.assertEquals("description", created.getSpec().get(1).getName());
        Assertions.assertEquals("value", created.getSpec().get(2).getName());

        Assertions.assertEquals(RecordFieldType.STRING, created.getSpec().get(0).getType());
        Assertions.assertEquals(RecordFieldType.STRING, created.getSpec().get(1).getType());
        Assertions.assertEquals(RecordFieldType.DOUBLE, created.getSpec().get(2).getType());
    }

    @Test
    public void testCreateManual() {
        RecordDef spec = new RecordDef();
        spec.set("title");
        spec.set("description");
        spec.setLong("count");
        spec.setDate("created");
        spec.setBinary("image");

        Assertions.assertEquals(5, spec.getSpec().size());
        Assertions.assertEquals("title", spec.getSpec().get(0).getName());
        Assertions.assertEquals("description", spec.getSpec().get(1).getName());
        Assertions.assertEquals("count", spec.getSpec().get(2).getName());
        Assertions.assertEquals("created", spec.getSpec().get(3).getName());
        Assertions.assertEquals("image", spec.getSpec().get(4).getName());

        Assertions.assertEquals(RecordFieldType.STRING, spec.getSpec().get(0).getType());
        Assertions.assertEquals(RecordFieldType.STRING, spec.getSpec().get(1).getType());
        Assertions.assertEquals(RecordFieldType.LONG, spec.getSpec().get(2).getType());
        Assertions.assertEquals(RecordFieldType.DATE, spec.getSpec().get(3).getType());
        Assertions.assertEquals(RecordFieldType.BINARY, spec.getSpec().get(4).getType());
    }

    @Test
    public void testAutoSpec() {
        Record item1 = Record.create("flower").add("title", "rose");
        Record item2 = Record.create("flower").add("title", "orchid");

        RecordDef created = RecordDef.get(Arrays.asList(item1, item2));

        Assertions.assertEquals(1, created.getSpec().size());
        Assertions.assertEquals("title", created.getSpec().get(0).getName());

        Assertions.assertEquals(RecordFieldType.STRING, created.getSpec().get(0).getType());
    }

    @Test
    public void testAutoSpecError() {
        Record item1 = Record.create("product").add("title", "rose");
        Record item2 = Record.create("product").add("title", 99l);

        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            RecordDef.get(Arrays.asList(item1, item2));
        });

        Assertions.assertTrue(thrown.getMessage().contains("Cannot store items in list"));
    }
}
