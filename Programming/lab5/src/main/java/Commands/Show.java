package Commands;

import Data.Worker;
import Managers.CollectionManager;

public class Show extends Command implements Executable{

    @Override
    public void execute(String[] splitedConsoleRead, CollectionManager collectionManager) {

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
