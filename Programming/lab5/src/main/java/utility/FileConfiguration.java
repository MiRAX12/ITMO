package utility;

import exceptions.*;

import java.nio.file.Path;

public class FileConfiguration {

    public static void checkReadFile(Path path) throws Exception {
        if (!path.toFile().exists()) throw new FileNotExistsException();
        if (!path.toFile().isFile()) throw new NotAFileException();
        if (!path.toFile().canRead()) throw new NoReadPermissionException();
    }

    public static void checkWriteFile(Path path) throws Exception {
        if (!path.toFile().exists()) throw new FileNotExistsException();
        if (!path.toFile().isFile()) throw new NotAFileException();
        if (!path.toFile().canWrite()) throw new NoWritePermissionException();
    }
}
