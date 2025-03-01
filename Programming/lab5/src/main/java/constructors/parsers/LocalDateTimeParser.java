package constructors.parsers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeParser extends AbstractParser<LocalDateTime> {
    @Override
    public LocalDateTime getResult(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(input, formatter);
    }

    @Override
    public String toString() {
        return "LocalDateTimeParser";
    }
}

