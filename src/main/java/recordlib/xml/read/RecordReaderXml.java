package recordlib.xml.read;

import org.jdom2.Element;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.read.RecordParseUtil;
import recordlib.specification.RecordFieldType;
import recordlib.util.Log;
import recordlib.xml.XmlUtil;
import recordlib.xml.spec.RecordDefHelperXml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecordReaderXml {

    public static List<Record> readItemsFromFile(File file, RecordDef itemSpec) {
        List<Record> items = RecordReaderXml.readItems(XmlUtil.readXml(file), itemSpec);
        return items;
    }

    public static List<Record> readItemsFromString(String xml, RecordDef itemSpec) {
        List<Record> items = RecordReaderXml.readItems(XmlUtil.readXml(xml), itemSpec);
        return items;
    }

    public static List<Record> readItems(Element parent, RecordDef itemSpec) {
        List<Record> result = new ArrayList();
        if (parent == null) {
            return result;
        }

        for (Element element : parent.getChildren(itemSpec.getName())) {
            Record item = readItem(element, itemSpec);
            result.add(item);
        }

        return result;
    }

    public static Record readItem(Element element, RecordDef itemSpec) {
        Record item = new Record(element.getName());

        // Id
        RecordFieldDef valueSpecId = RecordDefHelperXml.getIdSpec(itemSpec);
        Long id = getLongValue(element, valueSpecId.getName());
        if (id != null) {
            item.setId(id);
        }

        // TODO ParentId
        /*if (typeDefinition.hasParent()) {
            Long id = getLongValue(element, C.PARENT_ID);
            String parentType = getValue(element, C.PARENT_TYPE);
            if (id != null && parentType != null) {
                record.setParentInfo(parentType, id);
            }
        }*/

        // Values
        for (RecordFieldDef valueSpec : itemSpec.getSpec()) {
            if (valueSpec.isItemId()) {
                // Treated above
                continue;
            }

            String name = valueSpec.getName();
            RecordFieldType type = valueSpec.getType();

            String value = element.getAttributeValue(name);
            if (value == null) {
                value = element.getChildText(name);
            }

            RecordParseUtil.setParsedValue(item, name, type, value, true);
        }

        return item;
    }

    public static Long getLongValue(Element element, String name) {
        String valueString = element.getAttributeValue(name);
        if (valueString == null) {
            valueString = element.getChildText(name);
        }

        if (valueString != null) {
            Long value = null;

            try {
                value = Long.valueOf(valueString);
            } catch (NumberFormatException e) {
                Log.warning("Cannot parse long value", valueString);
            }

            return value;
        }

        return null;
    }
}
