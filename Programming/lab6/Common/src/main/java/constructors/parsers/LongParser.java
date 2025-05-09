package constructors.parsers;

/**
 * Class to parse string from input to Long value
 *
 * @author Mirax
 * @since 1.0
 */
public class LongParser extends AbstractParser<Long> {

    /**
     * Method which parses string and returns it's Long value
     *
     * @param input a string from input
     * @return A Long value
     */
    @Override
    public Long getResult(String input) {
        return Long.valueOf(input);
    }

    /**
     * Overridden {code toString} to return name of this parser
     *
     * @return name of the parser
     */
    @Override
    public String toString() {
        return "LongParser";
    }
}

