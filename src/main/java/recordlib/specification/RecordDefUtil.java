package recordlib.specification;

import recordlib.Record;
import recordlib.RecordDef;
import recordlib.RecordFieldDef;

import java.util.List;

public class RecordDefUtil {

    public static RecordDef getItemSpecFromItems(String specName, List<Record> items) {
        RecordDef spec = new RecordDef(specName);

        for (Record item : items) {
            List<String> names = item.getNames();
            for (String name : names) {
                RecordFieldDef existingSpec = spec.getSpec(name);
                if (existingSpec != null) {
                    continue;
                }

                RecordFieldType type = item.getSubitem(name).getType();
                spec.add(name, type);
            }
        }

        return spec;
    }

}
