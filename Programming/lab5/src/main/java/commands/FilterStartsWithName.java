package commands;

import model.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

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
    public Response execute(Request request) {
        Map<Integer, Worker> result = CollectionManager.getInstance().getCollection().entrySet().stream()
                .filter(entry -> entry.getValue().getName().startsWith(request.arg()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (result.isEmpty()) {
            return new Response("Нет элемента, начинающегося на %s".formatted(request.arg()));
        }
        System.out.println("Найдены элементы: ");
        for (Map.Entry<Integer, Worker> worker : result.entrySet()) {
            System.out.println(new Response(worker.toString()).message());
        }
        return Response.empty();
    }

    @Override
    public String toString() {
        return getName();
    }
}
