package Managers;

import Data.Worker;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;

public class CollectionManager {
//    private static LinkedHashMap<Long, Worker> collection = new LinkedHashMap<>();
    private LinkedHashMap<String, Worker> collection;
    private LocalDateTime lastInitTime = LocalDateTime.now().withNano(0);
    private LocalDateTime lastSaveTime;
    private DumpManager dumpManager;

    public CollectionManager() {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
        this.collection = collection;
    }

    public  void setCollection(LinkedHashMap<String, Worker> coll) {
        this.collection = coll;    }

    public LocalDateTime getLastInitTime() {return getLastInitTime();}

    public LinkedHashMap<String, Worker> getCollection() {return collection;
    }

    public Worker findByID(Long id){ //TODO: проверить тип данных
        return collection.get(id);
    }

    public boolean isContain(Worker e){
        return e==null || findByID(e.getId())!=null;
    }

    public boolean add(Worker a){
        if (isContain(a)) return false;
        collection.put("Worker_" + a.getId(),a);
        return true;
    }

    public boolean remove(Long id){
        if(findByID(id)==null) return false;
        collection.remove(id);
        return true;
    }

    public void init() throws IOException {
        collection.clear();
        DumpManager.xmlSerialize(collection);
        lastInitTime = LocalDateTime.now();
        }

    public void saveCollection() throws IOException {
        DumpManager.xmlSerialize(collection);
        lastSaveTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var e : collection.values()) {
            info.append(e+"\n\n");
        }
        return info.toString().trim();
    }
}

