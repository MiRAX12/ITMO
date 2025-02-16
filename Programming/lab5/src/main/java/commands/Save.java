package commands;

import managers.CollectionManager;

import java.io.IOException;

import static managers.DumpManager.xmlSerialize;

public class Save extends Command implements Executable {
    CollectionManager collectionManager;
    public Save (CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() throws IOException {
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
