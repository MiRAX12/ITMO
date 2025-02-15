package Commands;

import Utility.Engine;

public class Exit extends Command implements Executable {

    @Override
    public void execute(String[] splitedRequest) {
        Engine.finishProgramm();
    }

    @Override
    public String toString() {
        return "exit";
    }


}
