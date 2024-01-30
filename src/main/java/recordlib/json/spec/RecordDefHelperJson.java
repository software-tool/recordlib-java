package recordlib.json.spec;

import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.specification.RecordFieldHint;

public class RecordDefHelperJson {

    private static RecordDefHelperJson instance = new RecordDefHelperJson();

    private String idName = "id";

    public String getIdName() {
        return idName;
    }

    public static RecordDefHelperJson getInstance() {
        return instance;
    }

    public static RecordFieldDef getIdSpec(RecordDef itemSpec) {
        String idName = RecordDefHelperJson.getInstance().getIdName();

        RecordFieldDef idSpec = itemSpec.getSpec(idName);
        if (idSpec == null) {
            return new RecordFieldDef(idName, RecordFieldHint.ITEM_ID);
        }

        // Cannot happen
        return null;
    }
}
