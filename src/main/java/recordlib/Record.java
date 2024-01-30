package recordlib;

import recordlib.constant.RecordConstants;
import recordlib.specification.RecordFieldType;
import recordlib.storage.Storage;
import recordlib.util.BooleanUtil;
import recordlib.util.DoubleUtil;
import recordlib.util.LongUtil;

import java.util.*;

public class Record {

    // Identifier
    private RecordId recordId = new RecordId();

    // Content
    private Object value = null;
    private List<Record> records = new ArrayList();

    // Optional: Parent
    private Record parentRecord = null;

    // Optional: Storage information
    private Storage storage;

    public Record() {
    }

    public Record(String name) {
        setName(name);
    }

    public Record(long id, String name) {
        this(name);
        this.recordId.setId(id);
    }

    public Record(long id, String name, String value) {
        this(id, name);
        set(value);
    }

    public Record(String name, String value) {
        this(name);
        this.value = value;
    }

    public Record(String name, Double value) {
        this(name);
        this.value = value;
    }

    public Record(String name, Boolean value) {
        this(name);
        this.value = value;
    }

    public Record(String name, Long value) {
        this(name);
        this.value = value;
    }

    public Record(String name, Date value) {
        this(name);
        this.value = value;
    }

    public Record(String name, Object value) {
        this(name);
        this.value = value;
    }

    public Record(String name, byte[] value) {
        this(name);
        this.value = value;
    }

    public Record(String name, String contentName, Object contentValue) {
        this(name);

        add(contentName, contentValue);
    }

    public Record(RecordId recordId) {
        this(recordId.getId(), recordId.getName());
    }

    public Object getValue(String key) {
        Record item = getSubitem(key);
        if (item == null) {
            return null;
        }

        return item.getValue();
    }

    public Record add(Record record) {
        return addItem(record);
    }

    public Record addRecord(Record record) {
        if (record != null) {
            records.add(record);
        }

        return this;
    }

    public Record addItem(Record item) {
        if (item != null) {
            records.add(item);
        }

        return this;
    }

    public Record addItem(Record item, int index) {
        if (item != null) {
            records.add(index, item);
        }

        return this;
    }

    public Record addRecords(List<Record> items) {
        if (items != null) {
            this.records.addAll(items);
        }

        return this;
    }

    public Record addItems(List<Record> items) {
        return addRecords(items);
    }

    public Record addContent(Record... items) {
        if (items != null) {
            this.records.addAll(Arrays.stream(items).toList());
        }

        return this;
    }

    // Add value - short

    public Record add(String name, Object value) {
        addValue(name, value);

        return this;
    }

    // Add value

    public Record addValue(String name, String value) {
        Record item = new Record(name, value);
        records.add(item);
        return item;
    }

    public void addValue(String name, Double value) {
        records.add(new Record(name, value));
    }

    public void addValue(String name, Date value) {
        records.add(new Record(name, value));
    }

    public void addValue(String name, Long value) {
        records.add(new Record(name, value));
    }

    public void addValue(String name, Object value) {
        records.add(new Record(name, value));
    }

    public void addValue(String name, byte[] value) {
        records.add(new Record(name, value));
    }

    public Record addValue(String name, Boolean value) {
        Record item = new Record(name, value);
        records.add(item);
        return item;
    }

    public Record addItem(String name) {
        Record item = new Record(name);
        records.add(item);
        return item;
    }

    // Setter

    public void setName(String name) {
        this.recordId.setName(name);
    }

    public void setId(Long id) {
        this.recordId.setId(id);
    }

    // Setter - basic

    public Record set(Object value) {
        this.value = value;
        return this;
    }

    public Record set(String value) {
        this.value = value;
        return this;
    }

    public Record set(Long value) {
        this.value = value;
        return this;
    }

    public Record set(Double value) {
        this.value = value;
        return this;
    }

    public Record set(Boolean value) {
        this.value = value;
        return this;
    }

    public Record set(Date value) {
        this.value = value;
        return this;
    }

    public Record set(byte[] value) {
        this.value = value;
        return this;
    }

    // Setter - named

    public Record setValue(Object value) {
        this.value = value;
        return this;
    }

    public Record setStringValue(String value) {
        this.value = value;
        return this;
    }

