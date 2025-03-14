package model;

import java.util.Map;

public class IdGenerator {
    private static Long workerID = 0L;

    private static void setTicketIdCounter(Long idCounter) {
        workerID = idCounter;
    }

    public static void resetID(Map<Integer, Worker> workerMap) {
        if (workerMap.isEmpty()) {
            setTicketIdCounter(0L);
            return;
        }
        for (Worker worker : workerMap.values()) {
            if (worker.getId() > workerID) {
                setTicketIdCounter(worker.getId());
            }
        }
    }

    public static Long updateID() {
        workerID++;
        return workerID;
    }
}
