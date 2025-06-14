package constructors.parsers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

public class LocalDateParser {

    public String formatter(String text, ResourceBundle currentBundle) {
        DateTimeFormatter formatter;
        LocalDate date = LocalDate.parse(text);
        switch (currentBundle.getLocale().toString()) {
            case "en_NZ":
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", currentBundle.getLocale());
                break;
            case "pl":
                formatter = DateTimeFormatter.ofPattern("yyyy MMMM d", currentBundle.getLocale());
                break;
            case "ru":
                formatter = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.'", currentBundle.getLocale());
                break;
            case "pt":
                formatter = DateTimeFormatter.ofPattern("d. MMMM yyyy", currentBundle.getLocale());
                break;
            default:
                formatter = DateTimeFormatter.ofPattern("MMMM d, yyyy", Locale.US);
                break;
        }
        return date.format(formatter);
    }}
