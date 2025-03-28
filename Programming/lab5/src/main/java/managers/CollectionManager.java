package managers;

import io.IdGenerator;
import model.Worker;
import io.XMLReader;

import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionManager {
    private static final CollectionManager INSTANCE = new CollectionManager();

    private CollectionManager() {
    }

    private final Map<Integer, Worker> collection = new LinkedHashMap<>();

    public static CollectionManager getInstance() {
        return INSTANCE;
    }

    public Map<Integer, Worker> getCollection() {
        return collection;
    }

    public void load() {
        XMLReader xmlReader = new XMLReader();
        try {
            collection.clear();
            Map<Integer, Worker> workerMap = xmlReader.readFromFile();
            if (workerMap != null){
                collection.putAll(workerMap);
                System.out.printf("Загружено %d новых элементов%n", collection.size());
                return;
            }
            System.out.println("Файл пустой, workers не были добавлены");
        } catch (Exception e) {
            if (e.getMessage() == null) System.out.println("Не удалось загрузить workers");
            else System.out.println("Не удалось загрузить workers: " + e.getMessage());
        }
    }

    public void addElement(Worker worker, Integer key) {
        collection.put(key, worker);
    }
}

