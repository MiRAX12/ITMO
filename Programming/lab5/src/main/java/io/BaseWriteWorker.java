package io;

import java.io.IOException;

public interface BaseWriteWorker extends AutoCloseable {

    void write(String data) throws IOException;

}
