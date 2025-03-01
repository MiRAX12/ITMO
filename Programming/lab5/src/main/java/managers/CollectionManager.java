package managers;

import data.Worker;
import io.XMLReader;

import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionManager {
    private static CollectionManager instance;
    private CollectionManager() {}

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


//    public CollectionManager(Map<String, Worker> collection) {
//        this.collection = collection;
//    }
//

//
//    public void setCollection(Map<String, Worker> collection) {
//        this.collection = collection;
//    }

}
//    private LocalDateTime lastInitTime = LocalDateTime.now().withNano(0);
//    private LocalDateTime lastSaveTime;
//
//    public CollectionManager(LinkedHashMap<String, Worker> coll) {
//        this.lastInitTime = null;
//        this.lastSaveTime = null;
//        this.collection = coll;
//    }
//
//    public LocalDateTime getLastInitTime() {return getLastInitTime();}
//
//    public LinkedHashMap<String, Worker> getCollection() {return collection;
//    }
//
//    public Worker findByID(Long id){ //TODO: проверить тип данных
//        return collection.get(id);
//    }
//
//    public boolean isContain(Worker e){
//        return e==null || findByID(e.getId())!=null;
//    }
//
//    public boolean add(Worker a){
//        if (isContain(a)) return false;
//        collection.put("Worker_" + a.getId(),a);
//        return true;
//    }
//
//    public boolean remove(Long id){
//        if(findByID(id)==null) return false;
//        collection.remove(id);
//        return true;
//    }
//
//    public void init() throws IOException {
//        collection.clear();
//        DumpManager.xmlSerialize(collection);
//        lastInitTime = LocalDateTime.now();
//        }
//
//    public void saveCollection() throws IOException {
//        DumpManager.xmlSerialize(collection);
//        lastSaveTime = LocalDateTime.now();
//    }
//
//    @Override
//    public String toString() {
//        if (collection.isEmpty()) return "Коллекция пуста!";
//
//        StringBuilder info = new StringBuilder();
//        for (var e : collection.values()) {
//            info.append(e+"\n\n");
//        }
//        return info.toString().trim();
//    }
//}

