package commands;

import model.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.util.Map;

/**
 * Command to show all workers containing in collection
 *
 * @since 1.0
 */
public class Show extends Command {

    public Show (){
        super("show", "Вывести содержимое коллекции");
    }

    /**
     * Prints all entries that collection has to the output.
     *
     * @param request unused for this command
     * @return a {@link Response} which has string of each worker from collection
     */
    @Override
    public Response execute(Request request) {
        Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();

        if (collection.isEmpty()){
            return new Response("Коллекция пуста!");
        }
        for (Map.Entry<Integer, Worker> worker : collection.entrySet()){
            System.out.println(new Response(worker.toString()).getMessage());
        }
        return Response.empty();
    }

    /**
     * Overridden {code toString} to return name of this command
     *
     * @return name of the command
     */
    @Override
    public String toString() {
        return getName();
    }
}
