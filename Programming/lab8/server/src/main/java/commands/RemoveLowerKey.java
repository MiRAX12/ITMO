package commands;

import database.Database;
import model.Worker;
import managers.CollectionManager;
import network.Request;
import network.Response;

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
    public synchronized Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Long, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            collection.entrySet().removeIf(entry -> {
                boolean remove = entry.getKey() < Integer.parseInt(request.getArg());
                if (remove) return Database.deleteById(entry.getKey(), request.getUser());
                return false;
            });
            int difference = collectionSize - collection.size();
            if (difference == 0) response = new Response("Не найдено таких записей, владельцем каких вы являетесь");
            else response = new Response("Удалено %d элементов".formatted(difference));
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

