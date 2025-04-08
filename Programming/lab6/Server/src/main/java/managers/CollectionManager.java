package managers;

import io.IdGenerator;
import io.XMLWriter;
import model.Worker;
import io.XMLReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Manages the collection of {@link Worker} objects.
 * <p>
 * A singleton class which provides methods to add workers to the collection load and save
 * the collection of workers from and to a file, as well as access the current map
 * of workers in memory.
 * </p>
 *
 * @see Worker
 * @see XMLReader
 * @since 1.0
 */
public class CollectionManager {
    private static final CollectionManager INSTANCE = new CollectionManager();
    private final Map<Integer, Worker> collection = new LinkedHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(CollectionManager.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private CollectionManager() {}

    /**
     * Returns the singleton instance of {@code CollectionManager}.
     * <p>
     * If the instance does not exist, creates it. Otherwise, returns the existing one
     * </p>
     *
     * @return the singleton instance of {@code CollectionManager}
     */
    public static CollectionManager getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the current map of workers.
     *
     * @return a {@link Map} of {@link Worker} objects
     */
    public Map<Integer, Worker> getCollection() {
        return collection;
    }

    /**
     * Loads the collection of persons from the configured file.
     * <p>
     * Clears the current map of workers and fills it with the data read
     * from the file. Any errors occured, its message is printed to the console
     * </p>
     */
    public void load() {
        XMLReader xmlReader = new XMLReader();
        try {
            collection.clear();
            Map<Integer, Worker> workerMap = xmlReader.readFromFile();
            if (workerMap != null){
                collection.putAll(workerMap);
                logger.info("Загружено " + collection.size() + " новых элементов");
//                System.out.printf("Загружено %d новых элементов%n", collection.size());
                return;
            }
            logger.info("Файл пустой, workers не были добавлены");
//            System.out.println("Файл пустой, workers не были добавлены");
        } catch (Exception e) {

            if (e.getMessage() == null) logger.error("Не удалось загрузить workers");
//                System.out.println("Не удалось загрузить workers");
            else logger.error("Не удалось загрузить workers: " + e.getMessage());
//                System.out.println("Не удалось загрузить workers: " + e.getMessage());
        }
    }

    /**
     * Saves the collection of workers to the configured file.
     * <p>
     * Writes the current map of persons to the file using {@link XMLWriter}
     *
     * </p>
     */
    public void save() throws Exception {
        XMLWriter xmlWriter = new XMLWriter();
        xmlWriter.writeToFile();
    }

    /**
     * Adds elements to the <code>Integer</code>/<code>Worker</code> map
     */
    public void addElement(Worker worker, Integer key) {
        collection.put(key, worker);
    }
}

