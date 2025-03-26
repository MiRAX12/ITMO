package commands;

import managers.CommandManager;
import utility.Request;
import utility.Response;

public class Help extends Command{
    CommandManager commandManager = CommandManager.getInstance();

    public Help(){
        super("help", "Вывести справку о доступных командах");
    }

    public Response execute(Request request){
        for(Command command: CommandList.commandList){
            System.out.print(command.toString() + ": ");
            command.getDescription();
        }
        return new Response("Команды вмещают максимум 1 аргумент");
    }
    @Override
    public String toString() {
        return getName();
    }
}
