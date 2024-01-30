package recordlib.read;

import recordlib.Record;
import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.specification.RecordFieldType;
import recordlib.util.BooleanUtil;
import recordlib.util.DoubleUtil;
import recordlib.util.Log;
import recordlib.util.LongUtil;

import java.time.Instant;
import java.util.Date;

public class RecordParseUtil {

    /**
     * Applies the value to the item
     */
    public static Object setParsedValue(Record item, RecordDef itemSpec, String name, String valueStr, boolean nullForEmptyStrings) {
        RecordFieldDef valueSpec = itemSpec.getSpec(name);

        return setParsedValue(item, name, valueSpec.getType(), valueStr, nullForEmptyStrings);
    }

    /**
     * Applies the value to the item
     */
    public static Object setParsedValue(Record item, String valueName, RecordFieldType valueType, String valueStr, boolean nullForEmptyStrings) {
        if (valueType.isString()) {
            if (nullForEmptyStrings && valueStr != null && valueStr.trim().isEmpty()) {
                valueStr = null;
            }

            item.setStringValue(valueName, valueStr);
            return valueStr;
        } else if (valueType.isBoolean()) {
            Boolean value = null;
            try {
                // Hier kein Fallback auf false, weil dies z.B. bei Binary auch nicht der Fall ist
                value = BooleanUtil.parse(valueStr);
            } catch (Exception e) {
                Log.warning("Failed to parse boolean", valueStr);
            }

            item.setBooleanValue(valueName, value);
            return value;
        } else if (valueType.isDate()) {
            Date value = null;

            if (valueStr != null && !valueStr.isEmpty()) {
                Instant parsed = Instant.parse(valueStr);
                if (parsed != null) {
                    value = Date.from(parsed);
                }
            }

            item.setDateValue(valueName, value);
            return value;
        } else if (valueType.isLong()) {
            Long value = null;

            try {
                value = LongUtil.parse(valueStr);
            } catch (Exception e) {
                Log.warning("Failed to parse long", valueStr);
            }

            item.setLongValue(valueName, value);
            return value;
        } else if (valueType.isDouble()) {
            Double value = null;

            try {
                value = DoubleUtil.parse(valueStr);
            } catch (Exception e) {
                Log.warning("Failed to parse double", valueStr);
            }

            item.setDoubleValue(valueName, value);
            return value;
        }

        throw new IllegalStateException("Invalid data type: " + valueType);
    }

}
