package handlers;

import commands.*;
import utility.Request;
import utility.Response;

public class Router {

    private static final Router INSTANCE = new Router();

    private Router(){};

    public static Router getInstance(){
        return INSTANCE;
    }

    public Response route(Request request) {
        if (request == null || request.command().isBlank()) return Response.empty();
        return CommandList.commandList.stream()
                .filter(command -> command.getName().equals(request.command()))
                .findFirst()
                .map(command -> command.execute(request))
                .orElse(new Response("Команда не распознана, введите 'help', чтобы вывести список команд"));
    }
}
