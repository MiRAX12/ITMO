package utility.wrappers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

/**
 * A wrapper class that contains list of {@link WorkerKeys} to be written to a file
 *
 * @author Mirax
 * @see WorkerKeys
 * @since 1.0
 */
@JacksonXmlRootElement(localName = "ArrayList")
public class WorkerKeyList {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "workerKeys")
    private List<WorkerKeys> workerKeys;

    public void setWorkerKeys(List<WorkerKeys> workerKeys) {
        this.workerKeys = workerKeys;
    }

    public List<WorkerKeys> getWorkerKeys() {
        return workerKeys;
    }
}
