package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

public class BufferedWriteWorker implements BaseWriteWorker{
    private final BufferedWriter writer;

    public BufferedWriteWorker(Path path) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(path.toFile(), false));
    }

    public void write(String data) throws IOException {
        writer.append(data).flush();
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}

