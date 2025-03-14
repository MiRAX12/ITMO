package constructors;

import constructors.parsers.*;
import data.Status;
import exceptions.ExitWritten;
import managers.ParserManager;
import utility.BuildingRequest;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ParameterConstructor {
    static final Scanner consoleRead = new Scanner(System.in);
    private static ParameterConstructor instance;
    boolean next;

    private ParameterConstructor() {}

    public static ParameterConstructor getInstance() {
        return instance == null ? instance = new ParameterConstructor() : instance;
    }

    public <T> T askParameter(BuildingRequest<T> buildingRequest) throws
            NoSuchElementException, IllegalStateException, IllegalArgumentException,
            ExitWritten, DateTimeParseException{
        T x = null;

        this.next = true;
        do {
            try {
            System.out.print(buildingRequest.message());
            String input = consoleRead.nextLine().trim();
            if (input.equals("exit")) {
                throw new ExitWritten("Выход из консоли...");
            }

            if (input.isEmpty()) x = null;
            x = buildingRequest.parser().getResult(input);
            next = false;

            if (buildingRequest.validation()!=null) {
                next = !buildingRequest.validation().test(x);
            }

            } catch (DateTimeParseException e) {
                System.out.println("Ошибка ввода даты. Пожалуйста, введите дату в правильном формате.");
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат аргумента, повторите ввод");
            } catch (NoSuchElementException e) {
                System.out.println("Пользовательский ввод не обнаружен");
            }

        } while(next);
        return x;

    }
}

