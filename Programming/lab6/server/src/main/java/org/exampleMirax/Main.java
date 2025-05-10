package org.exampleMirax;//import Commands.Add;

import server.Server;

import java.io.IOException;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax

 */
public class Main {

    private static final int PORT = 5505;
    private static final int BUFFER_SIZE = 1024;

    public static void main(String[] args) throws IOException {

        Server server = new Server(5505);
        server.run();
    }
//        try (Selector selector = Selector.open();
//             ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
//
//            // Настраиваем серверный сокет
//            serverSocketChannel.bind(new InetSocketAddress(PORT));
//            serverSocketChannel.configureBlocking(false); // Неблокирующий режим
//
//            // Регистрируем серверный канал в селекторе для ACCEPT событий
//            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
//            System.out.println("Сервер запущен на порту " + PORT);
//
//            while (true) {
//                // Блокируемся, пока не появятся события
//                int readyChannels = selector.select();
//                if (readyChannels == 0) continue;
//
//                // Получаем ключи с событиями
//                Set<SelectionKey> selectedKeys = selector.selectedKeys();
//                Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
//
//                while (keyIterator.hasNext()) {
//                    SelectionKey key = keyIterator.next();
//                    keyIterator.remove(); // Удаляем ключ, чтобы не обрабатывать повторно
//
//                    if (key.isAcceptable()) {
//                        // Обработка нового подключения
//                        handleAccept(key, selector);
//                    } else if (key.isReadable()) {
//                        // Чтение данных от клиента
//                        handleRead(key);
//                    } else if (key.isWritable()) {
//                        // Запись данных клиенту (если нужно)
//                        handleWrite(key);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void handleAccept(SelectionKey key, Selector selector) throws IOException {
//        ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
//        SocketChannel clientChannel = serverChannel.accept();
//        clientChannel.configureBlocking(false); // Неблокирующий режим
//
//        // Регистрируем клиентский канал для чтения
//        clientChannel.register(selector, SelectionKey.OP_READ);
//        System.out.println("Подключился клиент: " + clientChannel.getRemoteAddress());
//    }
//
//    private static void handleRead(SelectionKey key) throws IOException {
//        SocketChannel clientChannel = (SocketChannel) key.channel();
//        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
//
//        int bytesRead = clientChannel.read(buffer);
//        if (bytesRead == -1) { // Клиент отключился
//            System.out.println("Клиент отключился: " + clientChannel.getRemoteAddress());
//            clientChannel.close();
//            return;
//        }
//
//        buffer.flip(); // Переключаем буфер в режим чтения
//        byte[] data = new byte[buffer.remaining()];
//        buffer.get(data);
//        String message = new String(data);
//        System.out.println("Получено от клиента: ");
//
//        // Можно зарегистрировать канал для записи, если нужно отправить ответ
//        key.interestOps(SelectionKey.OP_WRITE);
//    }
//
//    private static void handleWrite(SelectionKey key) throws IOException {
//        SocketChannel clientChannel = (SocketChannel) key.channel();
//        String response = "хуй";
//        ByteBuffer buffer = ByteBuffer.wrap(response.getBytes());
//
//        clientChannel.write(buffer);
//        System.out.println("Отправлен ответ клиенту: " + clientChannel.getRemoteAddress());
//
//        // Возвращаем обратно в режим чтения (если сервер должен продолжать слушать)
//        key.interestOps(SelectionKey.OP_READ);
//    }
    }





