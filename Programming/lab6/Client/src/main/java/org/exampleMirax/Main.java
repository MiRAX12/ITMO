package org.exampleMirax;//import Commands.Add;

import utility.Client;
import utility.Handler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Main class which creates a handler and runs it to handle
 * all following input
 *
 * @author Mirax
 * @see Handler
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Handler handler = new Handler();
        handler.run();


////        Client client = new Client();
////        client.init();
//        try (SocketChannel socketChannel = SocketChannel.open();
//             Scanner scanner = new Scanner(System.in)) {
//
//            // Подключаемся к серверу
//            socketChannel.configureBlocking(false); // Неблокирующий режим
//            socketChannel.connect(new InetSocketAddress(SERVER_HOST, SERVER_PORT));
//
//            // Ждем завершения подключения
//            while (!socketChannel.finishConnect()) {
//                System.out.println("Устанавливаем соединение с сервером...");
//                Thread.sleep(300);
//            }
//
//            System.out.println("Подключено к серверу " + SERVER_HOST + ":" + SERVER_PORT);
//            System.out.println("Введите сообщение (или 'exit' для выхода):");
//
//            while (true) {
//                String message = scanner.nextLine();
//                if ("exit".equalsIgnoreCase(message)) {
//                    break;
//                }
//
//                // Отправляем сообщение
//                ByteBuffer writeBuffer = ByteBuffer.wrap(message.getBytes(StandardCharsets.UTF_8));
//                socketChannel.write(writeBuffer);
//
//                // Читаем ответ
//                ByteBuffer readBuffer = ByteBuffer.allocate(BUFFER_SIZE);
//                int bytesRead = socketChannel.read(readBuffer);
//                if (bytesRead > 0) {
//                    readBuffer.flip();
//                    byte[] data = new byte[readBuffer.remaining()];
//                    readBuffer.get(data);
//                    String response = new String(data, StandardCharsets.UTF_8);
//                    System.out.println("Ответ сервера: " + response);
//                }
//            }
//        } catch (IOException | InterruptedException e) {
//            System.err.println("Ошибка в клиенте: " + e.getMessage());
//        }
//    }
    }
}


