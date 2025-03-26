package io;

import java.io.*;
import java.nio.file.Path;

public class BufferedReadWorker implements BaseReadWorker{
    private final BufferedReader reader;

    public BufferedReadWorker(Path path) throws IOException {
        this.reader = new BufferedReader(new FileReader(path.toFile()));
    }

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

    @Override
    public void close() throws Exception {
        reader.close();
    }
}
