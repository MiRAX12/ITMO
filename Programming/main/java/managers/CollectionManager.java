package managers;

import data.Worker;
import io.XMLReader;

import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionManager {
    private static CollectionManager instance;

    private CollectionManager() {
    }

    private final Map<Integer, Worker> collection = new LinkedHashMap<>();

    public static CollectionManager getInstance() {
        return instance == null ? instance = new CollectionManager() : instance;
    }

    public Map<Integer, Worker> getCollection() {
        return collection;
    }

    public void load() {
        XMLReader xmlReader = new XMLReader();
        try {
            collection.clear();
            collection.putAll(xmlReader.readFromFile());
            System.out.println("Загружено %d новых элементов".formatted(collection.size()));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addElement(Worker worker, Integer key) {
        collection.put(key, worker);
    }


}

