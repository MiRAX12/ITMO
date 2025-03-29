package io;

import model.Worker;
import java.util.Map;

/**
 * Base serialized files reader interface. Provides method for file reading.
 *
 * @author Mirax
 * @since 1.0
 */
public interface BaseReader {

    /**
     * Base method for reading file
     *
     * @return Map of workers with keys from file.
     */
    Map<Integer, Worker> readFromFile() throws Exception;
}
