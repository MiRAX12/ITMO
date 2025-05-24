package commands;

import model.Worker;
import managers.CollectionManager;
import Network.Request;
import Network.Response;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Command to show all workers containing in collection
 *
 * @since 1.0
 */
public class Show extends Command {

    public Show() {
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
        Map<Long, Worker> collection = CollectionManager.getInstance().getCollection();

        if (collection.isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        String workers = collection.entrySet().stream()
                .map(worker -> "Key: " + worker.getKey() +
                        " Worker: " + worker.getValue())
                .collect(Collectors.joining("\n"));
        return new Response(workers);
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
