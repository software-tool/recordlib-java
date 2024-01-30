package recordlib.write;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import recordlib.Record;
import recordlib.RecordDef;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class RecordWriterTest {

    @Test
    public void writeToFile() throws IOException {
        File tmpFile = null;

        RecordDef def = new RecordDef().set("title").setDouble("age");

        try {
            tmpFile = File.createTempFile("record_writer_test", ".json");
            tmpFile.deleteOnExit();

            Record record1 = new Record();
            Record record2 = new Record();

            record1.set("title", "Planet 1");
            record1.set("age", 19.11);

            record2.set("title", "Planet 2").set("age", 12.12);

            RecordWriter.writeToFile(tmpFile, List.of(record1, record2), def);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String readFromFile = Files.readString(tmpFile.toPath(), StandardCharsets.UTF_8);
        Assertions.assertTrue(readFromFile.contains("\"title\":\"Planet 1\""));
        Assertions.assertTrue(readFromFile.contains("\"title\":\"Planet 2\""));
        Assertions.assertTrue(readFromFile.contains("\"age\":19.11"));
        Assertions.assertTrue(readFromFile.contains("\"age\":12.12"));
    }

}
