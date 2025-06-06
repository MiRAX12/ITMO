package constructors.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class to parse string from input to LocalDateTime value
 *
 * @author Mirax
 * @since 1.0
 */
public class LocalDateTimeParser extends AbstractParser<LocalDateTime> {

    /**
     * Method which parses string and returns it's LocalDateTime value
     *
     * @param input a string from input
     * @return A LocalDateTime value
     */
    public LocalDateTime getResult(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(input, formatter);
    }

    /**
     * Overridden {code toString} to return name of this parser
     *
     * @return name of the parser
     */
    @Override
    public String toString() {
        return "LocalDateTimeParser";
    }
}

