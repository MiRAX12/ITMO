package serializators;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Serializator {


    public Serializator() {

    }

    public byte[] serialize(Serializable obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Объект для сериализации не может быть null");
        }
        
        try(ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bytes)) {

            out.writeObject(obj);
            out.flush();
            
            byte[] result = bytes.toByteArray();
            if (result.length == 0) {
                throw new IOException("Сериализация привела к пустому массиву байтов");
            }
            
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сериализации объекта: " + e.getMessage(), e);
        }
    }
}
