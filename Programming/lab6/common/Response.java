package common;

import common.model.Worker;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * A record class to provide response of executing a command
 *
 * @author Mirax
 * @since 1.0
 */
public record Response(String message, Map<String, Worker> workers) implements Serializable {

    public Response(final String message) {
        this(message, Collections.emptyMap());
    }

    public static Response empty() {
        return new Response("");
    }
}
