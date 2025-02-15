package Commands;

import Data.Worker;
import Managers.CollectionManager;

import java.io.IOException;

import static Managers.DumpManager.xmlSerialize;

public class Save extends Command implements Executable {

    @Override
    public void execute(String[] splitedRequest, CollectionManager collectionManager) throws IOException {
        if (collectionManager.getCollection().isEmpty()){
            System.out.println("Коллекция пуста!");
            return;
        }
            xmlSerialize(collectionManager.getCollection());
        }
    @Override
    public String toString() {
        return "save";
    }
}
