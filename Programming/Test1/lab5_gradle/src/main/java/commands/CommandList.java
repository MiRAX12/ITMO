package commands;

import handlers.Handler;

import java.util.List;

public final class CommandList {
    private CommandList(){}
    private static Handler handler;

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

    public static void setRunner(Handler handler) {
        CommandList.handler = handler;
    }
}
