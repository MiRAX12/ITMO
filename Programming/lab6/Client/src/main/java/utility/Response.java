package utility;

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
    private Map<String, Worker> workers;

    public Response(String message, Map<String, Worker> workers) {
        this.message = message;
        this.workers = workers;
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

    public Map<String, Worker> getWorkers() {
        return workers;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setWorkers(Map<String, Worker> workers) {
        this.workers = workers;
    }

    @Override
    public String toString() {
        return "Response{" +
                "message='" + message + '\'' +
                ", workers=" + workers +
                '}';
    }
}
