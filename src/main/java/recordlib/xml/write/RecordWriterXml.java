package recordlib.xml.write;

import org.jdom2.Element;
import recordlib.Record;
import recordlib.RecordDef;
import recordlib.RecordFieldDef;
import recordlib.write.RecordWriteUtil;
import recordlib.xml.XmlUtil;
import recordlib.xml.spec.RecordDefHelperXml;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class RecordWriterXml {

    public static void writeToFile(File file, List<Record> items, RecordDef itemSpec) throws IOException {
        XmlUtil.writeXml(file, RecordWriterXml.writeItems(null, items, itemSpec));
    }

    public static String writeToString(List<Record> items, RecordDef itemSpec) throws IOException {
        String xml = XmlUtil.writeXml(RecordWriterXml.writeItems(null, items, itemSpec));
        return xml;
    }

    public static Element writeItems(Element parent, List<Record> items, RecordDef itemSpec) {
        String name = getName(itemSpec, items);

        if (parent == null) {
            parent = new Element(name);
        }

        for (Record item : items) {
            Element element = writeItem(item, itemSpec);
            parent.addContent(element);
        }

        return parent;
    }

    public static Element writeItem(Record item, RecordDef itemSpec) {
        String name = getName(itemSpec, item);
        Element element = new Element(name);

        // Id

        RecordFieldDef valueSpecId = RecordDefHelperXml.getIdSpec(itemSpec);
        Long id = item.getId();
        if (id != null) {
            element.setAttribute(valueSpecId.getName(), id.toString());
        }

        for (RecordFieldDef itemValue : itemSpec.getSpec()) {
            String asString = RecordWriteUtil.getValueAsString(item, itemValue);

            Element childElement = new Element(itemValue.getName());
            childElement.setText(asString);

            element.addContent(childElement);
        }

        return element;
    }

    private static String getName(RecordDef spec, Record item) {
        String name = spec.getName();
        if (name == null && item != null) {
            // Find name in item
            name = item.getName();
        }

        return name;
    }

    private static String getName(RecordDef spec, List<Record> items) {
        String name = spec.getName();
        if (name == null) {
            // Find name in items

            for(Record item : items) {
                String nameFound = item.getName();
                if (nameFound != null) {
                    name = nameFound;
                    break;
                }
            }
        }

        return name;
    }
}
