package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.NoSuchEnvironmentVariableException;
import managers.CollectionManager;
import utility.FileConfiguration;
import utility.Remapper;
import utility.XmlMapperConfig;
import utility.wrappers.WorkerKeyList;
import utility.Demapper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLWriter implements BaseWriter {

    public void writeToFile() throws Exception {
        XmlMapper xmlMapper = new XmlMapperConfig().ConfigureXmlMapper(new XmlMapper());

        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnvironmentVariableException();
        }
        Path path = Paths.get(System.getenv("xml_file"));
        FileConfiguration.checkWriteFile(path);

        try (BufferedWriteWorker bufferedWriteWorker = new BufferedWriteWorker(path)){
            Demapper demapper = new Demapper();
            WorkerKeyList workerKeyList = new WorkerKeyList();

            if (CollectionManager.getInstance().getCollection().isEmpty()) {
                bufferedWriteWorker.write("");
                return;
            }
            demapper.Demap(CollectionManager.getInstance().getCollection());
            workerKeyList.setWorkerKeys(demapper.getWorkerKeys());
            String xmlString = xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(workerKeyList);
            bufferedWriteWorker.write(xmlString);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
