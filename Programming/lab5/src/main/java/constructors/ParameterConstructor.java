package constructors;

import constructors.parsers.*;
import data.Status;
import exceptions.ExitWritten;
import managers.CommandManager;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

public class ParameterConstructor {
    static final Scanner consoleRead = new Scanner(System.in);
    private static ParameterConstructor instance;

    private ParameterConstructor() {}

    public static ParameterConstructor getInstance() {
        return instance == null ? instance = new ParameterConstructor() : instance;
    }

    public <T> T askParameter(String parser, Class<T> dataType, String message) throws
            NoSuchElementException, IllegalStateException, IllegalArgumentException,
            ExitWritten, DateTimeParseException{
        T x = null;

        CommandManager commandManager = CommandManager.getInstance();
        commandManager.setUpParser(new FloatParser());
        commandManager.setUpParser(new LongParser());
        commandManager.setUpParser(new LocalDateTimeParser());
        commandManager.setUpParser(new ZonedDateTimeParser());
        commandManager.setUpParser(new StringParser());
        commandManager.setUpParser(new IntegerParser());

        boolean next = true;
        do {
            try {
            System.out.print(message);
            String input = consoleRead.nextLine().trim();
            if (input.equals("exit")) {
                throw new ExitWritten("Выход из консоли...");
            }
            if (parser.equals("EnumParser"))
                x = dataType.cast(Enum.valueOf(Status.class, input));
            else
                x = dataType.cast(commandManager.getParsers().get(parser).getResult(input));
            next = false;

            } catch (DateTimeParseException e) {
                System.out.println("Ошибка ввода даты. Пожалуйста, введите дату в правильном формате.");
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат аргумента, повторите ввод");
            } catch (NoSuchElementException e) {
                System.out.println("Пользовательский ввод не обнаружен");
//            } catch (ExitWritten e){
//                System.out.println("Выход из консоли...");
            }

        } while(next);
        return x;

    }
}

