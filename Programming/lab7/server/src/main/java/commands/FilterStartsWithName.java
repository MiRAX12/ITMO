package commands;

import model.Worker;
import managers.CollectionManager;
import Network.Request;
import Network.Response;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Command to filter all elements and return all elements starts with written name.
 * <p>
 * The {@code FilterStartsWithName} checks every element {@link Worker} of
 * {@link CollectionManager} if it starts with name, collecting them and returning.
 * </p>
 *
 * @see CollectionManager
 * @see Worker
 * @since 1.0
 */
public class FilterStartsWithName extends Command {


    public FilterStartsWithName() {
        super("filter_starts_with_name",
                "вывести элементы, значение поля name которых начинается с заданной подстроки"
        );
    }

    /**
     * Executes the command to remove all elements which has field greater than the specified element.
     * <p>
     * If there's no such elements, an appropriate message is returned. Otherwise, found elements
     * are returned
     * </p>
     *
     * @param request the request containing letters to be a name should start with
     * @return a {@link Response} indicates if elements was found. If yes, enumerating them.
     */
    public synchronized Response execute(Request request) {
        Map<Long, Worker> filteredWorkers = CollectionManager.getInstance().getCollection().entrySet().stream()
                .filter(entry -> entry.getValue().getName().startsWith(request.getArg()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (filteredWorkers.isEmpty()) {
            return new Response("Нет элемента, начинающегося на %s".formatted(request.getArg()));
        }

        StringBuilder result = new StringBuilder("Найдены Workers:\n");
        filteredWorkers.forEach((key, worker) ->
                result.append("Key: ").append(key)
                        .append("Worker: ").append(worker)
                        .append("\n")
        );
        return new Response(result.toString());
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
