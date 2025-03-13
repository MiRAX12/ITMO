package commands;

import data.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.util.Map;

public class Show extends Command {

    public Show (){
        super("show", "Вывести содержимое коллекции");
    }

    @Override
    public Response execute(Request request) {
        Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();

        if (collection.isEmpty()){
            return new Response("Коллекция пуста!");
        }
        for (Map.Entry<Integer, Worker> worker : collection.entrySet()){
            System.out.println(new Response(worker.toString()).message());
        }
        return Response.empty();
    }

    @Override
    public String toString() {
        return getName();
    }
}
