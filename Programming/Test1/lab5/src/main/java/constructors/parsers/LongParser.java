package constructors.parsers;

public class LongParser extends AbstractParser<Long> {
    @Override
    public Long getResult(String input) {
        return Long.valueOf(input);
    }

    @Override
    public String toString() {
        return "LongParser";
    }
}