    public Record setLongValue(Long value) {
        this.value = value;
        return this;
    }

    public Record setDoubleValue(Double value) {
        this.value = value;
        return this;
    }

    public Record setBooleanValue(Boolean value) {
        this.value = value;
        return this;
    }

    public Record setDateValue(Date value) {
        this.value = value;
        return this;
    }

    public Record setBinaryValue(byte[] value) {
        this.value = value;
        return this;
    }

    // Setter - short

    public Record setString(String value) {
        return setStringValue(value);
    }

    public Record setLong(Long value) {
        return setLongValue(value);
    }

    public Record setLong(Integer value) {
        return setLongValue(value == null ? null : value.longValue());
    }

    public Record setDouble(Double value) {
        return setDoubleValue(value);
    }

    public Record setBoolean(Boolean value) {
        return setBooleanValue(value);
    }

    public Record setDate(Date value) {
        return setDateValue(value);
    }

    public Record setBinary(byte[] value) {
        return setBinaryValue(value);
    }

    // Content setter - named

    public Record set(String key, String value) {
        return setStringValue(key, value);
    }

    public Record set(String key, Long value) {
        return setLongValue(key, value);
    }

    public Record set(String key, Double value) {
        return setDoubleValue(key, value);
    }

    public Record set(String key, Boolean value) {
        return setBooleanValue(key, value);
    }

    public Record set(String key, Date value) {
        return setDateValue(key, value);
    }

    public Record set(String key, Object value) {
        return setObjectValue(key, value);
    }

    public Record set(String key, byte[] value) {
        return setBinaryValue(key, value);
    }

    // Content setter - named

    public Record setStringValue(String key, String value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setStringValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setLongValue(String key, Long value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setLongValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setLongValue(String key, Integer value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setLongValue(value == null ? null : value.longValue());
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setDoubleValue(String key, Double value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setDoubleValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setBooleanValue(String key, Boolean value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setBooleanValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setDateValue(String key, Date value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setDateValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setObjectValue(String key, Object value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setBinaryValue(String key, byte[] value) {
        validateSetter(key);

        Record found = getSubitem(key);
        if (found != null) {
            found.setBinaryValue(value);
        } else {
            addValue(key, value);
        }

        return this;
    }

    public Record setValue(String key, Date value) {
        setObjectValue(key, value);

        return this;
    }

    public Record setString(String key, String value) {
        return setStringValue(key, value);
    }

    public Record setLong(String key, Long value) {
        return setLongValue(key, value);
    }

    public Record setLong(String key, Integer value) {
        return setLongValue(key, value);
    }

    public Record setBoolean(String key, Boolean value) {
        return setBooleanValue(key, value);
    }

    public Record setDate(String key, Date value) {
        return setDateValue(key, value);
    }

    public Record setBinary(String key, byte[] value) {
        return setBinaryValue(key, value);
    }

    public Record setObject(String key, Object value) {
        return setObjectValue(key, value);
    }

    // Getter

    public Object get() {
        return value;
    }

    public Object getValue() {
        return value;
    }

    public String getName() {
        if (recordId == null) {
            return null;
        }

        return recordId.getName();
    }

    public RecordFieldType getType() {
        if (value instanceof String) {
            return RecordFieldType.STRING;
        }
        if (value instanceof Boolean) {
            return RecordFieldType.BOOLEAN;
        }
        if (value instanceof Long) {
            return RecordFieldType.LONG;
        }
        if (value instanceof Double) {
            return RecordFieldType.DOUBLE;
        }
        if (value instanceof Date) {
            return RecordFieldType.DATE;
        }
        if (value instanceof byte[]) {
            return RecordFieldType.BINARY;
        }

        return null;
    }

    public Long getId() {
        if (recordId == null) {
            return null;
        }

        return recordId.getId();
    }

    public String getString() {
        return getStringValue();
    }

    public Long getLong() {
        return getLongValue();
    }

    public Double getDouble() {
        return getDoubleValue();
    }

    public Date getDate() {
        return getDateValue();
    }

    public Boolean getBoolean() {
        return getBooleanValue();
    }

    // Getter - named

    public String getStringValue() {
        if (value instanceof String) {
            return (String) value;
        }

        return null;
    }

    public Long getLongValue() {
        if (value instanceof Long) {
            return (Long) value;
        }

        return null;
    }

    public Double getDoubleValue() {
        if (value instanceof Double) {
            return (Double) value;
        }

        return null;
    }

    public Date getDateValue() {
        if (value instanceof Date) {
            return (Date) value;
        }

        return null;
    }

    public Boolean getBooleanValue() {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        return null;
    }

    public byte[] getBinaryValue() {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }

        return null;
    }

    // Content getter

    public String get(String key) {
        return getStringValue(key);
    }

    public String getString(String key) {
        return getStringValue(key);
    }

    public Boolean getBoolean(String key) {
        return getBooleanValue(key);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return getBooleanValue(key, defaultValue);
    }

    public Long getLong(String key) {
        return getLongValue(key);
    }

    public Double getDouble(String key) {
        return getDoubleValue(key);
    }

    public Date getDate(String key) {
        return getDateValue(key);
    }

    public byte[] getBinary(String key) {
        return getBinaryValue(key);
    }

    // Content getter - named

    public String getStringValue(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            return found.getStringValue();
        }

        return null;
    }

    public Boolean getBooleanValue(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            return found.getBooleanValue();
        }

        return null;
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        Boolean found = getBooleanValue(key);
        if (found == null) {
            return defaultValue;
        }

        return found;
    }

    public Long getLongValue(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            return found.getLongValue();
        }

        return null;
    }

    public Double getDoubleValue(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            return found.getDoubleValue();
        }

        return null;
    }

    public Date getDateValue(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            return found.getDateValue();
        }

        return null;
    }

    public byte[] getBinaryValue(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            return found.getBinaryValue();
        }

        return null;
    }

