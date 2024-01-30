package recordlib.specification;

public enum RecordFieldType {

    // Basic
    STRING, LONG, DOUBLE, BOOLEAN,

    // Binary
    BINARY,

    // Date
    DATE;

    public boolean isString() {
        return this == STRING;
    }

    public boolean isBoolean() {
        return this == BOOLEAN;
    }

    public boolean isLong() {
        return this == LONG;
    }

    public boolean isDouble() {
        return this == DOUBLE;
    }

    public boolean isBinary() {
        return this == BINARY;
    }

    // Date

    public boolean isDate() {
        return this == DATE;
    }

}
