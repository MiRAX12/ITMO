package utility;

import model.Worker;
import utility.wrappers.WorkerKey;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Remapper {
    Map<Integer, Worker> map = new LinkedHashMap<>();

    public Remapper() {}

    public void Remap(List<WorkerKey> workerKeys){
        for(WorkerKey wk : workerKeys){
            map.put(Integer.parseInt(wk.getKey()), wk.getWorker());
        }
    }

    public Map<Integer, Worker> getMap() {
        return map;
    }
}
