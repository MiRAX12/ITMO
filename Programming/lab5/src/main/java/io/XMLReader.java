package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import model.Worker;
import exceptions.NoSuchEnvironmentVariableException;
import utility.FileConfiguration;
import utility.XmlMapperConfig;
import utility.wrappers.WorkerKeyList;
import utility.Remapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class XMLReader implements BaseReader {

    @Override
    public Map<Integer, Worker> readFromFile() throws Exception {
        XmlMapper xmlMapper = new XmlMapperConfig().ConfigureXmlMapper(new XmlMapper());

        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnvironmentVariableException();
        }
        Path path = Paths.get(System.getenv("xml_file"));
        FileConfiguration.checkReadFile(path);

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

