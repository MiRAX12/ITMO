package common;

import java.io.Serializable;

/**
 * A record class create a request from input
 *
 * @author Mirax
 * @since 1.0
 */
public record Request(String command, String arg, Serializable object) implements Serializable {

    public Request(final String command, final String arg) {
        this(command, arg, null);
    }

}
