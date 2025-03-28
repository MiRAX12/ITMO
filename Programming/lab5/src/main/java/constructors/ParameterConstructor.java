package constructors;

import exceptions.ExitWritten;
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
                System.out.println(new ExitWritten().getMessage());
                System.exit(0);
            }

            if (!input.equals("^D")) x = buildingRequest.parser().getResult(input);
            next = false;

            if (buildingRequest.validation()!=null) {
                next = !buildingRequest.validation().test(x);
            }

            } catch (DateTimeParseException e) {
                System.out.println("Ошибка ввода даты. Пожалуйста, введите дату в правильном формате.");
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат аргумента, повторите ввод");
            } catch (NoSuchElementException e) {
                System.exit(0);

            }
        } while(next);
        return x;

    }
}

