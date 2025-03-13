package managers;

import constructors.parsers.AbstractParser;

import java.util.HashMap;
import java.util.Map;

public class ParserManager {
    private final Map<String, AbstractParser<?>> parsers = new HashMap<>();
    private static final ParserManager INSTANCE = new ParserManager();

    private ParserManager(){};

    public static ParserManager getInstance(){
        return INSTANCE;
    }

    public void setUpParser(AbstractParser<?> command) {
        addParser(command.toString(), command);
    }

    public void addParser(String name, AbstractParser<?> parser) {
        parsers.put(name, parser);
    }

    public Map<String, AbstractParser<?>> getParsers() {
        return parsers;
    }
}
