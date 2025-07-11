package commands;

import network.Request;
import network.Response;

import java.util.stream.Collectors;

/**
 * Command to print a description of using of all commands
 *
 * @since 1.0
 */
public class Help extends Command{

    public Help(){
        super("help", "Вывести справку о доступных командах");
    }

    /**
     * Retrieves information about all available commands.
     * <p>
     * Return a response containing a list of all commands and descriptions.
     * </p>
     *
     * @param request unused for this command
     * @return a {@link Response} of using guide.
     */
    public Response execute(Request request) {
        return new Response(CommandList.commandList.stream()
                .map(command -> command.getName() + " - " + command.getDescription())
                .collect(Collectors.joining("\n")));
    }

    /**
     * Overridden {code toString} to return name of this command
     *
     * @return name of the command
     */
    @Override
    public String toString() {
        return getName();
    }
}
