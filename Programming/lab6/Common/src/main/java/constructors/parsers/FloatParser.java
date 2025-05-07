package constructors.parsers;

/**
 * Class to parse string from input to Float value
 *
 * @author Mirax
 * @since 1.0
 */
public class FloatParser extends AbstractParser<Float> {

    /**
     * Method which parses string and returns it's Float value
     *
     * @param input a string from input
     * @return A Float value
     */
    @Override
    public Float getResult(String input) {
        return Float.valueOf(input);
    }

    /**
     * Overridden {code toString} to return name of this parser
     *
     * @return name of the parser
     */
    @Override
    public String toString() {
        return "FloatParser";
    }
}
