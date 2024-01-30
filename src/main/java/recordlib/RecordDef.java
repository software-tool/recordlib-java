package recordlib;

import recordlib.constant.RecordConstants;
import recordlib.registry.RecordDefRegistry;
import recordlib.specification.RecordFieldType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RecordDef {

    private static RecordDefRegistry registry = new RecordDefRegistry();

    // Optional name
    private String name;

    private boolean autoApplyId = false;

    private List<RecordFieldDef> fieldDefs = new ArrayList();

    public RecordDef() {
        this.name = null;
    }

    public RecordDef(String name) {
        this.name = name;
    }

    public RecordDef add(String name) {
        setString(name);

        return this;
    }

    public RecordDef addString(String name) {
        setString(name);

        return this;
    }

    public RecordDef addBoolean(String name) {
        setBoolean(name);

        return this;
    }

    public RecordDef addLong(String name) {
        setLong(name);

        return this;
    }

    public RecordDef addDouble(String name) {
        setDouble(name);

        return this;
    }

    public RecordDef addDate(String name) {
        setDate(name);

        return this;
    }

    public RecordDef addBinary(String name) {
        setBinary(name);

        return this;
    }

    public RecordDef add(String name, RecordFieldType type) {
        RecordFieldDef spec = new RecordFieldDef(name, type);
        fieldDefs.add(spec);

        return this;
    }

    public RecordDef set(String name) {
        return setString(name);
    }

    public RecordDef setString(String name) {
        validateAddSpec(name);

        RecordFieldDef spec = new RecordFieldDef(name, RecordFieldType.STRING);
        fieldDefs.add(spec);

        return this;
    }

    public RecordDef setBoolean(String name) {
        validateAddSpec(name);

        RecordFieldDef spec = new RecordFieldDef(name, RecordFieldType.BOOLEAN);
        fieldDefs.add(spec);

        return this;
    }

    public RecordDef setLong(String name) {
        validateAddSpec(name);

        RecordFieldDef spec = new RecordFieldDef(name, RecordFieldType.LONG);
        fieldDefs.add(spec);

        return this;
    }

    public RecordDef setDouble(String name) {
        validateAddSpec(name);

        RecordFieldDef spec = new RecordFieldDef(name, RecordFieldType.DOUBLE);
        fieldDefs.add(spec);

        return this;
    }

    public RecordDef setDate(String name) {
        validateAddSpec(name);

        RecordFieldDef spec = new RecordFieldDef(name, RecordFieldType.DATE);
        fieldDefs.add(spec);

        return this;
    }

    public RecordDef setBinary(String name) {
        validateAddSpec(name);

        RecordFieldDef spec = new RecordFieldDef(name, RecordFieldType.BINARY);
        fieldDefs.add(spec);

        return this;
    }

    public List<RecordFieldDef> getSpec() {
        return fieldDefs;
    }

    public RecordFieldDef getSpec(String name) {
        for (RecordFieldDef itemValue : fieldDefs) {
            if (itemValue.getName().equals(name)) {
                return itemValue;
            }
        }

        return null;
    }

    public RecordFieldDef getDef(String name) {
        return getSpec(name);
    }

    public String getName() {
        return name;
    }

    public boolean getAutoApplyId() {
        return autoApplyId;
    }

    public void setAutoApplyId(boolean autoApplyId) {
        this.autoApplyId = autoApplyId;
    }

    public static RecordDef create(String name) {
        return registry.getOrCreate(name);
    }

    public static RecordDef get(String name) {
        return registry.get(name);
    }

    public static RecordDef get(Record record) {
        RecordDef spec = new RecordDef(record.getName());
        for (Record subitem : record.getItems()) {
            spec.add(subitem.getName(), subitem.getType());
        }

        return spec;
    }

    public static RecordDef get(List<Record> records) {
        RecordDef spec = null;
        Record itemTemplate = null;

        for (Record record : records) {
            RecordDef created = get(record);

            if (spec == null) {
                spec = created;
                itemTemplate = record;
            } else {
                if (!spec.equals(created)) {
                    throw new IllegalArgumentException("Cannot store items in list, structure differs from item to item: " + itemTemplate + " <-> " + record);
                }
            }
        }

        return spec;
    }

    private void validateAddSpec(String name) {
        if (RecordConstants.NAME_ID.equals(name)) {
            throw new IllegalArgumentException("Cannot specify attribute, this name is reserved for internal use: '" + name + "'");
        }
    }

    // Equals, Hashcode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordDef itemSpec = (RecordDef) o;
        return autoApplyId == itemSpec.autoApplyId && Objects.equals(name, itemSpec.name) && Objects.equals(fieldDefs, itemSpec.fieldDefs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, autoApplyId, fieldDefs);
    }
}
