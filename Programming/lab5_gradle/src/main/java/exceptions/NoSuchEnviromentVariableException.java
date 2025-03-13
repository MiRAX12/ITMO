package exceptions;

public class NoSuchEnviromentVariableException extends RuntimeException {
    public NoSuchEnviromentVariableException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Не найдена переменная окружения, ведущая к файлу!" +
                "Пожалуйста, установите переменную окружения и запустите программу снова";
    }
}
