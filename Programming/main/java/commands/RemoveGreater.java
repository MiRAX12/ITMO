package commands;

import managers.CollectionManager;
import utility.Request;
import utility.Response;

public class RemoveGreater extends Command {
    float salaryThreshold = 0.5f; //TODO: test

    public RemoveGreater() {
        super("remove_greater",
                "Удаляет всех Worker с зарплатой выше заданной");
    }

    @Override
    public Response execute(Request request) {
        if (CollectionManager.getInstance().getCollection().isEmpty()) {
            return new Response("Коллекция пуста!");
        }
        CollectionManager.getInstance().getCollection().entrySet().
                removeIf(entry -> entry.getValue().getSalary() > salaryThreshold);
        return new Response("Workers с зарплатой выше указанной удалены");
    }

    @Override
    public String toString() {
        return getName();
    }
}
