package commands;

import handlers.Router;
import utility.Request;
import utility.Response;

public class Help extends Command{
    Router router = Router.getInstance();

    public Help(){
        super("help", "Вывести справку о доступных командах");
    }

    public Response execute(Request request){
        for(Command command: CommandList.commandList){
            System.out.print(command.toString() + ": ");
            command.getDescription();
        }
        return Response.empty();
    }
    @Override
    public String toString() {
        return getName();
    }
}
