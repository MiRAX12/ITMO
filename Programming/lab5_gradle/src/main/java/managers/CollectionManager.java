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
            if (xmlReader.readFromFile() != null){
                collection.putAll(xmlReader.readFromFile());
                System.out.printf("Загружено %d новых элементов%n", collection.size());
                return;
            }
            System.out.println("Файл пустой, workers не были добавлены");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void addElement(Worker worker, Integer key) {
        collection.put(key, worker);
    }
//    private static final CollectionManager INSTANCE = new CollectionManager();
//
//    private CollectionManager() {
//    }
//
//    private final Map<Integer, Worker> collection = new LinkedHashMap<>();
//
//    public static CollectionManager getInstance() {
//        return INSTANCE;
//    }
//
//    public Map<Integer, Worker> getCollection() {
//        return collection;
//    }
//
//    public void load() {
//        XMLReader xmlReader = new XMLReader();
//        try {
//            collection.clear();
//            collection.putAll(xmlReader.readFromFile());
//            System.out.printf("Загружено %d новых элементов\n", collection.size());
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public void addElement(Worker worker, Integer key) {
//        collection.put(key, worker);
//    }


}

