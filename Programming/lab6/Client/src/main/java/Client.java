package client;

import client.serializators.Deserializator;
import client.serializators.Serializator;
import common.Request;
import common.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
//    private byte[] arr;
//    private int len;
    private String host;
    private int port;
    private SocketAddress address;
    private SocketChannel channel;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() throws IOException {
//        this.arr = new byte[]{2, 32, 5};
//        this.len = arr.length;
        this.address = new InetSocketAddress(host, port);
        this.channel = SocketChannel.open();
        channel.connect(address);
        System.out.println("connected to " + host + ":" + port); //TODO подумай над выводом
    }

    public void sendToServer(Request request) throws IOException {
        Serializator serializator = new Serializator(request);
        byte[] serializedObject = serializator.serialize();
        ByteBuffer buffer = ByteBuffer.wrap(serializedObject);
        channel.write(buffer);
    }

    public void receiveFromServer() throws IOException, ClassNotFoundException {
        ByteBuffer dataToReceiveLength = ByteBuffer.allocate(32);
        channel.read(dataToReceiveLength); // читаем длину ответа от сервера
        dataToReceiveLength.flip();
        int responseLength = dataToReceiveLength.getInt(); // достаём её из буфера

        ByteBuffer dataToReceive = ByteBuffer.allocate(responseLength); // создаем буфер нужной нам длины
        channel.read(dataToReceive); // получаем ответ от сервера
        Deserializator deserializator = new Deserializator(dataToReceive.array());
        Response response = (Response) deserializator.deserialize();
        System.out.println(response.message());
        if(!response.workers().isEmpty()) {
            System.out.println(response.workers());
        }
    }
}
