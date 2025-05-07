package serializators;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Deserializator {

    public Deserializator() {
    }

    public Object deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        try(ByteArrayInputStream by = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(by)) {
            return in.readObject();
        }
    }
}
