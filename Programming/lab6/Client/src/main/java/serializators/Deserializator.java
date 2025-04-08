package client.serializators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializator {
    byte[] bytes;

    public Deserializator(byte[] bytes) {
        this.bytes = bytes;
    }

    public Object deserialize() throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream by = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(by)) {
            return in.readObject();
        }
    }
}
