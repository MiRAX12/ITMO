package constructors;

import constructors.parsers.AbstractParser;

import java.util.function.Predicate;

/**
 * A record class to create a request for building a field of {@link common.model.Worker}
 * Contains a parser to parse input, constraints of field and welcome-message
 *
 * @see common.model.Worker
 *
 * @author Mirax
 * @since 1.0
 */
public record BuildingRequest<T>(AbstractParser<T> parser, Predicate<T> validation, String message) {

    /**
     * Creates request for building a field of {@link common.model.Worker} if it has not any constraints
     *
     * @param parser parser to parse input
     * @param message a welcome message
     */
    public BuildingRequest(AbstractParser<T> parser, String message){this(parser, null, message);}
}
