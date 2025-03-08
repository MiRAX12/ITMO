package io;

import java.io.IOException;

public interface IOHandler<T> extends AutoCloseable {

    T read() throws IOException;

    void write(T output);
}
