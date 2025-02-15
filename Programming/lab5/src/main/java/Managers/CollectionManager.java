package Managers;

import Data.Worker;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;

public class CollectionManager { //TODO Такая херня....
    private LinkedHashMap<Long, Worker> collections = new LinkedHashMap<>();
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;
    private DumpManager dumpManager;

    public CollectionManager(DumpManager dumpManager) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpManager = dumpManager;
    }

    public LinkedHashMap<Long, Worker> getCollections() {
        return collections;
    }

    public Worker findByID(Long id){ //TODO: проверить тип данных
        return collections.get(id);
    }

    public boolean isContain(Worker e){
        return e==null || findByID(e.getId())!=null;
    }

    public boolean add(Worker a){
        if (isContain(a)) return false;
        collections.put(a.getId(),a);
        return true;
    }

    public boolean remove(Long id){
        if(findByID(id)==null) return false;
        collections.remove(id);
        return true;
    }

    public void init() throws IOException {
        collections.clear();
        DumpManager.xmlSerialize(collections);
        lastInitTime = LocalDateTime.now();
        }

    public void saveCollection() throws IOException {
        DumpManager.xmlSerialize(collections);
        lastSaveTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collections.isEmpty()) return "Коллекция пуста!";

        StringBuilder info = new StringBuilder();
        for (var e : collections.values()) {
            info.append(e+"\n\n");
        }
        return info.toString().trim();
    }
}

