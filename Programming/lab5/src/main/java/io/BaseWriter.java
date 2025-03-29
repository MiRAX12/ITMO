package io;

/**
 * Base serialized data writer interface. Provides method for file writing.
 *
 * @author Mirax
 * @since 1.0
 */
public interface BaseWriter {

    /**
     * Base method for writing collection in file.
     */
    void writeToFile() throws Exception;
}
