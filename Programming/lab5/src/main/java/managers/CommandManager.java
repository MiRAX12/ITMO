package managers;

import commands.*;
import constructors.parsers.AbstractParser;
import constructors.parsers.Parsable;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {
    private LinkedHashMap<String, Executable> commands = new LinkedHashMap<String, Executable>();
    private Map<String, AbstractParser<?>> parsers = new HashMap<>();

    public void setUpCommand(Executable command) {
        addCommand(command.toString(), command);
    }

    public void setUpParser(AbstractParser<?> command) {
        addParser(command.toString(), command);
    }

    private void addCommand(String name, Executable command) {
        commands.put(name, command);
    }

    public void addParser(String name, AbstractParser<?> parser) {
        parsers.put(name, parser);
    }

    public LinkedHashMap<String, Executable> getCommands() {
        return commands;
    }

    public Map<String, AbstractParser<?>> getParsers() {
        return parsers;
    }

    public  void setUserRequest(String[] splitedRequest) throws IOException {
        String request = splitedRequest[0];
        if (getCommands().containsKey(request)) {
            getCommands().get(request).execute();
        } else {
            System.out.println("Команда не распознана! Попробуйте ознакомиться с перечнем команд, введя 'help'.");
        }
    }


}
