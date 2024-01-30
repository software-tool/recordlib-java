package recordlib;

import org.junit.jupiter.api.Test;
import recordlib.write.RecordWriter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;

public class SimpleExample {

    @Test
    public void simpleExampleTest() {
        Record record1 = new Record()
                .set("title", "Big Mountains")
                .set("subtitle", "Learn about the biggest mountains in the world")
                .setLong("pages", 202)
                .setDate("published", new Date());

        System.out.println("Record: " + record1);
    }

    @Test
    public void simpleExample_WriteRecords() throws IOException {
        // Define structure
        RecordDef def = new RecordDef().set("title").setDouble("age");

        // Example records
        Record record1 = new Record().set("title", "Planet 1").set("age", 19.11);
        Record record2 = new Record().set("title", "Planet 2").set("age", 12.12);

        // Write to file (Json is default format)
        File tmpFile = File.createTempFile("planets", ".json");
        RecordWriter.writeToFile(tmpFile, List.of(record1, record2), def);

        System.out.println("Records written to file: " + tmpFile + "\n" + Files.readString(tmpFile.toPath()));

        // Delete (comment out to check file contents)
        tmpFile.deleteOnExit();
    }

}
