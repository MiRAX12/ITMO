package constructors.parsers;

import model.Coordinates;

/**
 * Abstract class of parsers used to parse data from string input
 *
 * @author Mirax
 * @since 1.0
 */
public abstract class AbstractParser<T> {

    /**
     * Abstract method to call an appropriate parser
     *
     * @param input a string from input
     */
    public abstract T getResult(String input);
}
