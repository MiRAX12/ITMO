package constructors;

import constructors.parsers.*;
import data.Status;
import exceptions.ExitWritten;
import managers.CommandManager;
import managers.ParserManager;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ParameterConstructor {
    static final Scanner consoleRead = new Scanner(System.in);
    private boolean next;
        private static ParameterConstructor instance;

    private ParameterConstructor() {}

    public static ParameterConstructor getInstance() {
        return instance == null ? instance = new ParameterConstructor() : instance;
    }

    public boolean isNext() {
        return next;
    }

    public void setNext(boolean next) {
        this.next = next;
    }

    public <T> T askParameter(AbstractParser<T> parser, String message) throws
            NoSuchElementException, IllegalStateException, IllegalArgumentException,
            ExitWritten, DateTimeParseException{
        T x = null;
//
//        ParserManager parserManager = ParserManager.getInstance();
//        parserManager.setUpParser(new FloatParser());
//        parserManager.setUpParser(new LongParser());
//        parserManager.setUpParser(new LocalDateTimeParser());
//        parserManager.setUpParser(new ZonedDateTimeParser());
//        parserManager.setUpParser(new StringParser());
//        parserManager.setUpParser(new IntegerParser());

        do {
            try {
            System.out.print(message);
            String input = consoleRead.nextLine().trim();
            if (input.equals("exit")) {
                throw new ExitWritten("Выход из консоли...");
            }
            x = parser.getResult(input);

//            if (parser.equals("EnumParser"))
//                x = dataType.cast(Enum.valueOf(Status.class, input));
//            else
//                x = dataType.cast(parserManager.getParsers().get(parser).getResult(input));



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

