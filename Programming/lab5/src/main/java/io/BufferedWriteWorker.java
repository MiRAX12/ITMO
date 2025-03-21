package io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class BufferedWriteWorker implements AutoCloseable{
    private final PrintWriter writer;

    public BufferedWriteWorker(Path path) throws IOException {
        this.writer = new PrintWriter(new FileWriter(path.toFile(), false));
    }

    public void write(final String data) {
        writer.append(data).flush();
    }

    @Override
    public void close() {
        writer.close();
    }
}
