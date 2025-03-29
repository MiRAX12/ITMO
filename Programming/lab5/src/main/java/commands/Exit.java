package commands;

import handlers.Handler;
import utility.Request;
import utility.Response;

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

    /**
     * Executes the command to exit the program.
     * <p>
     * This method terminates the program using {@link Handler#finish()} method. It also returns
     * response indicating the program has been finished.
     * </p>
     *
     * @param request unused for this command
     * @return a {@link Response} indicating the program has been finished
     */
    @Override
    public Response execute(Request request) {
            Handler.finish();
        return new Response("Программа завершена командой exit");
    }

    @Override
    public String toString() {
        return getName();
    }
}
