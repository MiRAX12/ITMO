package Data;

import Exceptions.ExitWritten;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ParameterConstructor<T> {
    static final Scanner consoleRead = new Scanner(System.in);

    public static Float askParameterFloat(String message) {
        ParameterConstructor<Float> parameterConstructor = new ParameterConstructor<>();
        return parameterConstructor.askParameter(message, Float.class);
    }

    public static Long askParameterLong(String message) {
        ParameterConstructor<Long> parameterConstructor = new ParameterConstructor<>();
        return parameterConstructor.askParameter(message, Long.class);
    }

    public static String askParameterString(String message) {
        ParameterConstructor<String> parameterConstructor = new ParameterConstructor<>();
        return parameterConstructor.askParameter(message, String.class);
    }

    public static LocalDateTime askLocalDateTime(String message) {
        ParameterConstructor<LocalDateTime> parameterConstructor = new ParameterConstructor<>();
        return parameterConstructor.askParameter(message, LocalDateTime.class);
    }

    public static ZonedDateTime askZonedDateTime(String message) {
        ParameterConstructor<ZonedDateTime> parameterConstructor = new ParameterConstructor<>();
        return parameterConstructor.askParameter(message, ZonedDateTime.class);
    }

    private T askParameter(String message, Class<T> dataType) {
        T x;
        boolean next = true;
        do {
            try {
                System.out.print(message);
                String input = consoleRead.nextLine().trim();
                if (input.equals("exit")) {
                    throw new ExitWritten("Выход из консоли...");
                }
                if (dataType == Float.class) {
                    x = dataType.cast(Float.parseFloat(input));
                    return x;

                } else if (dataType == Long.class) {
                    x = dataType.cast(Long.parseLong(input));
                    return x;

                } else if (dataType == String.class) {
                    x = dataType.cast(input);
                    return x;

                } else if (dataType == LocalDateTime.class) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    x = dataType.cast(LocalDateTime.parse(input, formatter));
                    return x;

                } else if (dataType == ZonedDateTime.class) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                    x = dataType.cast(ZonedDateTime.parse(input, formatter));
                    return x;

                } else {
                    next = false;
                }

            } catch (NumberFormatException e) {
                System.out.println("Неправильный формат числа, повторите ввод");
            } catch (NoSuchElementException e) {
                System.out.println("Пользовательский ввод не обнаружен");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка");
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка ввода даты. Пожалуйста, введите дату в правильном формате.");
            } catch (IllegalArgumentException e) {
                System.out.println("Неправильный формат аргумента, повторите ввод");
            }
        }while (next) ;
        return null;
    }
}
