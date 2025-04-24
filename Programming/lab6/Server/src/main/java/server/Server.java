package server;

import handlers.Router;
import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializators.Deserializator;
import serializators.Serializator;
import utility.Request;
import utility.Response;

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
            logger.error("IO Ошибка: " + e.getMessage());
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
            logger.info("Ошибка: " + e.getMessage());
        }
    }

    private Response receiveRequest(SelectionKey key) throws IOException, ClassNotFoundException {
        ByteBuffer clientData = ByteBuffer.allocate(222048);
        SocketChannel clientChannel = (SocketChannel) key.channel();
        try {
            clientChannel.configureBlocking(false);
            logger.info("{} байт пришло от клиента", clientChannel.read(clientData));
            Request request = (Request) deserializator.deserialize(clientData.array());
            response = Router.getInstance().route(request);
            logger.info("Запрос {}{}{} успешно обработан", request.getCommand(), request.getArg(), request.getWorker());
            clientChannel.register(selector, SelectionKey.OP_WRITE);
        } catch (StreamCorruptedException e) {
            System.out.println("StreamCorruptedException");
            key.cancel();
        }
        return response;
    }

    private void sendResponse(SelectionKey key) throws IOException {
        SocketChannel clientChannel = (SocketChannel) key.channel();
        byte[] serializedData = serializator.serialize(response);
        clientChannel.configureBlocking(false);
        ByteBuffer clientData = ByteBuffer.wrap(serializedData);
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
