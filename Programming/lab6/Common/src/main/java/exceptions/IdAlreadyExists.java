package exceptions;

public class IdAlreadyExists extends RuntimeException {
  int workerkey;
    public IdAlreadyExists(int workerKey) {
        super();
        this.workerkey = workerKey;
    }

  @Override
  public String getMessage() {
    return "Worker с ключом " + workerkey + " имеет id, который уже есть в коллекции." +
            " Пожалуйста, перепроверьте валидность worker в файле";
  }
}
