package exceptions;

public class IncorrectStringValueException extends Exception {

    public IncorrectStringValueException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Введены некорректные символы";
    }
}
