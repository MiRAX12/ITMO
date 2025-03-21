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
//    public Command(String name, String description) {
//        this.name = name;
//        this.description = description;
//    }
//
//    public String getName() {
//        return name;
//    }
//    public String getDescription() {
//        return description;
//    }
//
//    public abstract ExecutionResponse apply(String argument);

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Command command = (Command) o;
//        return Objects.equals(name, command.name) && Objects.equals(description, command.description);
//    }
//
//    @Override
//    public int hashCode() {
//        return name.hashCode() + description.hashCode();
//    }
//
//    @Override
//    public String toString() {
//        return "Command{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                '}';
//    }
//
//}
