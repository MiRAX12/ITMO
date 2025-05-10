package io;

import java.io.IOException;

/**
 * Base file writer interface. Provides method for file writing.
 * Implements {@link AutoCloseable} to prevent resources loss
 *
 * @author Mirax
 * @since 1.0
 */
public interface BaseWriteWorker extends AutoCloseable {

    /**
     * Base method for writing collection in file.
     */
    void write(String data) throws IOException;

}
