package io;

/**
 * Base file reader interface. Provides method for file reading.
 * Implements {@link AutoCloseable} to prevent resources loss
 *
 * @author Mirax
 * @since 1.0
 */
public interface BaseReadWorker extends AutoCloseable {

    /**
     * Base method for reading file
     *
     * @return Map of workers with keys from file.
     */
    String read();

}
