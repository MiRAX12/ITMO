package utility;

import model.Worker;

import java.io.Serializable;

/**
 * A record class create a request from input
 *
 * @author Mirax
 * @since 1.0
 */
public record Request(String command, String arg, Worker worker) implements Serializable {

    public Request(String command, String arg) {
        this(command, arg, null);
    }

    public Request(String command) {
        this(command, null);
    }

}
