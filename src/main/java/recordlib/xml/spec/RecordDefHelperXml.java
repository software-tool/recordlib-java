package recordlib.xml.spec;

import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.specification.RecordFieldHint;

public class RecordDefHelperXml {

    private static RecordDefHelperXml instance = new RecordDefHelperXml();

    private String idName = "id";

    private String parentIdName = "parent_id";
    private String parentName = "parent";

    public String getIdName() {
        return idName;
    }

    public static RecordDefHelperXml getInstance() {
        return instance;
    }

    public static RecordFieldDef getIdSpec(RecordDef itemSpec) {
        String idName = RecordDefHelperXml.getInstance().getIdName();

        RecordFieldDef idSpec = itemSpec.getSpec(idName);
        if (idSpec == null) {
            return new RecordFieldDef(idName, RecordFieldHint.ITEM_ID);
        }

        // Cannot happen
        return null;
    }
}
