package utility.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.Valid;
import model.Worker;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkerKeys {
    @JacksonXmlProperty(localName = "key")
    String key;

    @JacksonXmlProperty(localName = "worker")
    Worker worker;

    public WorkerKeys() {}

    public WorkerKeys(String key, Worker worker) {
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
