package Commands;

import Managers.CollectionManager;

public class Clear extends Command implements Executable{


    @Override
    public void execute(String[] splitedConsoleRead, CollectionManager collectionManager) {
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

