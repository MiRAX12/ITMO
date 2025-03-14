package utility;

import data.Worker;
import io.wrappers.WorkerKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Demapper {
    List<WorkerKey> workerKeys = new ArrayList<WorkerKey>();

    public Demapper(){}

    public void Demap(Map<Integer, Worker> map){
        for(Map.Entry<Integer, Worker> entry : map.entrySet()){
            workerKeys.add(new WorkerKey(entry.getKey().toString(), entry.getValue()));
        }
    }

    public List<WorkerKey> getWorkerKeys() {
        return workerKeys;
    }
}
