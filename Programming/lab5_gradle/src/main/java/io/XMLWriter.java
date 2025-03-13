package io;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import exceptions.NoSuchEnviromentVariableException;
import managers.CollectionManager;
import io.wrappers.WorkerKeyList;
import utility.Demapper;

import java.nio.file.Path;
import java.nio.file.Paths;

public class XMLWriter implements BaseWriter {

    public void writeToFile(){
        XmlMapper xmlMapper = new XmlMapper();
        Path path = Paths.get(System.getenv("xml_file"));

        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnviromentVariableException();
        }

        try (FileWriteWorker fileWriteWorker = new FileWriteWorker(path)){
            Demapper demapper = new Demapper();
            WorkerKeyList workerKeyList = new WorkerKeyList();

            if (CollectionManager.getInstance().getCollection().isEmpty()) {
                fileWriteWorker.write("");
                return;
            }
            demapper.Demap(CollectionManager.getInstance().getCollection());
            workerKeyList.setWorkerKeys(demapper.getWorkerKeys());
            String serialized = xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(workerKeyList);
            fileWriteWorker.write(serialized);
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
