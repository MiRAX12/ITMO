package commands;

import chat.Runner;

import java.util.List;
import java.util.logging.Filter;

public final class CommandList {
    private CommandList(){}
    private static Runner runner;

    public static final List<Command> commandList = List.of(
            new Clear(),
            new Exit(),
            new Info(),
            new Save(),
            new Show(),
            new Help(),
            new Insert(),
            new Exit(),
            new ExecuteScript(),
            new RemoveGreater(),
            new RemoveGreaterKey(),
            new RemoveLowerKey(),
            new RemoveAllByEndDate(),
            new RemoveAllByStartDate(),
            new FilterStartsWithName()
    );

    public static void setRunner(Runner runner) {
        CommandList.runner = runner;
    }
}
