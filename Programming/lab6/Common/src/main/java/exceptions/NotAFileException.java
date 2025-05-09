package exceptions;

public class NotAFileException extends RuntimeException {
    public NotAFileException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Объект по ссылке не является файлом";
    }
}
