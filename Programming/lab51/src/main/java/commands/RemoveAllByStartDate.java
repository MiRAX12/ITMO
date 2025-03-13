package commands;

import data.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.time.LocalDateTime;
import java.util.Map;

public class RemoveAllByStartDate extends Command {

    public RemoveAllByStartDate() {
        super("remove_all_by_start_date",
                "удалить из коллекции все элементы," +
                        " значение поля startDate которого эквивалентно заданному");
    }

    public Response execute(Request request) {
        Map <Integer, Worker> collection = CollectionManager.getInstance().getCollection();
        int collectionSize = collection.size();
        collection.entrySet().removeIf(entry -> entry.getValue().
                        getStartDate().isEqual(LocalDateTime.parse(request.arg())));
        int difference = collectionSize - collection.size();
        return new Response("Удалено %d элементов".formatted(difference));
    }

    @Override
    public String toString() {
        return getName();
    }
}
