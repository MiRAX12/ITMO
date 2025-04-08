package io;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;

/**
 * Writes to a file using {@link PrintWriter}.
 * <p>
 * This class provides file writing using {@link PrintWriter}
 * </p>
 *
 * @author Mirax
 * @see PrintWriter
 * @see BaseWriteWorker
 * @since 1.0
 */
public class PrintWriteWorker implements BaseWriteWorker{
    private final PrintWriter writer;

    /**
     * Creates a new instance of the {@code PrintWriter} with the specified file path.
     *
     * @param path the path to the file
     * @throws IOException if an I/O error occurs while opening the file
     */
    public PrintWriteWorker(Path path) throws IOException {
        this.writer = new PrintWriter(new FileWriter(path.toFile(), false));
    }

    /**
     * Writes character sequence of provided data
     *
     * @param data a string of data to be written
     */
    public void write(String data) {
        writer.append(data).flush();
    }

    /**
     * Closes the file writer streams.
     * <p>
     * Once closed, no further write operations can be performed on this worker.
     * </p>
     */
    @Override
    public void close() {
        writer.close();
    }
}
