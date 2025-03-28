package utility;

import model.Worker;
import utility.wrappers.WorkerKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demapper {
    List<WorkerKeys> workerKeys = new ArrayList<WorkerKeys>();

    public Demapper(){}

    public void Demap(Map<Integer, Worker> map){
        for(Map.Entry<Integer, Worker> entry : map.entrySet()){
            workerKeys.add(new WorkerKeys(entry.getKey().toString(), entry.getValue()));
        }
    }

    public List<WorkerKeys> getWorkerKeys() {
        return workerKeys;
    }
}
