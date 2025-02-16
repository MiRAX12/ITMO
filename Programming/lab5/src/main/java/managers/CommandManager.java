package managers;

import commands.*;

import java.io.IOException;
import java.util.LinkedHashMap;

public class CommandManager {
    private LinkedHashMap<String, Executable> commands = new LinkedHashMap<String, Executable>();
    private CollectionManager collectionManager;
    String[] splitedRequest;

    public CommandManager(String[] splitedRequest, CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.splitedRequest = splitedRequest;
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

    public  void setUserRequest() throws IOException {
        String request = splitedRequest[0];
        if (getCommands().containsKey(request)) {
            getCommands().get(request).execute();
        } else {
            System.out.println("Команда не распознана! Попробуйте ознакомиться с перечнем команд, введя '\\help'.");
        }
    }


}