    public List<Record> getItems() {
        return records;
    }

    public List<Record> getRecords() {
        return records;
    }

    public List<Record> getContent() {
        return records;
    }

    // Converted

    public Double getDoubleValueParsed(String key, Double defaultValue) {
        Record found = getSubitem(key);
        if (found != null) {
            String str = found.getStringValue();
            return DoubleUtil.parse(str, defaultValue);
        }

        return defaultValue;
    }

    public Long getLongValueParsed(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            String str = found.getStringValue();
            return LongUtil.parse(str);
        }

        return null;
    }

    public Boolean getBooleanValueParsed(String key) {
        Record found = getSubitem(key);
        if (found != null) {
            String str = found.getStringValue();
            return BooleanUtil.parse(str);
        }

        return null;
    }

    public boolean getBooleanValueParsed(String key, boolean defaultValue) {
        Boolean result = getBooleanValueParsed(key);
        if (result == null) {
            return defaultValue;
        }

        return result.booleanValue();
    }

    // Find

    public Record getContent(String name) {
        return getSubitem(name);
    }

    public Record getSubitem(String name) {
        if (name == null) return null;

        for (Record sub : records) {
            if (name.equals(sub.getName())) {
                return sub;
            }
        }

        return null;
    }

    public Record getSubitem(int index) {
        if (records.size() <= index) {
            return null;
        }
        return records.get(index);
    }

    public Record getOrCreateItem(String name) {
        Record found = getSubitem(name);
        if (found == null) {
            return addItem(name);
        }

        return found;
    }

    public List<Record> getRecords(String name) {
        List<Record> found = new ArrayList();

        for (Record sub : records) {
            if (sub.getName().equals(name)) {
                found.add(sub);
            }
        }

        return found;
    }

    public List<Record> getSubitems(String name) {
        return getRecords(name);
    }

    public Record getRecord(String name, int index) {
        int currentIndex = -1;

        for (Record sub : records) {
            if (sub.getName().equals(name)) {
                currentIndex++;

                if (currentIndex == index) {
                    return sub;
                }
            }
        }

        return null;
    }

    public Record getSubitem(String name, int index) {
        return getRecord(name, index);
    }

    public boolean hasItem(String name) {
        Record found = getSubitem(name);
        return found != null;
    }

    public boolean hasValue() {
        return value != null;
    }

    public boolean hasItems() {
        return !records.isEmpty();
    }

    public boolean hasRecords() {
        return !records.isEmpty();
    }

    public boolean hasContent() {
        return !records.isEmpty();
    }

    // Misc

    public void clear() {
        clearValue();

        records.clear();
    }

    public void clearValue() {
        this.value = null;
    }

    /**
     * Returns list of names of the child-items
     */
    public List<String> getNames() {
        List<String> names = new ArrayList<>();
        records.forEach(item -> names.add(item.getName()));
        return names;
    }

    private void validateSetter(String name) {
        if (RecordConstants.NAME_ID.equals(name)) {
            throw new IllegalArgumentException("Cannot set value, this name is reserved for internal use: '" + name + "'");
        }
    }

    // Parent

    public Long getParentId() {
        if (recordId == null) {
            return null;
        }

        return recordId.getParentId();
    }

    public RecordId getParentItemId() {
        if (recordId == null) {
            return null;
        }

        return recordId.getParentItemId();
    }

    public boolean hasParent() {
        return recordId.hasParent();
    }

    public String getParentName() {
        if (recordId == null) {
            return null;
        }

        return recordId.getParentName();
    }

    public Record duplicate() {
        Record newRecord = new Record(getName());
        newRecord.value = value;

        for (Record subrecord : records) {
            Record subrecordDuplicate = subrecord.duplicate();
            newRecord.add(subrecordDuplicate);
        }
        return newRecord;
    }

    public String toString() {
        return toStringMultiline(false, 0);
    }

    public String toStringMultiline(boolean continueLevel, int level) {
        StringBuilder sb = new StringBuilder();

        String name = getName();
        //System.out.println("name: '" + name + "'");

        if (continueLevel) {
            sb.append("\n");
        }
        for (int indent=0; indent<level; indent++) {
            sb.append(" ");
        }
        sb.append(name);
        if (recordId.hasId()) {
            sb.append(":");
            sb.append(recordId.getId());
        }
        if (hasValue()) {
            Object value = getValue();

            sb.append("=");

            if (value instanceof String) {
                sb.append("\"");
            }

            sb.append(value);

            if (value instanceof String) {
                sb.append("\"");
            }
        }

        if (hasContent()) {
            sb.append(" ");
            sb.append("{\n");

            int i = 0;
            for (Record item : records) {
                if (i > 0) {
                    sb.append(", ");
                }

                sb.append(item.toStringMultiline(i > 0, level + 1));

                i++;
            }

            sb.append("\n");
            for (int indent=0; indent<level; indent++) {
                sb.append(" ");
            }
            sb.append("}");
        }

        return sb.toString();
    }

    public String toStringSingleLine() {
        StringBuilder sb = new StringBuilder();

        String name = getName();
        //System.out.println("name: '" + name + "'");

        sb.append(name);
        if (recordId.hasId()) {
            sb.append(":");
            sb.append(recordId.getId());
        }
        if (hasValue()) {
            Object value = getValue();

            sb.append("=");

            if (value instanceof String) {
                sb.append("\"");
            }

            sb.append(value);

            if (value instanceof String) {
                sb.append("\"");
            }
        }

        if (hasContent()) {
            sb.append(" ");
            sb.append("{ ");

            int i = 0;
            for (Record item : records) {
                if (i > 0) {
                    sb.append(", ");
                }

                sb.append(item.toStringSingleLine());

                i++;
            }

            sb.append(" }");
        }

        return sb.toString();
    }

    // Hashcode, Equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record item = (Record) o;
        return Objects.equals(recordId, item.recordId) && Objects.equals(value, item.value) && Objects.equals(records, item.records) && Objects.equals(parentRecord, item.parentRecord) && Objects.equals(storage, item.storage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(recordId, value, records, parentRecord, storage);
    }

    public Record cloneValuesOnly() {
        Record newItem = new Record(getName());

        if (value instanceof Date) {
            newItem.value = Date.from(((Date)value).toInstant());
        } else {
            newItem.value = value;
        }

        for (Record item : getItems()) {
            newItem.addItem(item.cloneValuesOnly());
        }

        return newItem;
    }

    // Static

    public static Record create(String name) {
        return new Record(name);
    }

    public static Record create(String name, Long id) {
        return new Record(name, id);
    }
}
