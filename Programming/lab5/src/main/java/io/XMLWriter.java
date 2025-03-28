package io;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import exceptions.NoSuchEnvironmentVariableException;
import managers.CollectionManager;
import model.Worker;
import utility.FileConfiguration;
import utility.XmlMapperConfig;
import utility.wrappers.WorkerKeyList;
import utility.Demapper;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class XMLWriter implements BaseWriter {

    public void writeToFile() throws Exception {
        XmlMapper xmlMapper = new XmlMapperConfig().ConfigureXmlMapper(new XmlMapper());
        Map<Integer, Worker> workerMap = CollectionManager.getInstance().getCollection();
        if (System.getenv("xml_file") == null) {
            throw new NoSuchEnvironmentVariableException("xml_file");
        }
        Path path = Paths.get(System.getenv("xml_file"));
        FileConfiguration.checkWriteFile(path);
        try (PrintWriteWorker printWriteWorker = new PrintWriteWorker(path)){
            if (CollectionManager.getInstance().getCollection().isEmpty()) {
                printWriteWorker.write("");
                return;
            }
            WorkerKeyList demappedCollection = demapCollection(workerMap);
            String xmlString = xmlMapper.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(demappedCollection);
            printWriteWorker.write(xmlString);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private WorkerKeyList demapCollection(Map<Integer, Worker> workerMap){
        Demapper demapper = new Demapper();
        WorkerKeyList workerKeyList = new WorkerKeyList();

        demapper.Demap(workerMap);
        workerKeyList.setWorkerKeys(demapper.getWorkerKeys());
        return workerKeyList;
    }
}
