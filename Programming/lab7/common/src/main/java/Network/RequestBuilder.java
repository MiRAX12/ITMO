package Network;

import model.Worker;

public class RequestBuilder {
    private User user = null;
    private String command;
    private String arg = null;
    private Worker worker = null;
    private boolean registerRequired;

    public RequestBuilder setUser(User user) {
        this.user = user;
        return this;
    }

    public RequestBuilder setCommand(String command) {
        this.command = command;
        return this;
    }

    public RequestBuilder setArg(String arg) {
        this.arg = arg;
        return this;
    }

    public RequestBuilder setWorker(Worker worker) {
        this.worker = worker;
        return this;
    }

    public RequestBuilder setRegisterRequired(boolean registerRequired) {
        this.registerRequired = registerRequired;
        return this;
    }

    public Request build() {
        return new Request(user, command, arg, worker, registerRequired);
    }
}