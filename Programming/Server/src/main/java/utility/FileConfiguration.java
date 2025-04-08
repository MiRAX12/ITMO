package utility;

import exceptions.*;
import managers.CollectionManager;

import java.nio.file.Path;

/**
 * A class to check file's permissions
 *
 * @author Mirax
 * @since 1.0
 */
public class FileConfiguration {

    /**
     * checks if path leads to a file, if it exists and able to read
     *
     * @param path a path to the file
     */
    public static void checkReadFile(Path path) throws Exception {
        if (!path.toFile().exists()) throw new FileNotExistsException();
        if (!path.toFile().isFile()) throw new NotAFileException();
        if (!path.toFile().canRead()) throw new NoReadPermissionException();
    }

    /**
     * checks if path leads to a file, if it exists and able for writing
     *
     * @param path a path to the file
     */
    public static void checkWriteFile(Path path) throws Exception {
        if (!path.toFile().exists()) throw new FileNotExistsException();
        if (!path.toFile().isFile()) throw new NotAFileException();
        if (!path.toFile().canWrite()) throw new NoWritePermissionException();
    }
}
