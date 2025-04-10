package utility;

import model.Worker;

import java.io.Serializable;

/**
 * A class create a request from input
 *
 * @author Mirax
 * @since 1.0
 */
public class Request implements Serializable {
    private String command;
    private String arg;
    private Worker worker;

    public Request(String command, String arg, Worker worker) {
        this.command = command;
        this.arg = arg;
        this.worker = worker;
    }

    public Request(String command, String arg) {
        this(command, arg, null);
    }

    public Request(String command) {
        this(command, null, null);
    }

    public String getCommand() {
        return command;
    }

    public String getArg() {
        return arg;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public String toString() {
        return "Request{" +
                "command='" + command + '\'' +
                ", arg='" + arg + '\'' +
                ", worker=" + worker +
                '}';
    }
}
