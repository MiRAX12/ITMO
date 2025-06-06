package utility;

import model.Worker;
import utility.wrappers.WorkerKeys;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A class to perform reconstruction of map to list
 *
 * @author Mirax
 * @since 1.0
 */
public class Demapper {
    List<WorkerKeys> workerKeys = new ArrayList<>();

    public Demapper(){}

    /**
     * Takes a map from argument and adds pairs key-worker as a {@link WorkerKeys} element
     *
     * @param map - a map to be reconstructed to a list
     */
    public void Demap(Map<Integer, Worker> map){
        for(Map.Entry<Integer, Worker> entry : map.entrySet()){
            workerKeys.add(new WorkerKeys(entry.getKey().toString(), entry.getValue()));
        }
    }

    /**
     * Method to get an instance of {@link WorkerKeys}
     *
     * @return {@link WorkerKeys} instance
     */
    public List<WorkerKeys> getWorkerKeys() {
        return workerKeys;
    }
}
