package recordlib.read;

import recordlib.json.read.RecordReaderJson;

public class RecordReader extends RecordReaderJson {

    public RecordReader(String jsonText) {
        super(jsonText);
    }

    public RecordReader(String jsonText, boolean parseToDate) {
        super(jsonText, parseToDate);
    }
}
