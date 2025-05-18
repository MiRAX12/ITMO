package commands;

import Network.Request;
import Network.Response;

/**
 * Command to exit the program.
 * <p>
 * The {@code Exit} command terminates the program immediately.
 * </p>
 *
 * @since 1.0
 */
public class Exit extends Command {
    public Exit() {super("exit", "завершить программу без сохранения");}

    @Override
    public Response execute(Request request) {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}