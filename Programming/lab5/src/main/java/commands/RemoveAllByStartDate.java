package commands;

import model.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;


/**
 * The {@code RemoveGreater} command removes all elements in the collection managed by
 * {@link CollectionManager} which has the same start date as given
 *
 * @see CollectionManager
 * @since 1.0
 */
public class RemoveAllByStartDate extends Command {

    public RemoveAllByStartDate() {
        super("remove_all_by_start_date",
                "удалить из коллекции все элементы," +
                        " значение поля startDate которого эквивалентно заданному");
    }

    /**
     * Executes the command to remove all elements that has the same start date as given
     * in argument
     * <p>
     * If the collection is empty, an appropriate response is returned. Otherwise,
     * elements which has the same start date as given in argument.
     * </p>
     *
     * @param request the request containing the {@link Worker} end date.
     * @return a {@link Response} indicating if the elements was successfully removed or
     * message that date need to be written
     */
    public Response execute(Request request) {
        Response response;

        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        try {
            Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();
            int collectionSize = collection.size();

            collection.entrySet().removeIf(entry -> entry.getValue().
                    getStartDate().isEqual(LocalDateTime.parse(request.arg())));
            int difference = collectionSize - collection.size();
            response = new Response("Удалено %d элементов".formatted(difference));
        }catch (DateTimeParseException e){
            response = new Response("Чтобы удалить Worker, укажите через пробел" +
                    " дату и время начала в формате 'yyyy-MM-dd HH:mm:ss'" +
                    " (например, '2023-10-05 14:30:00')");
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
