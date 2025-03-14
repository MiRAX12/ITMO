//package commands;
//
//import constructors.WorkerBuilder;
//import managers.CollectionManager;
//import utility.Request;
//import utility.Response;
//
//import java.io.IOException;
//
//public class Insert extends Command {
//
//    public Insert() {
//        super("insert", "Добавляет Worker с заданным ключом");
//    }
//
//    @Override
//    public Response execute(Request request) {
//        try {
//            CollectionManager.getInstance()
//                    .addElement(WorkerBuilder.build(), Integer.parseInt(request.arg()));
//        } catch (IOException e) {
//            System.out.println("Ошибка чтения");
//        }
//        return new Response("Новый Worker с ключом %d добавлен");
//    }
//
//    @Override
//    public String toString() {
//        return getName();
//    }
//}
