package exceptions;

public class NoSuchEnvironmentVariableException extends RuntimeException {
    public NoSuchEnvironmentVariableException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Не найдена переменная окружения, ведущая к файлу! " +
                "Чтобы прочитать файл, установите переменную окружения xml_file, " +
                "содержащую путь к файлу и запустите программу снова";
    }
}
