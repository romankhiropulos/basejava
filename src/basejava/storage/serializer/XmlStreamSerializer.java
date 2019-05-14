package basejava.storage.serializer;


import basejava.model.*;
import basejava.util.XmlParser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements SerializationStrategy {
    private XmlParser xmlParser;

    public XmlStreamSerializer() {
        xmlParser = new XmlParser(
                Resume.class, Location.class, Link.class,
                LocationSection.class, TextSection.class, ProgressSection.class, Location.Position.class);
    }

    @Override
    public void makeWrite(Resume resume, OutputStream os) throws IOException {
        try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
            xmlParser.marshall(resume, w);
        }
    }

    @Override
    public Resume makeRead(InputStream is) throws IOException {
        try (Reader resume = new InputStreamReader(is, StandardCharsets.UTF_8)) {
            return xmlParser.unmarshall(resume);
        }
    }
}
