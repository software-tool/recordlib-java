package recordlib.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.charset.Charset;

public class XmlUtil {

    private static Charset UTF8_CHARSET = Charset.forName("UTF-8");

    public static Element readXml(String xmlString) {
        if (xmlString == null) {
            return null;
        }

        try (InputStream inputStream = new ByteArrayInputStream(xmlString.getBytes(UTF8_CHARSET)))  {
            SAXBuilder sax = new SAXBuilder();
            Document doc = sax.build(inputStream);

            Element rootNode = doc.getRootElement();
            return rootNode;
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static Element readXml(File file) {
        if (!file.exists() || !file.isFile()) {
            return null;
        }

        try (InputStream inputStream = new FileInputStream(file))  {
            SAXBuilder sax = new SAXBuilder();
            Document doc = sax.build(inputStream);

            Element rootNode = doc.getRootElement();
            return rootNode;
        } catch (IOException | JDOMException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void writeXml(File file, Element rootElement) throws IOException {
        if (file.exists() && !file.isFile()) {
            throw new IOException("Cannot write file, non-file exists: " + file.getAbsolutePath());
        }

        Document doc = new Document(rootElement);

        try (OutputStream outputStream = new FileOutputStream(file))  {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(getFormatDefault());
            outputter.output(doc, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String writeXml(Element rootElement) {
        Document doc = new Document(rootElement);

        try (ByteArrayOutputStream byteArr = new ByteArrayOutputStream())  {
            XMLOutputter outputter = new XMLOutputter();
            outputter.setFormat(getFormatDefault());
            outputter.output(doc, byteArr);

            return byteArr.toString(UTF8_CHARSET);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static Format getFormatDefault() {
        Format format = Format.getPrettyFormat();
        format.setEncoding(UTF8_CHARSET.name());
        return format;
    }
}
