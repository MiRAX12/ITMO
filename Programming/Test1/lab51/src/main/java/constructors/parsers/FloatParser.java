package constructors.parsers;

public class FloatParser extends AbstractParser<Float> {

    @Override
    public Float getResult(String input) {
        return Float.parseFloat(input);
    }

    @Override
    public String toString() {
        return "FloatParser";
    }
}
