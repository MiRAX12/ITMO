package serializators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializator {


    public Serializator() {

    }

    public byte[] serialize(Serializable obj) {
        try(ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes)) {

            out.writeObject(obj);

            return bytes.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
