package exceptions;

public class ExitWritten extends RuntimeException {
    public ExitWritten() {
        super();
    }

    @Override
    public String getMessage() {
        return "Введена команда exit. Выход из консоли...";
    }
}
