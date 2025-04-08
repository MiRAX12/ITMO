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
    private int port = 6505;
    private byte[] arr = new byte[10];
    private int len = arr.length;
    private ByteBuffer buffer = ByteBuffer.wrap(arr);

    public void init() throws IOException {
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(port));
        this.channel = server.accept();
    }

    public void bufferRead () throws IOException { //TODO ошибки
        channel.read(buffer);
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
