package client.serializators;

import java.io.*;

public class Serializator {
    Serializable obj;

    public Serializator(Serializable obj) {
        this.obj = obj;
    }

    public byte[] serialize() {
        try(ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes)) {

            out.writeObject(obj);

            return bytes.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
