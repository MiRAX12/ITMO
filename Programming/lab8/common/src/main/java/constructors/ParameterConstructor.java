package constructors;

import exceptions.ExitWritten;

import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Class for reading parameters of fields from input.
 * <p>
 * This class uses generics to read different types of data
 * </p>
 *
 * @author Mirax
 * @since 1.0
 */
public class ParameterConstructor {
    public static Scanner consoleRead = new Scanner(System.in);

    public ParameterConstructor() {
    }

    /**
     * Tries to read parameter from input until it read successfully.
     * <p>
     * If exit is written, terminates program. Stops a loop if parameter was read, and
     * it satisfies constraints if there are such in parameter
     * </p>
     *
     * @return parameter read from input
     */
    public static <T> T readParameter(BuildingRequest<T> buildingRequest) throws
            NoSuchElementException, IllegalStateException, IllegalArgumentException,
            ExitWritten, DateTimeParseException {
        T x = null;
        boolean next = true;
        do {
            try {
                System.out.print(buildingRequest.message());
                String input = consoleRead.nextLine().trim();

                if (input.equals("exit")) {
                    System.out.println(new ExitWritten().getMessage());
                    System.exit(0);
                }

                if (!input.equals("^D")){
                    x = buildingRequest.parser().getResult(input);}
                next = false;

                if (buildingRequest.validation() != null) {
                    next = !buildingRequest.validation().test(x);
                }

            } catch (DateTimeParseException e) {
                System.out.println("Ошибка ввода даты. Пожалуйста, введите дату в правильном формате.");
                return null;
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат аргумента, повторите ввод");
            } catch (NoSuchElementException e) {
                System.exit(0);

            }
        } while (next);
        return x;

    }

}

