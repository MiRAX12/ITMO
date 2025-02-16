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

    public <T> T askParameter(String parser, Class<T> dataType, String message) throws
            NoSuchElementException, IllegalStateException, IllegalArgumentException,
            ExitWritten, DateTimeParseException{
        T x = null;

        CommandManager commandManager = new CommandManager();
        commandManager.setUpParser(new FloatParser());
        commandManager.setUpParser(new LongParser());
        commandManager.setUpParser(new LocalDateTimeParser());
        commandManager.setUpParser(new ZonedDateTimeParser());
        commandManager.setUpParser(new StringParser());
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
//        T x;
//        boolean next = true;
//        do {
//            try {
//                System.out.print(message);
//                String input = consoleRead.nextLine().trim();
//                if (input.equals("exit")) {
//                    throw new ExitWritten("Выход из консоли...");
//                }
//                if (dataType == Float.class) {
//                    x = dataType.cast(Float.parseFloat(input));
//                    return x;
//
//                } else if (dataType == Long.class) {
//                    x = dataType.cast(Long.parseLong(input));
//                    return x;
//
//                } else if (dataType == String.class) {
//                    x = dataType.cast(input);
//                    return x;
//
//                } else if (dataType == LocalDateTime.class) {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//                    x = dataType.cast(LocalDateTime.parse(input, formatter));
//                    return x;
//
//                } else if (dataType == ZonedDateTime.class) {
//                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
//                    x = dataType.cast(ZonedDateTime.parse(input, formatter));
//                    return x;
//
//                } else {
//                    next = false;
//                }
//
//            } catch (NumberFormatException e) {
//                System.out.println("Неправильный формат числа, повторите ввод");
//            } catch (NoSuchElementException e) {
//
//            } catch (IllegalStateException e) {
//                System.out.println("Непредвиденная ошибка");
//
//            } catch (IllegalArgumentException e) {
//                System.out.println("Неправильный формат аргумента, повторите ввод");
//            }
//        }while (next) ;
//        return null;
//    }
    }
}

