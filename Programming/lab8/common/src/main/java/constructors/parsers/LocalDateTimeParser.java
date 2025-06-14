package constructors.parsers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.ResourceBundle;

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

    public String formatter(String text, ResourceBundle currentBundle) {
        DateTimeFormatter formatter;
        LocalDateTime date = LocalDateTime.parse(text);
        switch (currentBundle.getLocale().toString()) {
            case "en_NZ":
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss", currentBundle.getLocale());
                break;
            case "pl":
                formatter = DateTimeFormatter.ofPattern("yyyy MMMM d HH:mm:ss", currentBundle.getLocale());
                break;
            case "ru":
                formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.' HH:mm:ss", currentBundle.getLocale());
                break;
            case "pt":
                formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy HH:mm:ss", currentBundle.getLocale());
                break;
            default:
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss", Locale.US);
                break;
        }
        return date.format(formatter);
    }
}

