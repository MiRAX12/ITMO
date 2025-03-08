package managers;

import constructors.parsers.AbstractParser;

import java.util.HashMap;
import java.util.Map;

public class ParserManager {
    private final Map<String, AbstractParser<?>> parsers = new HashMap<>();
    private static ParserManager instance;
    private ParserManager(){};

    public static ParserManager getInstance(){
        return instance == null ? instance = new ParserManager() : instance;
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
