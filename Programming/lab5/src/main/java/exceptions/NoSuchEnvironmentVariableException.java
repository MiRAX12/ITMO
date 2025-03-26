package exceptions;

public class NoSuchEnvironmentVariableException extends RuntimeException {
    String variableName;
    public NoSuchEnvironmentVariableException(String variableName) {
        super();
        this.variableName=variableName;
    }

    @Override
    public String getMessage() {
        return "Не найдена переменная окружения, ведущая к файлу! " +
                "Чтобы прочитать файл, установите переменную окружения " + variableName +
                " содержащую путь к файлу и запустите программу снова";
    }
}
