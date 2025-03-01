package constructors.parsers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ZonedDateTimeParser extends AbstractParser<ZonedDateTime> {
    @Override
    public ZonedDateTime getResult(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        return ZonedDateTime.parse(input, formatter);
    }

    @Override
    public String toString() {
        return "ZonedDateTimeParser";
    }
}
