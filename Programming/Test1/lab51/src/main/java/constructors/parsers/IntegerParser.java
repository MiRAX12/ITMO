package constructors.parsers;

public class IntegerParser extends AbstractParser<Integer> {

    @Override
    public Integer getResult(String input) {
        return Integer.parseInt(input);
    }

    @Override
    public String toString() {
        return "IntegerParser";
    }
}

