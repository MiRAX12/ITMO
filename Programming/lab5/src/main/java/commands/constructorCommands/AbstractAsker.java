package commands.constructorCommands;

import managers.CollectionManager;

import java.util.Scanner;

public abstract class AbstractAsker {
    String input = new Scanner(System.in).nextLine().trim();
    CollectionManager collectionManager;
    public AbstractAsker(CollectionManager collectionManager){
        this.collectionManager = collectionManager;
    }

    public abstract  <T> AbstractAsker parse();
}
