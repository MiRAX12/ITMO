package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.NoSuchEnviromentVariableException;
import managers.CollectionManager;
import io.wrappers.WorkerKeyList;
import utility.Demapper;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLWriter {

    public void writeToFile() throws IOException, XMLStreamException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnviromentVariableException("Не найдена переменная окружения %s, ведущая к файлу!");
        }
        Path path = Paths.get(System.getenv("xml_file"));

        try (FileHandler fileHandler = new FileHandler(path)) {


            String serialized = xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(path.toFile());
            fileHandler.write(serialized);

        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
