package managers;

import Network.User;
import database.Database;
import database.WorkerService;
import model.Worker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * Manages the collection of {@link Worker} objects.
 * <p>
 * A singleton class which provides methods to add workers to the collection load and save
 * the collection of workers from and to a file, as well as access the current map
 * of workers in memory.
 * </p>
 *
 * @see Worker
 * @since 1.0
 */
public class CollectionManager {
    private static final CollectionManager INSTANCE = new CollectionManager();
    private final Map<Long, Worker> collection = new ConcurrentHashMap<>();

    /**
     * Private constructor to prevent instantiation.
     */
    private CollectionManager() {
    }

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
    public Map<Long, Worker> getCollection() {
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
        try {
            collection.clear();
            Map<Long, Worker> workerMap = Database.loadWorkers();
            if (workerMap != null) {
                collection.putAll(workerMap);
                System.out.println("Загружено " + collection.size() + " новых элементов");
                return;
            }
            System.out.println("В базе данных нет Workers");
        } catch (Exception e) {
            if (e.getMessage() == null) {
                System.out.println("Не удалось загрузить workers");
            } else {
                System.out.println("Не удалось загрузить workers: " + e.getMessage());
            }
        }
    }

    /**
     * Adds elements to the <code>Integer</code>/<code>Worker</code> map
     * and Database
     */
    public void addElement(Worker worker, User user) {
        boolean isAdded = false;
        while (!isAdded) {
            try {
                Database.addWorker(worker, user);
                isAdded = true;
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        Long id = Database.getLastUserInsert(user);
        worker.setId(id);
        collection.put(id, worker);
    }
}

