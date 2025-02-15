package Commands;

//import Data.Worker;
//import Managers.CollectionManager;
//
//public class Add extends Command {
//    private final CollectionManager collectionManager;
//
//    public Add(CollectionManager collectionManager) {
//        super("add {element}", "добавить новый элемент в коллекцию");
//        this.collectionManager = collectionManager;
//    }

//    @Override
//    public ExecutionResponse apply(String argument){
//        try {
//            if (!argument.isEmpty()) return new ExecutionResponse(false,
//                    "Неправильное кол-во аргументов!/nИспользование:'" + getName() + "'");
//            System.out.println("Создание нового Worker:");
////            Worker worker = new Worker.WorkerBuilder().name().coordinates().salary().status().person().build();
//
//            if (worker != null && worker.validate()) {
//                collectionManager.add(worker);
//                return new ExecutionResponse("Worker успешно добавлен");
//
//            } else
//                return new ExecutionResponse(false, "Поля worker не валидны! Создание отменено!");
//        }catch (Exception e){
//            return new ExecutionResponse(false, "Отмена...");
//        }
//    }
//}
