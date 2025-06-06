package exceptions;

public class EmptyValueException extends Throwable {
    public EmptyValueException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Ввод не может быть пустым";
    }
}
