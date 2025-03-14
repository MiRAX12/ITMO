package exceptions;

public class ExitWritten extends RuntimeException {
    public ExitWritten(String message) {
        super(message);
    }
}
