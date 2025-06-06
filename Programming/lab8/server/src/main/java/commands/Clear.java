package commands;

import database.Database;
import managers.CollectionManager;
import model.Worker;
import network.Request;
import network.Response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Command to clear the entire collection.
 * <p>
 * {@code Clear} command removes all elements from the collection managed by
 * {@link CollectionManager}.
 * </p>
 *
 * @see CollectionManager
 * @since 1.0
 */
public class Clear extends Command {
    public Clear() {
        super("clear", "Очищает коллекцию");
    }

    /**
     * Executes the command to clear the collection.
     * <p>
     * If the collection is empty, a response indicating the empty state is returned.
     * Otherwise, the collection is cleared, and a success message is returned.
     * </p>
     *
     * @param request the request to execute (unused for this command)
     * @return a {@link Response} indicating whether the collection was cleared or was already empty
     */
    @Override
    public synchronized Response execute(Request request) {
        Response response;
        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        Map<Long, Worker> collection = CollectionManager.getInstance().getCollection();
        int collectionSize = collection.size();

        collection.entrySet().removeIf(entry -> {
            boolean deletedFromDb = Database.deleteById(entry.getKey(), request.getUser());
            return deletedFromDb;
        });
        int difference = collectionSize - collection.size();
        if (difference == 0) response = new Response("Не найдено таких записей, владельцем каких вы являетесь");
        else response = new Response("Удалено %d элементов".formatted(difference));
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

