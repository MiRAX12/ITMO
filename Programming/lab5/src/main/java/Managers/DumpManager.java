package Managers;


import Data.Coordinates;
import Data.Worker;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.util.LinkedHashMap;

public class DumpManager {

        /**
         *
         * Запись в файл
         */
    public static void xmlSerialize(LinkedHashMap<Long, Worker> workers) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        String xmlString = xmlMapper
                .writerWithDefaultPrettyPrinter()
                .writeValueAsString(workers.values());

        File xmlOutput = new File("C:\\Users\\nagor\\IdeaProjects\\ITMO\\Programming\\lab5\\serialized.xml");

        FileWriter fileWriter = new FileWriter(xmlOutput);
        fileWriter.write(xmlString);
        System.out.println("Сериализация в XML...");
        fileWriter.close();
    }

    public static Worker xmlDeserialize() throws IOException, XMLStreamException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        xmlMapper.registerModule(new JavaTimeModule());
        InputStream xmlResources = new FileInputStream("C:\\Users\\nagor\\IdeaProjects\\ITMO\\Programming\\lab5\\serialized.xml");

        XMLInputFactory factory = XMLInputFactory.newFactory();
        XMLStreamReader xmlStreamReader = factory.createXMLStreamReader(xmlResources);

        return xmlMapper.readValue(xmlStreamReader, Worker.class);
    }


}
