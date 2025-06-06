package commands;

import database.Database;
import model.Worker;
import managers.CollectionManager;
import network.Request;
import network.Response;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

/**
 * The {@code RemoveGreater} command removes all elements in the collection managed by
 * {@link CollectionManager} which has the same end date as given
 *
 * @see CollectionManager
 * @since 1.0
 */
public class RemoveAllByEndDate extends Command {

    public RemoveAllByEndDate() {
        super("remove_all_by_end_date",
                "удалить из коллекции все элементы," +
                        " значение поля endDate которого эквивалентно заданному");
    }

    /**
     * Executes the command to remove all elements that has the same end date as given
     * in argument
     * <p>
     * If the collection is empty, an appropriate response is returned. Otherwise,
     * elements which has the same end date as given in argument.
     * </p>
     *
     * @param request the request containing the {@link Worker} end date.
     * @return a {@link Response} indicating if the elements was successfully removed or
     * message that date need to be written
     */
    public synchronized Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Long, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
            collection.entrySet().removeIf(entry -> {
                boolean remove = entry.getValue().getEndDate().isEqual(ZonedDateTime.parse(request.getArg(), formatter));
                if (remove) return Database.deleteById(entry.getKey(), request.getUser());
                return false;
            });
            int difference = collectionSize - collection.size();
            if (difference == 0) response = new Response("Не найдено таких записей, владельцем каких вы являетесь");
            else response = new Response("Удалено %d элементов".formatted(difference));
        } catch (DateTimeParseException e) {
            response = new Response("Чтобы удалить Worker, укажите через пробел" +
                    " дату и время конца в формате 'yyyy-MM-dd HH:mm:ss z'" +
                    " (например, '2023-10-05 14:30:00 UTC')");
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