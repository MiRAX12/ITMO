package exceptions;

import java.util.Objects;

public class NoSuchEnvironmentVariableException extends RuntimeException {
    String variableName;
    public NoSuchEnvironmentVariableException(String variableName) {
        super();
        this.variableName=variableName;
    }

    @Override
    public String getMessage() {
        if(Objects.equals(variableName, "id_collector")) {
            return "Не найдена переменная окружения, ведущая к файлу ID! " +
                    "Установите переменную окружения " + variableName +
                    " содержащую путь к файлу и запустите программу снова";
        }
        return "Не найдена переменная окружения, ведущая к XML файлу! " +
                "Установите переменную окружения " + variableName +
                " содержащую путь к файлу и запустите программу снова";
    }
}
