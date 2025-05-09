package exceptions;

public class NoScriptPathException extends RuntimeException {
    public NoScriptPathException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Укажите путь к файлу скрипта";
    }
}