package constructors.parsers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class to parse string from input to ZonedDateTime value
 *
 * @author Mirax
 * @since 1.0
 */
public class ZonedDateTimeParser extends AbstractParser<ZonedDateTime> {

    /**
     * Method which parses string and returns it's ZonedDateTime value
     *
     * @param input a string from input
     * @return A ZonedDateTime value
     */
    @Override
    public ZonedDateTime getResult(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return ZonedDateTime.parse(input, formatter);
    }

    /**
     * Overridden {code toString} to return name of this parser
     *
     * @return name of the parser
     */
    @Override
    public String toString() {
        return "ZonedDateTimeParser";
    }
}
