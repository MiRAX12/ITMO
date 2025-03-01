package utility;

import data.Worker;

import java.util.Collections;
import java.util.Map;

public record Response(String message, Map<String, Worker> workers, String script) {

    public Response(String message, Map<String, Worker> workers) {
        this(message, workers, null);
    }

    public Response(String message, String script) {
        this(message, Collections.emptyMap(), script);
    }

    public Response(final String message) {
        this(message, Collections.emptyMap(), null);
    }

    public static Response empty() {
        return new Response(null);
    }
}
