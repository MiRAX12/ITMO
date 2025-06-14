package commands;


import database.Database;
import network.Request;
import network.Response;

public class GetWorkerCreatorMap extends Command {

    @Override
    public Response execute(Request request) {
        return new Response(Database.getWorkerCreatorMap());    }

    public String getName() {
        return "get_worker_creator_map";
    }

    public GetWorkerCreatorMap() {
        super("get_worker_creator_map",
                "получить соответствия рабочих и создателя записи о них");
    }
}
