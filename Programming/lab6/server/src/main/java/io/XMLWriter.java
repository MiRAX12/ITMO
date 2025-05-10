package io;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import exceptions.NoSuchEnvironmentVariableException;
import jakarta.validation.Validator;
import managers.CollectionManager;
import model.Worker;
import utility.FileConfiguration;
import utility.XmlMapperConfig;
import utility.wrappers.WorkerKeyList;
import utility.Demapper;
import utility.wrappers.WorkerKeys;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

/**
 * XML implementation of the {@link BaseWriter} interface. It writes
 * a <code>Integer</code>/<code>Worker</code> map to XML file by given path
 *
 * @author Mirax
 * @see BaseWriter
 * @since 1.0
 */
public class XMLWriter implements BaseWriter {

    /**
     * Writes <code>Integer</code>/<code>Worker</code> map
     * to the XML file using {@link XmlMapper}
     * <p>
     * Path to XML file is taken from environment variable.
     * * {@link XmlMapperConfig} is used to perform configuration of {@link XmlMapper}.
     * </p>
     * <p>
     * If collection is empty, it writes an empty string to the file
     * Otherwise it transforms the map to a {@link WorkerKeyList} using {@link Demapper},
     * serializes the list to a {@code xmlString} and writes the string
     * to the file using {@link BufferedWriteWorker}
     * </p>
     *
     * <p>If any exception occurs, its message is printed to the console.
     *
     * @throws NoSuchEnvironmentVariableException if environment variable wasn't found
     * @see XmlMapper
     * @see WorkerKeyList
     * @see Demapper
     * @see BufferedWriteWorker
     *
     * @since 1.0
     */
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

    /**
     * Transform {@code workerMap} to a list {@link WorkerKeyList} using {@link Demapper}
     *
     * @param workerMap A map of workers to be transformed to list
     * @return {@link WorkerKeyList} instance
     * @see WorkerKeyList
     * @see Demapper
     *
     * @since 1.0
     */
    private WorkerKeyList demapCollection(Map<Integer, Worker> workerMap){
        Demapper demapper = new Demapper();
        WorkerKeyList workerKeyList = new WorkerKeyList();

        demapper.Demap(workerMap);
        workerKeyList.setWorkerKeys(demapper.getWorkerKeys());
        return workerKeyList;
    }
}
