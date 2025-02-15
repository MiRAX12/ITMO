package Commands;

import Managers.CollectionManager;

public class Help extends Command implements Executable{
    private static final  String helpMessage="+help : вывести справку по доступным командам\n" +
            "+info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "1show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
            "insert null {element} : добавить новый элемент с заданным ключом\n" +
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
            "remove_key null : удалить элемент из коллекции по его ключу\n" +
            "1clear : очистить коллекцию\n" +
            "save : сохранить коллекцию в файл\n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
            "1exit : завершить программу (без сохранения в файл)\n" +
            "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
            "remove_greater_key null : удалить из коллекции все элементы, ключ которых превышает заданный\n" +
            "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный\n" +
            "remove_all_by_start_date startDate : удалить из коллекции все элементы, значение поля startDate которого эквивалентно заданному\n" +
            "remove_any_by_end_date endDate : удалить из коллекции один элемент, значение поля endDate которого эквивалентно заданному\n" +
            "filter_starts_with_name name : вывести элементы, значение поля name которых начинается с заданной подстроки";

    @Override
    public void execute(String[] splitedRequest, CollectionManager collectionManager) {
        System.out.println(helpMessage);
    }

    @Override
    public String toString() {
        return "help";
    }
}
