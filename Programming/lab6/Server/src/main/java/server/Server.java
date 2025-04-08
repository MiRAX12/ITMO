package server;

import handlers.Router;
import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.Request;
import utility.Response;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Server {
    private final InetSocketAddress address;
    private Selector selector;
    private final Logger logger;
    private Response response;

    public Server(InetSocketAddress address) {
        this.address = address;
        logger = LoggerFactory.getLogger(Server.class);
    }

    private void init() throws IOException {
        CollectionManager.getInstance().load();
        selector = Selector.open();
        logger.info("Селектор открыт");
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(address);
        logger.info("Канал сервера готов к работе");
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public void run() throws IOException {
        try {
            this.init();
            while (true) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                logger.info("Итератор по ключам селектора получен");

                while (keys.hasNext()) {
                    SelectionKey key = keys.next();
                    logger.info("Началась обработка ключа");

                    try {
                        if (key.isValid()) {
                            if (key.isAcceptable()) {
                                acceptConnection(key);
                            }
                            if (key.isReadable()) {
                                response = receiveRequest(key);
                            }
                            if (key.isWritable()) {
                                sendResponse(key);
                            }
                        }
                    } catch (SocketException | CancelledKeyException e) {
                        logger.info("Клиент {} отключился", key.channel().toString());
                        Router.getInstance().route(new Request("save"));
                        key.cancel();
                    }
                    keys.remove();
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("Несоответствие классов: {}", e.getStackTrace());
        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода");
        } catch (NoSuchElementException e) {
            logger.error("Сервер остановлен");
            Router.getInstance().route(new Request("save"));
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    private void acceptConnection(SelectionKey key) throws IOException {
        try (ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();) {
            SocketChannel clientChannel = serverChannel.accept();
            logger.info("Установлено соединение с клиентом {}", clientChannel.socket().toString());
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
        }
    }

    private Response receiveRequest(SelectionKey key) throws IOException, ClassNotFoundException {
        ByteBuffer clientData = ByteBuffer.allocate(1024);
        try (ByteArrayInputStream bytes = new ByteArrayInputStream(clientData.array());
             ObjectInputStream clientDataInput = new ObjectInputStream(bytes);
             SocketChannel clientChannel = (SocketChannel) key.channel();) {
            clientChannel.configureBlocking(false);
            logger.info("{} байт пришло от клиента", clientChannel.read(clientData));
            Request request = (Request) clientDataInput.readObject();
            response = Router.getInstance().route(request);
            logger.info("Запрос {}{}{} успешно обработан", request.command(), request.arg(), request.worker());
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (StreamCorruptedException e) {
            System.out.println("StreamCorruptedException");
            key.cancel();
        }
        return response;
    }

    private void sendResponse(SelectionKey key) throws IOException {
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream clientDataOutput = new ObjectOutputStream(bytes);
             SocketChannel clientChannel = (SocketChannel) key.channel();) {
            clientChannel.configureBlocking(false);
            clientDataOutput.writeObject(response);
            ByteBuffer clientData = ByteBuffer.wrap(bytes.toByteArray());
            ByteBuffer dataLength = ByteBuffer.allocate(32).putInt(clientData.limit());
            dataLength.flip();
            clientChannel.write(dataLength);
            logger.info("Ответ длиной {} отправлен клиенту", dataLength);
            clientChannel.write(clientData);
            logger.info("Ответ отправлен клиенту");
            clientData.clear();
            clientChannel.register(selector, SelectionKey.OP_READ);
        }
    }
}
//
//    public void run() throws IOException {
//        ServerSocketChannel socketChannel = ServerSocketChannel.open();
//        socketChannel.bind(new InetSocketAddress(port));
//            System.out.println("accepting connection");
//            SocketChannel clientChannel = socketChannel.accept();
//
//            System.out.println("Принято новое подключение:" +
//                    " " + clientChannel.getRemoteAddress());
//            bufferRead();
//
//            bufferWrite();
//
//    }
//
//    public void bufferRead () throws IOException { //TODO ошибки
//        channel.read(buffer);
//        System.out.println("hello world");
//    }
//
//    public void bufferWrite () throws IOException {
//        buffer.flip();
//        byte[] dataBytes = new byte[buffer.remaining()];
//        buffer.get(dataBytes);
//
//        for (int j = 0; j<dataBytes.length; j++){
//            dataBytes[j] *=2;
//        }
//
//        channel.write(ByteBuffer.wrap(dataBytes));
//    }

