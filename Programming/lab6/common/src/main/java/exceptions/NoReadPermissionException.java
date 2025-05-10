package exceptions;

public class NoReadPermissionException extends Exception {
    public NoReadPermissionException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Нет прав на чтение файла";
    }
}
