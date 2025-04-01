package utility.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import jakarta.validation.Valid;
import model.Worker;

import java.util.List;

/**
 * A class that contains a pair key-worker of an element {@link Worker} from map
 *
 * @author Mirax
 * @see Worker
 * @since 1.0
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JacksonXmlRootElement(localName = "workerKeys")
public class WorkerKeys {
    @JacksonXmlProperty(localName = "key")
    String key;

    @Valid
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
