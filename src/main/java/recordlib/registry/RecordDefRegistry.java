package recordlib.registry;

import recordlib.RecordDef;

import java.util.HashMap;
import java.util.Map;

public class RecordDefRegistry {

    private Map<String, RecordDef> recordDefs = new HashMap();

    public RecordDef get(String name) {
        return recordDefs.get(name);
    }

    public RecordDef getOrCreate(String name) {
        RecordDef found = recordDefs.get(name);

        if (found == null) {
            found = new RecordDef(name);
            recordDefs.put(name, found);
        }

        return found;
    }

}
