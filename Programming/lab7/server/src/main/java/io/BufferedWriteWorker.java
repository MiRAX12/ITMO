package io;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
 * Writes data to a file using {@link BufferedWriteWorker}.
 * <p>
 * This class provides file writing using {@link BufferedWriteWorker}.
 * The data in the file is always overwritten.
 * </p>
 *
 * @author Mirax
 * @see BufferedWriteWorker
 * @see BaseWriteWorker
 * @since 1.0
 */
public class BufferedWriteWorker implements BaseWriteWorker{
    private final BufferedWriter writer;

    /**
     * Creates a new instance of the {@code BufferedWriteWorker} with the specified file path.
     *
     * @param path the path to the file
     * @throws IOException if an I/O error occurs while opening the file
     */
    public BufferedWriteWorker(Path path) throws IOException {
        this.writer = new BufferedWriter(new FileWriter(path.toFile(), false));
    }

    /**
     * Writes character sequence of provided data
     *
     * @param data a string of data to be written
     */
    public void write(String data) throws IOException {
        writer.append(data).flush();
    }

    /**
     * Closes the file writer streams.
     * <p>
     * Once closed, no further write operations can be performed on this worker.
     * </p>
     */
    @Override
    public void close() throws IOException {
        writer.close();
    }
}

