package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import data.Worker;
import exceptions.NoSuchEnviromentVariableException;
import io.wrappers.WorkerKeyList;
import utility.Remapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class XMLReader implements BaseReader {

    @Override
    public Map<Integer, Worker> readFromFile() {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnviromentVariableException("Не найдена переменная окружения %s, ведущая к файлу!".formatted("xml_file"));
        }
        Path path = Paths.get(System.getenv("xml_file"));

        try (FileHandler fileHandler = new FileHandler(path)) {
            String XMLString = fileHandler.read();

            WorkerKeyList workerKeyList = xmlMapper.readValue(XMLString, WorkerKeyList.class);

            Remapper remapper = new Remapper();
            remapper.Remap(workerKeyList.getWorkerKeys());

            return remapper.getMap();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
