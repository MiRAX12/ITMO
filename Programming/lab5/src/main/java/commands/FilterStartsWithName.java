package commands;

import model.Worker;
import managers.CollectionManager;
import utility.Request;
import utility.Response;

import java.util.Map;
import java.util.stream.Collectors;

public class FilterStartsWithName extends Command {


    public FilterStartsWithName() {
        super("filter_starts_with_name",
                "вывести элементы, значение поля name которых начинается с заданной подстроки"
        );
    }

    public Response execute(Request request) {
        Map<Integer, Worker> result = CollectionManager.getInstance().getCollection().entrySet().stream()
                .filter(entry -> entry.getValue().getName().startsWith(request.arg()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        if (result.isEmpty()) {
            return new Response("Нет элемента, начинающегося на %s"); //TODO
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
