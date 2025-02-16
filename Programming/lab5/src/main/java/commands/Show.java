package commands;

import data.Worker;
import managers.CollectionManager;

public class Show extends Command implements Executable{
    CollectionManager collectionManager;

    public Show (CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {

        if (collectionManager.getCollection().isEmpty()){
            System.out.println("Коллекция пуста!");
            return;
        }

        for (Worker worker :collectionManager.getCollection().values()){
            System.out.println(worker.toString());
        }
    }

    @Override
    public String toString() {
        return "show";
    }
}
