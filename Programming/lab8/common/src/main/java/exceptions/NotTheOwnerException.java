package exceptions;

public class NotTheOwnerException extends RuntimeException {
    public NotTheOwnerException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Не найдено таких Workers, владельцем каких вы являетесь";
    }
}
