package commands;

import managers.CollectionManager;

public class Clear extends Command implements Executable{
    CollectionManager collectionManager;
    public Clear(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {
        collectionManager.getCollection().clear();
        if (collectionManager.getCollection().isEmpty()){
            System.out.println("Коллекция успешно очищена!");
        }

    }

    @Override
    public String toString() {
        return "clear";
    }
}

