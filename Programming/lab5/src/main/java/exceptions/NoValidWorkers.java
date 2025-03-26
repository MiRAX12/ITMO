package exceptions;

public class NoValidWorkers extends Exception {
    public NoValidWorkers() {
        super();
    }

    @Override
    public String getMessage(){return "Все workers повреждены и не подлежат чтению";}
}
