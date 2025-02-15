package Commands;

import Managers.CollectionManager;

public class Info extends Command implements Executable{

    @Override
    public void execute(String[] splitedConsoleRead, CollectionManager collectionManager) {

        System.out.println("Информация о коллекции:\n"+
                "Количество элементов : "+ collectionManager.getCollection().size()+"\n"+
                "Тип : " + collectionManager.getCollection().getClass().getSimpleName()+"\n"+
                "Время инициализации : "+ collectionManager.getLastInitTime()+"\n"+
                "Набор доступных ключей : "+ collectionManager.getCollection().keySet()+"\n"






        );
    }

    @Override
    public String toString() {
        return "info";
    }
}
