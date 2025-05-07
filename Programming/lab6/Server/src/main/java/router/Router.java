package router;

import commands.*;
import utility.Request;
import utility.Response;

/**
 * Routes requests to appropriate command for execution.
 * <p>
 * Processes incoming requests by matching them to the corresponding command and executing it.
 * It also provides functionality
 * </p>
 *
 * @author Mirax
 * @since 1.0
 */
public class Router {

    /**
     * The single instance of the {@code Router} class.
     * <p>
     * This field inline initialized.
     * </p>
     */
    private static final Router INSTANCE = new Router();

    /**
     * Private constructor to prevent instantiation.
     */
    private Router(){};

    /**
     * Returns the single instance of the {@code Router} class.
     *
     * @return the single instance of the {@code Router} class
     */
    public static Router getInstance(){
        return INSTANCE;
    }

    /**
     * Routes a {@link Request} to the appropriate {@link Command}.
     * <p>
     * If the request is empty, empty response is returned.
     * If the command in the request is invalid or not found, an error response is returned.
     * </p>
     *
     * @param request the request to route
     * @return the {@link Response} from the executed command
     */
    public Response route(Request request) {
        if (request == null || request.getCommand().isBlank()) return Response.empty();
        return CommandList.commandList.stream()
                .filter(command -> command.getName().equals(request.getCommand()))
                .findFirst()
                .map(command -> command.execute(request))
                .orElse(new Response("Команда не распознана, введите 'help', чтобы вывести список команд"));
    }
}
