package Managers;

import Commands.*;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {
    private LinkedHashMap<String, Executable> commands = new LinkedHashMap<String, Executable>();
    private CollectionManager collectionManager;

    public CommandManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setUpCommand(Executable command) {
        addCommand(command.toString(), command);
    }

    private void addCommand(String name, Executable command) {
        commands.put(name, command);
    }

    public LinkedHashMap<String, Executable> getCommands() {
        return commands;
    }

    public  void setUserRequest(String[] splitedRequest) throws IOException {
        String request = splitedRequest[0];
        if (getCommands().containsKey(request)) {
            getCommands().get(request).execute(splitedRequest, collectionManager);
        } else {
            System.out.println("Команда не распознана! Попробуйте ознакомиться с перечнем команд, введя '\\help'.");
        }
    }


}
