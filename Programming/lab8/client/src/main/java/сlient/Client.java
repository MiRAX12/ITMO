package сlient;

import model.Worker;
import network.*;
import serializators.Deserializator;
import serializators.Serializator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Client {
    private static final Client INSTANCE = new Client();
    private final Map<Long, Worker> collection = new ConcurrentHashMap<>();

    private Client() {
        try {
            init();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Client getInstance() {
        return INSTANCE;
    }

    private String host = "localhost";
    private int port = 5505;
    private SocketAddress address;
    private SocketChannel channel;
    private User user;
    private static int recursionCount = 0;
    private static final int MAX_RECURSION = 3;
    private long lastReconnectAttempt = 0;
    private static final long RECONNECT_DELAY_MS = 5000;

//    public Client(String host, int port) {
//        this.host = host;
//        this.port = port;
//    }

    public void init() throws IOException {
        while (recursionCount <= MAX_RECURSION) {
            try {
                recursionCount++;
                this.address = new InetSocketAddress(host, port);
                this.channel = SocketChannel.open();
                lastReconnectAttempt = System.currentTimeMillis();
                channel.socket().connect(address, 5000);
                channel.configureBlocking(false);

                if (!channel.isConnected()) {
                    throw new IOException("Не удалось установить соединение");
                }
                recursionCount = 0;
                break;
            } catch (Exception e) {
                closeChannel();
                System.out.println("Не удалось подключиться, сервер выключен или указаны неверные данные." +
                        " Повторная попытка через 3 секунды...");
                if (recursionCount < MAX_RECURSION) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                } else {
                    System.out.println("Превышено максимальное количество попыток подключения");
                    throw e;
                }
            }
        }
    }

    public boolean reconnect() {
        closeChannel();
        try {
            init();
            recursionCount = 0;
            return true;
        } catch (IOException e) {
            System.out.println("Введите любой символ, чтобы повторить попытку или exit, чтобы закрыть клиент");
            return false;
        }
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

    public void sendToServer(Request request) {
        try {
            Serializator serializator = new Serializator();
            byte[] serializedObject = serializator.serialize(request);
            long startTime = System.currentTimeMillis();
            final int TIMEOUT_MS = 5000;
            ByteBuffer buffer = ByteBuffer.wrap(serializedObject);
            while (buffer.hasRemaining()) {
                int bytesWritten = channel.write(buffer);
                if (bytesWritten == -1) {
                    throw new IOException("Сервер закрыл соединение");
                }
                if (bytesWritten == 0) {
                    if (System.currentTimeMillis() - startTime > TIMEOUT_MS) {
                        throw new SocketTimeoutException("Таймаут отправки данных");
                    }
                }
            }

        } catch (SocketTimeoutException e) {
            System.out.println("Сервер не ответил за " + 5000 + "мс");
        } catch (IOException e) {
            long now = System.currentTimeMillis();
            if ((now - lastReconnectAttempt) > RECONNECT_DELAY_MS) {
                System.out.println("Ошибка отправки данных. Попытка переподключения...");
                recursionCount = 0;
                reconnect();
                if (reconnect()) {
                    sendToServer(request);
                }
            } else System.out.println("Слишком частые попытки переподключения. Попробуйте позже");
        }
    }

    public Response receiveFromServer() throws ClassNotFoundException {
        Response response = null;
        try {
            ByteBuffer dataBuffer = ByteBuffer.allocate(1024 * 1024 * 10);
            long startTime = System.currentTimeMillis();
            final int TIMEOUT_MS = 5000;

            while (true) {
                int bytes = channel.read(dataBuffer);

                if (bytes > 0) {
                    dataBuffer.flip();
                    byte[] responseData = new byte[dataBuffer.remaining()];
                    dataBuffer.get(responseData);

                    response = (Response) new Deserializator().deserialize(responseData);
                    return response;
                } else if (bytes == 0) {
                    if (System.currentTimeMillis() - startTime > TIMEOUT_MS) {
                        throw new SocketTimeoutException("Таймаут ожидания данных");
                    }
                } else if (bytes == -1) {
                    throw new IOException("Соединение закрыто сервером");
                }
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Время ожидания ответа истекло");
        } catch (IOException e) {
            if (recursionCount < MAX_RECURSION) {
                System.out.println("Ошибка получения данных: сервер не отвечает. Попытка переподключения...");
                reconnect();
                if (reconnect()) {
                    System.out.println("Подключено к серверу " + host + ":" + port);
                    receiveFromServer();
                }
            }
        }
        return response;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
