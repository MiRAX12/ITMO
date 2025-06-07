package network;

import model.Worker;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * A record class to provide response of executing a command
 *
 * @author Mirax
 * @since 1.0
 */
public class Response implements Serializable {
    private String message;
    private Map<Long, Worker> workers;
    private Map<Long, String> workerCreatorMap;
    private boolean userAuthentication;

    public Response(String message, Map<Long, Worker> workers) {
        this.message = message;
        this.workers = workers;
    }

    public Response(Map<Long, String> workerCreatorMap){
        this.workerCreatorMap = workerCreatorMap;
    }

    public Response(String message, boolean userAuthentication) {
        this.message = message;
        this.userAuthentication = userAuthentication;
    }

    public Response(String message) {
        this(message, Collections.emptyMap());
    }

    public static Response empty() {
        return new Response("");
    }

    public String getMessage() {
        return message;
    }

    public Map<Long, Worker> getWorkers() {
        return workers;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWorkers(Map<Long, Worker> workers) {
        this.workers = workers;
    }

    public boolean isUserAuthenticated() {
        return userAuthentication;
    }

    public Map<Long, String> getWorkerCreatorMap() {
        return workerCreatorMap;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", workers=" + workers +
                '}';
    }
}
