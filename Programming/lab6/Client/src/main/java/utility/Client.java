package utility;

import serializators.Deserializator;
import serializators.Serializator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class Client {
    private String host;
    private int port;
    private SocketAddress address;
    private SocketChannel channel;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() {
        try {
            this.address = new InetSocketAddress(host, port);
            this.channel = SocketChannel.open();
            System.out.println("Подключение...");
            channel.connect(address);
            System.out.println("Подключено к серверу " + host + ":" + port);
        } catch (IOException e) {
            System.out.println("Не удалось подключиться, " +
                    "сервер выключен или указаны неверные данные");
        }
    }

    public void sendToServer(Request request) throws IOException {
        Serializator serializator = new Serializator(request);
        byte[] serializedObject = serializator.serialize();
        ByteBuffer buffer = ByteBuffer.wrap(serializedObject);
        channel.write(buffer);
    }

    public void receiveFromServer() throws IOException, ClassNotFoundException {
            ByteBuffer dataToReceiveLength = ByteBuffer.allocate(32);
            channel.read(dataToReceiveLength);
            dataToReceiveLength.flip();
            int responseLength = dataToReceiveLength.getInt();
            ByteBuffer dataToReceive = ByteBuffer.allocate(responseLength);
            channel.read(dataToReceive);
            Deserializator deserializator = new Deserializator(dataToReceive.array());
            Response response = (Response) deserializator.deserialize();
            System.out.println(response.getMessage());
            if (!response.getWorkers().isEmpty()) {
                System.out.println(response.getWorkers());
            }
    }
}
