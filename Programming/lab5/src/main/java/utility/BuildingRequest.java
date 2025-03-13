package utility;

import constructors.parsers.AbstractParser;

import java.util.function.Predicate;

public record BuildingRequest<T>(AbstractParser<T> parser, Predicate<T> validation, String message) {
    public BuildingRequest(AbstractParser<T> parser, String message){this(parser, null, message);}
}
