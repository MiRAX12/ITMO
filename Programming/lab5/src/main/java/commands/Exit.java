package commands;

import utility.Engine;

public class Exit extends Command implements Executable {

    @Override
    public void execute() {
        Engine.finishProgramm();
    }

    @Override
    public String toString() {
        return "exit";
    }


}
