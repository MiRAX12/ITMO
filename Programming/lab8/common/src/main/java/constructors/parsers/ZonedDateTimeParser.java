package constructors.parsers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public String formatter(String text, ResourceBundle currentBundle) {
        DateTimeFormatter formatter;
        ZonedDateTime date = ZonedDateTime.parse(text);
        switch (currentBundle.getLocale().toString()) {
            case "en_NZ":
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss z", currentBundle.getLocale());
                break;
            case "pl":
                formatter = DateTimeFormatter.ofPattern("yyyy MMMM d HH:mm:ss z", currentBundle.getLocale());
                break;
            case "ru":
                formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.' HH:mm:ss z", currentBundle.getLocale());
                break;
            case "pt":
                formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy HH:mm:ss z", currentBundle.getLocale());
                break;
            default:
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss z", Locale.US);
                break;
        }
        return date.format(formatter);
    }
}
