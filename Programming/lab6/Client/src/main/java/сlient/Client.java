package сlient;

import serializators.Deserializator;
import serializators.Serializator;
import utility.Request;
import utility.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
    private String host;
    private int port;
    private SocketAddress address;
    private SocketChannel channel;
    
    private static final int MAX_RESPONSE_SIZE = 100 * 1024 * 1024; 
    private static final int INT_SIZE = 4; 
    private static int recursionCount = 0; 
    private static final int MAX_RECURSION = 3; 

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void init() {
        while (true) {
            try {
                this.address = new InetSocketAddress(host, port);
                this.channel = SocketChannel.open();
                System.out.println("Подключение...");
                channel.connect(address);
                System.out.println("Подключено к серверу " + host + ":" + port);
                break;
            } catch (IOException e) {
                System.out.println("Не удалось подключиться, сервер выключен или указаны неверные данные. Повторная попытка через 3 секунды...");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public void reconnect() {
        closeChannel();
        init();
    }

    private void closeChannel() {
        if (channel != null && channel.isOpen()) {
            try {
                channel.close();
            } catch (IOException e) {
                System.out.println("Ошибка при закрытии канала: " + e.getMessage());
            }
        }
    }

    public void sendToServer(Request request) throws IOException {
        try {
            Serializator serializator = new Serializator();
            byte[] serializedObject = serializator.serialize(request);
            ByteBuffer buffer = ByteBuffer.wrap(serializedObject);
            channel.write(buffer);
        } catch (IOException e) {
            System.out.println("Ошибка отправки данных. Попытка переподключения...");
            reconnect();
            if (recursionCount < MAX_RECURSION) {
                recursionCount++;
                sendToServer(request);
                recursionCount--;
            } else {
                System.out.println("Превышено максимальное количество попыток отправки данных.");
                throw e;
            }
        }
    }

    public void receiveFromServer() throws IOException, ClassNotFoundException {
        try {
            ByteBuffer lengthBuffer = ByteBuffer.allocate(INT_SIZE);
            channel.read(lengthBuffer);
            lengthBuffer.flip();
            int responseLength = lengthBuffer.getInt();
            
            if (responseLength <= 0 || responseLength > MAX_RESPONSE_SIZE) {
                throw new IOException("Получена некорректная длина ответа: " + responseLength);
            }
            
            
            ByteBuffer dataBuffer = ByteBuffer.allocate(responseLength);
            channel.read(dataBuffer);
            
            dataBuffer.flip();
            byte[] responseData = new byte[responseLength];
            dataBuffer.get(responseData);
            
            Deserializator deserializator = new Deserializator();
            Response response = (Response) deserializator.deserialize(responseData);
            System.out.println(response.getMessage());
            if (!response.getWorkers().isEmpty()) {
                System.out.println(response.getWorkers());
            }
        } catch (IOException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage() + ". Попытка переподключения...");
            reconnect();
            
            if (recursionCount < MAX_RECURSION) {
                recursionCount++;
                receiveFromServer();
                recursionCount--;
            } else {
                System.out.println("Превышено максимальное количество попыток получения данных.");
                throw e;
            }
        }
    }
}
