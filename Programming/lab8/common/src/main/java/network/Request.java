package network;

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
    private User user;
    private boolean registerRequired;


    public Request(User user, String command, String arg, Worker worker, boolean registerRequired) {
        this.command = command;
        this.arg = arg;
        this.worker = worker;
        this.user = user;
        this.registerRequired = registerRequired;
    }
//
//    public Request(User user, String command, Worker worker) {
//        this(user, command, null, worker);
//    }
//
//    public Request(User user, String command, String arg) {
//        this(user, command, arg, null);
//    }
//
//    public Request(User user, String command) {
//        this(user, command, null, null);
//    }
//
//    public Request(User user, boolean registerRequired) {
//        this(user, null, null, null, );
//    }
//
//    public Request(String command) {
//        this(null, command, null, null);
//    }

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean userRegisterRequired() {
        return registerRequired;
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
