package io;

import java.io.*;
import java.nio.file.Path;

public class FileReadWorker implements AutoCloseable{
    private final BufferedReader reader;

    public FileReadWorker(Path path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path.toFile()));
    }

    public String read(){
        StringBuilder xmlContent = new StringBuilder();
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line);
            }
            return xmlContent.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
