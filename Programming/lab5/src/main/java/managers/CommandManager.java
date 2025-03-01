package managers;

import commands.*;
import constructors.parsers.AbstractParser;
import utility.Request;
import utility.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandManager {
    private final List<Command> commands = CommandList.commandList;
    private static CommandManager instance;
    private CommandManager(){};

    public static CommandManager getInstance(){
        return instance == null ? instance = new CommandManager() : instance;
    }

    public Response executeCommand(Request request) {
        if (request == null || request.command() == null || request.command().isBlank()) return Response.empty();
        return CommandList.commandList.stream()
                .filter(command -> command.getName().equals(request.command()))
                .findFirst()
                .map(command -> command.execute(request))
                .orElse(new Response("Команда не распознана, введите 'help', чтобы вывести список команд"));
    }

}
