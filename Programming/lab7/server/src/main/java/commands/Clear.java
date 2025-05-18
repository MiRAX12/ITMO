package commands;

import managers.CollectionManager;
import Network.Request;
import Network.Response;

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
public class Clear extends Command{
    public Clear(){
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
    public Response execute(Request request) {
        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        CollectionManager.getInstance().getCollection().clear();
            return new Response("Коллекция успешно очищена!");
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

