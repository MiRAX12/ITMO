package commands;

import utility.Request;
import utility.Response;

public abstract class Command {
    private final String name;
    private final String description;


    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract Response execute(Request request);

    public String getName() {
        return name;
    }

    public void getDescription() {
        System.out.println(description);;
    }

}

