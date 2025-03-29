package commands;

import handlers.Handler;

import java.util.List;

/**
 * A list of commands available in the program
 * <p>
 * The {@code CommandList} class provides a static, immutable list
 * of all {@link Command} instances used by the program. This class cannot be instantiated.
 * </p>
 *
 * @see Command
 * @since 1.0
 */
public final class CommandList {

    /**
     * A static, immutable list of commands
     * <p>
     * This list includes all commands available in the program.
     * </p>
     */
    public static final List<Command> commandList = List.of(
            new Clear(),
            new Exit(),
            new Info(),
            new Save(),
            new Show(),
            new Help(),
            new Insert(),
            new ExecuteScript(),
            new RemoveGreater(),
            new RemoveGreaterKey(),
            new RemoveLowerKey(),
            new RemoveAllByEndDate(),
            new RemoveAllByStartDate(),
            new FilterStartsWithName()
    );

    /**
     * Private constructor to prevent instantiation.
     */
    private CommandList(){}
}
