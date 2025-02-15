package Managers;

import Commands.Command;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {
    private static LinkedHashMap<String, Command> commands = new LinkedHashMap<String, Command>();

    public static void setUpCommand(Command command) {
        addCommand(command.toString(), command);

    }


    private static final void addCommand(String name, Command command) {
        commands.put(name, command);
    }

    public static final LinkedHashMap<String, Command> getCommands() {
        return commands;
    }

    public static void setUserRequest(String[] splitedRequest) {
        String request = splitedRequest[0];
        if (CommandManager.getCommands().containsKey(request)) {
            CommandManager.getCommands().get(request).apply("1");
        } else {
            System.out.println("Команда не распознана! Попробуйте ознакомиться с перечнем команд, введя '\\help'.");
        }
    }


}
