package commands;

import managers.CollectionManager;

public class Info extends Command implements Executable{
    CollectionManager collectionManager;
    public Info (CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    @Override
    public void execute() {

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
