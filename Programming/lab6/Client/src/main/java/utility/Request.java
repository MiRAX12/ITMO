package utility;

import model.Worker;

import java.io.Serializable;

/**
 * A record class create a request from input
 *
 * @author Mirax
 * @since 1.0
 */
public record Request(String command, String arg, Worker object) implements Serializable {

    public Request(final String command, final String arg, final Worker object) {
        this.command = command;
        this.arg = arg;
        this.object = object;
    }

    public Request(final String command, final String arg) {
        this(command, arg, null);
    }

    public Request(final String command) {
        this(command, null, null);
    }

}
