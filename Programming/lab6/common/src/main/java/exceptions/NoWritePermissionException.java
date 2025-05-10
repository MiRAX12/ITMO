package exceptions;

public class NoWritePermissionException extends Exception {
    public NoWritePermissionException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Нет прав на запись файла";
    }
}
