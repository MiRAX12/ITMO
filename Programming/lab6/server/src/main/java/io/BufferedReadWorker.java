package io;

import java.io.*;
import java.nio.file.Path;


/**
 * Reads a file using {@link BufferedReader}.
 * <p>
 * This class provides file reading using {@link BufferedReader}
 * </p>
 *
 * @author Mirax
 * @see BufferedReader
 * @see BaseReadWorker
 * @since 1.0
 */
public class BufferedReadWorker implements BaseReadWorker{
    private final BufferedReader reader;

    /**
     * Creates a new instance of the {@code BufferedReadWorker} with the specified file path.
     *
     * @param path the path to the file
     * @throws IOException if an I/O error occurs while opening the file
     */
    public BufferedReadWorker(Path path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path.toFile()));
    }

    /**
     * Reads entire file and returns a string of data
     * <p>
     * This method reads each line of the file and appends it to
     * a {@link StringBuilder} character sequence.
     *
     * @return A string representing the data in {@code fileContent} sequence.
     */
    public String read(){
        StringBuilder fileContent = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.append(line);
            }
            return fileContent.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    /**
     * Closes the file reader streams.
     * <p>
     * Once closed, no further read operations can be performed on this worker.
     * </p>
     *
     * @throws Exception if an I/O error occurs during closure
     */
    @Override
    public void close() throws Exception {
        reader.close();
    }
}
