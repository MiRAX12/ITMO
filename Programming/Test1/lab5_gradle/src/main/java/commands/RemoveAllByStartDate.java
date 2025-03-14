package commands;

import model.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Map;

public class RemoveAllByStartDate extends Command {

    public RemoveAllByStartDate() {
        super("remove_all_by_start_date",
                "удалить из коллекции все элементы," +
                        " значение поля startDate которого эквивалентно заданному");
    }

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

    @Override
    public String toString() {
        return getName();
    }
}
