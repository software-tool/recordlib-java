package recordlib.write;

import recordlib.Record;
import recordlib.RecordFieldDef;
import recordlib.specification.RecordFieldType;
import recordlib.util.BinaryUtil;

import java.time.Instant;
import java.util.Date;

public class RecordWriteUtil {

    public static String getValueAsString(Record item, RecordFieldDef itemValueSpec) {
        String name = itemValueSpec.getName();
        RecordFieldType type = itemValueSpec.getType();

        return getValueAsString(item, type, name);
    }

    public static String getValueAsString(Record item, RecordFieldType valueType, String name) {
        String result = switch (valueType) {
            case STRING -> item.getStringValue(name);
            case BOOLEAN -> item.getBooleanValue(name) + "";
            case LONG -> item.getLongValue(name) + "";
            case DOUBLE -> asString(item.getDoubleValue(name));
            case DATE -> asString(item.getDateValue(name));
            case BINARY -> asString(item.getBinaryValue(name));
        };

        if (result != null) {
            return result;
        }

        return item.getStringValue(name);
    }

    private static String asString(Double value) {
        if (value == null) {
            return "";
        }

        return value.toString();
    }

    private static String asString(Date value) {
        if (value == null) {
            return "";
        }

        Instant instant = value.toInstant();
        return instant.toString();
    }

    private static String asString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }

        return BinaryUtil.asString(bytes);
    }
}
