package utility.wrappers;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "ArrayList")
public class WorkerKeyList {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "workerKeys")
    private List<WorkerKey> workerKeys;




    public void setWorkerKeys(List<WorkerKey> workerKeys) {
        this.workerKeys = workerKeys;
    }

    public List<WorkerKey> getWorkerKeys() {
        return workerKeys;
    }
}
