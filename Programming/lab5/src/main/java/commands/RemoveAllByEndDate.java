package commands;

import data.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.time.ZonedDateTime;
import java.util.Map;

public class RemoveAllByEndDate extends Command {

    public RemoveAllByEndDate() {
        super("remove_all_by_end_date",
                "удалить из коллекции все элементы," +
                        " значение поля endDate которого эквивалентно заданному");
    }

    public Response execute(Request request) {
        Map<Integer, Worker> collection = CollectionManager.getInstance().getCollection();
        int collectionSize = collection.size();
        collection.entrySet().removeIf(entry -> entry.getValue()
                .getEndDate().isEqual(ZonedDateTime.parse(request.arg())));
        int difference = collectionSize - collection.size();
        return new Response("Удалено %d элементов".formatted(difference));
    }

    @Override
    public String toString() {
        return getName();
    }
}