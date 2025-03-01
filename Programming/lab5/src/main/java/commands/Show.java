package commands;

import data.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

public class Show extends Command {

    public Show (){
        super("show", "Вывести содержимое коллекции");
    }

    @Override
    public Response execute(Request request) {
        CollectionManager collection = CollectionManager.getInstance();
        if (collection.getCollection().isEmpty()){
            return new Response("Коллекция пуста!");
        }

        for (Worker worker :collection.getCollection().values()){
            return new Response(collection.getCollection().toString());
        }
        return Response.empty();
    }

    @Override
    public String toString() {
        return getName();
    }
}
