package utility;

import handlers.Router;
import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializators.Deserializator;
import serializators.Serializator;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

public class Server {
    private final InetSocketAddress address;
    private Selector selector;
    private final Logger logger;
    private Response response;
    private ServerSocketChannel serverChannel;
    private Deserializator deserializator = new Deserializator();
    private Serializator serializator = new Serializator();


    public Server(int address) {
        this.address = new InetSocketAddress(address);
        logger = LoggerFactory.getLogger(Server.class);
    }

    private void init() throws IOException {
        CollectionManager.getInstance().load();
        selector = Selector.open();
        logger.info("Селектор открыт");
        serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        serverChannel.bind(address);
        logger.info("Канал сервера готов к работе");
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

    }

    public void run() {
        try {
            this.init();
            while (true) {
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> keys = selectedKeys.iterator();
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
                        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

                    }
                    keys.remove();
                }
            }
        } catch (ClassNotFoundException e) {
            logger.error("Несоответствие классов: {}", (Object) e.getStackTrace());
        } catch (IOException e) {
            logger.error("Ошибка ввода-вывода" + e.getMessage());
        } catch (NoSuchElementException e) {
            logger.error("Сервер остановлен");
            Router.getInstance().route(new Request("save"));
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }


    private void acceptConnection(SelectionKey key) {
        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
        try {
            SocketChannel clientChannel = serverChannel.accept();
            logger.info("Установлено соединение с клиентом {}", clientChannel.socket().toString());
            clientChannel.configureBlocking(false);
            clientChannel.register(selector, SelectionKey.OP_READ);
        } catch (IOException e) {
            logger.info("Хуйня в аксепте" + e.getMessage());
        }
    }

    private Response receiveRequest(SelectionKey key) throws IOException, ClassNotFoundException {
        ByteBuffer clientData = ByteBuffer.allocate(222048);
        SocketChannel clientChannel = (SocketChannel) key.channel();
        try {
            clientChannel.configureBlocking(false);
            logger.info("{} байт пришло от клиента", clientChannel.read(clientData));
//            Request request = (Request) deserializator.deserialize(clientData.array());
            System.out.println(deserializator.deserialize(clientData.array()));
//            response = Router.getInstance().route(request);
//            logger.info("Запрос {}{}{} успешно обработан", request.command(), request.arg(), request.worker());
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (StreamCorruptedException e) {
            System.out.println("StreamCorruptedException");
            key.cancel();
        }
        return response;
    }

    private void sendResponse(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        try (ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream clientDataOutput = new ObjectOutputStream(bytes)) {
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

