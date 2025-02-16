package commands;

import managers.CollectionManager;

import java.io.IOException;

public interface Executable {
    default void execute() throws IOException {
        System.out.println("Команда выполнена");
    }
}
