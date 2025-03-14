package commands;

import managers.CollectionManager;
import utility.Request;
import utility.Response;

public class FilterStartsWithName extends Command {


    public FilterStartsWithName() {
        super("filter_starts_with_name",
                "вывести элементы, значение поля name которых начинается с заданной подстроки"
        );
    }

    public Response execute(Request request) {
        var result = CollectionManager.getInstance().getCollection().entrySet().stream()
                .filter(entry -> entry.getValue().getName().startsWith(request.arg()))
                .findFirst();
        if (result.isPresent()) return new Response("Найдены элементы: " + result);
        else return new Response("Нет элемента, начинающегося на %s");
    }

    @Override
    public String toString() {
        return getName();
    }
}
