package commands;

import chat.Runner;
import utility.Request;
import utility.Response;

public class Exit extends Command {
    public Exit() {super("exit", "завершить программу без сохранения");}

    @Override
    public Response execute(Request request) {
            Runner.finish();
        return new Response("Программа завершена командой exit");
    }

    @Override
    public String toString() {
        return getName();
    }
}
