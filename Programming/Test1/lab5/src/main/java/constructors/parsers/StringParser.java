package constructors.parsers;

public class StringParser extends AbstractParser<String> {
    @Override
    public String getResult(String input) {
        return input;
    }

    @Override
    public String toString() {
        return "StringParser";
    }
}
