package io;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

public interface BaseWriter {

    public void writeToFile() throws IOException, XMLStreamException;
}
