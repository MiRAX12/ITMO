package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    private SocketAddress addr;
    private SocketChannel channel;
    private InetAddress host;
    private int port = 5505;
    private byte[] arr = new byte[10];
    private ByteBuffer buffer = ByteBuffer.wrap(arr);

    public void init() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();
        socketChannel.bind(new InetSocketAddress(port));
            System.out.println("accepting connection");
            SocketChannel clientChannel = socketChannel.accept();

            System.out.println("Принято новое подключение:" +
                    " " + clientChannel.getRemoteAddress());
            bufferRead();

            bufferWrite();

    }

    public void bufferRead () throws IOException { //TODO ошибки
        channel.read(buffer);
        System.out.println("hello world");
    }

    public void bufferWrite () throws IOException {
        buffer.flip();
        byte[] dataBytes = new byte[buffer.remaining()];
        buffer.get(dataBytes);

        for (int j = 0; j<dataBytes.length; j++){
            dataBytes[j] *=2;
        }

        channel.write(ByteBuffer.wrap(dataBytes));
    }
}
