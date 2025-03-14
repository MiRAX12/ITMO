package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Worker;
import exceptions.NoSuchEnvironmentVariableException;
import utility.wrappers.WorkerKeyList;
import utility.Remapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class XMLReader implements BaseReader {

    @Override
    public Map<Integer, Worker> readFromFile() {
        Path path = Paths.get(System.getenv("xml_file"));
        XmlMapper xmlMapper = new XmlMapper();

        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnvironmentVariableException();
        }

        try (BufferedReadWorker bufferedReadWorker = new BufferedReadWorker(path)) {
            Remapper remapper = new Remapper();

            String XMLString = bufferedReadWorker.read();
            if (!XMLString.isEmpty()) {
                WorkerKeyList workerKeyList = xmlMapper.readValue(XMLString, WorkerKeyList.class);
                remapper.Remap(workerKeyList.getWorkerKeys());
                return remapper.getMap();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

