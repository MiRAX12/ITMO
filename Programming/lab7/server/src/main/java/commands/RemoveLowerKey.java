package commands;

import model.Worker;
import managers.CollectionManager;
import Network.Request;
import Network.Response;

import java.util.Map;

/**
 * Command to remove all elements from the collection which has key lower that the specified one.
 * <p>
 * The {@code RemoveGreater} command removes all elements in the collection managed by
 * {@link CollectionManager} which has key lower than the specified key are removed.
 * </p>
 *
 * @see CollectionManager
 * @since 1.0
 */
public class RemoveLowerKey extends Command {

    public RemoveLowerKey() {
        super("remove_lower_key",
                "удалить из коллекции все элементы, ключ которых меньше, чем заданный");
    }

    /**
     * Executes the command to remove all elements which has key lower than the specified element.
     * <p>
     * If the collection is empty, an appropriate response is returned. Otherwise,
     * elements which has key lower than the specified element are removed
     * from the collection.
     * </p>
     *
     * @param request the request containing the {@link Worker} key
     * @return a {@link Response} indicating whether the elements were successfully removed or if
     * key need to be written
     */
    public Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            collection.entrySet().removeIf(entry -> entry.getKey()
                    < Integer.parseInt(request.getArg()));
            int difference = collectionSize - collection.size();
            response = new Response("Удалено %d элементов".formatted(difference));
        } catch (Exception e) {
            response = new Response("Чтобы удалить Worker, укажите через пробел ключ в виде числа");
        }
        return response;
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

