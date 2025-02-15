package Commands;

import Managers.CollectionManager;
import Utility.Engine;

import java.io.IOException;

public interface Executable {
    default void execute(String[] splitedConsoleRead, CollectionManager coll) throws IOException {
        System.out.println("Команда выполнена?");
    }
    default void execute(String[] splitedConsoleRead) throws IOException {
        System.out.println("yes");
    }
}
