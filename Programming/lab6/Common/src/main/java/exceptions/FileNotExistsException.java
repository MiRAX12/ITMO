package exceptions;

public class FileNotExistsException extends RuntimeException {
    public FileNotExistsException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Файл по указанному пути не существует";
    }
}
