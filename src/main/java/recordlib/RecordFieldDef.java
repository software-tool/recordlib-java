package recordlib;

import recordlib.specification.RecordFieldHint;
import recordlib.specification.RecordFieldType;
import recordlib.util.EqualsUtil;

import java.util.Objects;

public class RecordFieldDef {

    private String name;
    private RecordFieldType valueType;

    // Optional: Detailed information
    private RecordFieldHint valueHint;

    public RecordFieldDef(String name, RecordFieldType valueType) {
        this.name = name;
        this.valueType = valueType;
    }

    public RecordFieldDef(String name, RecordFieldHint valueHint) {
        this.name = name;
        this.valueHint = valueHint;
    }

    public String getName() {
        return name;
    }

    public boolean isItemId() {
        return EqualsUtil.isEqual(valueHint, RecordFieldHint.ITEM_ID);
    }

    public RecordFieldType getType() {
        return valueType;
    }

    public RecordFieldType getValueType() {
        return valueType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordFieldDef that = (RecordFieldDef) o;
        return Objects.equals(name, that.name) && valueType == that.valueType && valueHint == that.valueHint;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, valueType, valueHint);
    }
}
