package Data;

import Exceptions.ExitWritten;
import Interfaces.Constructable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;

import static Data.Status.HIRED;

public class ParameterConstructor<T> implements Constructable<T> {
    private float f;
    private long l;
    private String s;

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

    public static <E extends Enum<E>> E askParameterEnum(String message, Class<E> enumClass) {
        ParameterConstructor<E> parameterConstructor = new ParameterConstructor<>();
        return parameterConstructor.askParameter(message, enumClass);
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
                    next = false;
                    return x;

                } else if (dataType == Long.class) {
                    x = dataType.cast(Long.parseLong(input));
                    next = false;
                    return x;

                } else if (dataType == String.class) {
                    x = dataType.cast(input);
                    next = false;
                    return x;

                } else if (dataType.isEnum()) {
                    x = dataType.cast(input.toUpperCase());
                    next = false;
                    return x;

                } else if (dataType == LocalDate.class) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    x = dataType.cast(LocalDateTime.parse(input, formatter));
                    next = false;
                    return x;

                } else if (dataType == ZonedDateTime.class) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                    x = dataType.cast(ZonedDateTime.parse(input, formatter));
                    next = false;
                    return x;

                } else {
                    throw new IllegalArgumentException("Неправильный формат" +
                            ", повторите ввод");
                }

            } catch (NumberFormatException e) {
                System.out.println("Неправильный формат, повторите ввод");
            } catch (NoSuchElementException e) {
                System.out.println("Пользовательский ввод не обнаружен");
            } catch (IllegalStateException e) {
                System.out.println("Непредвиденная ошибка");
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка ввода даты. Пожалуйста, введите дату в правильном формате.");
            }
        }
        while (next) ;
        return null;
    }
}
