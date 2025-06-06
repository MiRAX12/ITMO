package commands;

import network.Request;
import network.Response;

/**
 * Abstract base class for commands.
 * <p>
 * A command is a specific operation that can be executed with a {@link Request}.
 * Each command has a name and a description help message.
 * </p>
 *
 * @see Request
 * @see Response
 * @since 1.0
 */
public abstract class Command {
    private final String name;
    private final String description;

    /**
     * Constructs a command with a name and description.
     *
     * @param name the name of the command
     * @param description description for help description command
     */
    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Executes the command with taken {@link Request}.
     * <p>
     * Subclasses must implement this method perform request-response behavior.
     * </p>
     *
     * @param request the request containing input data for the command
     * @return a {@link Response} representing the result of the execution
     */
    public abstract Response execute(Request request);

    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the command.
     *
     * @return the description of the command
     */
    public String getDescription() {
        return description;
    }

}

