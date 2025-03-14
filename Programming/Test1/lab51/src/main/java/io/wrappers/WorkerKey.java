package io.wrappers;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import data.Worker;

public class WorkerKey {
    @JacksonXmlProperty(localName = "key")
    String key;

    @JacksonXmlProperty(localName = "worker")
    Worker worker;

    public WorkerKey() {}

    public WorkerKey(String key, Worker worker) {
        this.key = key;
        this.worker = worker;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public String getKey() {
        return key;
    }

    public Worker getWorker() {
        return worker;
    }

}
