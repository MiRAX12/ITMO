package constructors.parsers;

public class LongParser extends AbstractParser<Long> {
    @Override
    public Long getResult(String input) {
        return Long.parseLong(input);
    }

    @Override
    public String toString() {
        return "LongParser";
    }
}

