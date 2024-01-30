# RecordLib

A quick **tool library to read/write Records** for Java.

`Records` have values (String, Long, Double, Boolean, Date, byte[]) and are **mutable**.

(do not confuse with `java.lang.Record`)

Records do have

* `Name` (optional)
* `Id` (Long; optional)
* `Child records` as needed

Value Types:

* String
* Long
* Double
* Boolean
* Date
* byte[]

## Examples

### Record instances

```
import recordlib.Record;

Record record1 = new Record()
  .set("title", "Big Mountains")
  .set("subtitle", "Learn about the biggest mountains in the world")
  .set("value", 1.96112); // Detected as double
  .setLong("pages", 202)
  .setDate("published", new Date());

System.out.println("Record: " + record1);
```

### Persistence

Writes two Records to a file (describing planets):

```
import recordlib.Record;
import recordlib.RecordDef;

public void writeRecord() throws java.io.IOException {
    // Define structure
    RecordDef def = new RecordDef().set("title").setDouble("age");
    
    // Example records
    Record record1 = new Record().set("title", "Planet 1").set("age", 19.11);
    Record record2 = new Record().set("title", "Planet 2").set("age", 12.12);
    
    // Write to file (Json is default format)
    File tmpFile = File.createTempFile("planets", ".json");
    RecordWriter.writeToFile(tmpFile, List.of(record1, record2), def);
    
    System.out.println("Records written to file: " + tmpFile + "\n" + java.nio.file.Files.readString(tmpFile.toPath()));
}
```