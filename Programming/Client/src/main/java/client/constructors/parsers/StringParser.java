package client.constructors.parsers;

/**
 * Class for generality way of reading input through parses.
 *
 * @author Mirax
 * @since 1.0
 */
public class StringParser extends AbstractParser<String> {

    /**
     * Method which returns string value
     *
     * @param input a string from input
     * @return A String value
     */
    @Override
    public String getResult(String input) {
        return input;
    }

    /**
     * Overridden {code toString} to return name of this parser
     *
     * @return name of the parser
     */
    @Override
    public String toString() {
        return "StringParser";
    }
}
