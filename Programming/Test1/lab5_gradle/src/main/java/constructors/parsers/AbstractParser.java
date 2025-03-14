package constructors.parsers;

import java.util.Scanner;

public abstract class AbstractParser<T> {
    public abstract T getResult(String input);
}
