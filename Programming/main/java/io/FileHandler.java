package io;

import java.io.*;
import java.nio.file.Path;

public class FileHandler implements IOHandler<String> {

    private final BufferedReader reader;
    private final PrintWriter writer;
    private boolean append;

    public FileHandler(Path path, boolean append) throws IOException {
        this.reader = new BufferedReader(new FileReader(path.toFile()));
        this.writer = new PrintWriter(new FileWriter(path.toFile(), append));
        this.append = append;
    }

    public FileHandler(Path path) throws IOException {
    this.reader = new BufferedReader(new FileReader(path.toFile()));
    this.writer = new PrintWriter(new FileWriter(path.toFile(), true));
    this.append = true;
    }




    @Override
    public String read() throws IOException{
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
    public void write(final String data) {
        writer.append(data).flush();
    }

    @Override
    public void close() throws Exception {
        reader.close();
        writer.close();
    }


}
