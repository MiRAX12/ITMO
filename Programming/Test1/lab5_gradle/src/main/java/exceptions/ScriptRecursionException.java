package exceptions;

public class ScriptRecursionException extends RuntimeException {
    public ScriptRecursionException() {
        super();
    }

    @Override
    public String getMessage() {
        return "В скрипте обнаружена рекурсия, выполнение будет прервано";
    }
}
