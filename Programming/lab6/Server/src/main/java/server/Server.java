package server;

import commands.Save;
import router.Router;
import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializators.Deserializator;
import serializators.Serializator;
import utility.Request;
import utility.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.nio.ByteBuffer;

public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private final Logger logger;
    private Deserializator deserializator = new Deserializator();
    private Serializator serializator = new Serializator();

    public Server(int port) {
        this.port = port;
        logger = LoggerFactory.getLogger(Server.class);
    }

    private void init() throws IOException {
        CollectionManager.getInstance().load();
        serverSocket = new ServerSocket(port);
        logger.info("Сервер запущен на порту " + port);
        System.out.println("Сервер запущен на порту " + port);
    }

    public void run() throws IOException {
        this.init();

        Thread consoleThread = new Thread(() -> {
            try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                String currentInput;
                while ((currentInput = consoleReader.readLine()) != null) {
                    if (currentInput.equals("save")) {
                        Save save = new Save();
                        save.execute(new Request("save"));
                    } else if (currentInput.equals("exit")) {
                        Save save = new Save();
                        save.execute(new Request("save"));
                        System.exit(0);
                    }
                }
            } catch (IOException e) {
                logger.error("Ошибка чтения с консоли: " + e.getMessage());
            }
        });
        consoleThread.setDaemon(true);
        consoleThread.start();

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();
                logger.info("Установлено соединение с клиентом " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                System.out.println("Установлено соединение с клиентом " + clientSocket.getRemoteSocketAddress());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            } catch (IOException e) {
                logger.error("Ошибка при подключении клиента: " + e.getMessage());
            }
        }
    }

    private class ClientHandler implements Runnable {
        private final Socket clientSocket;
        private InputStream input;
        private OutputStream output;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
            try {
                output = clientSocket.getOutputStream();
                output.flush();
                input = clientSocket.getInputStream();
            } catch (IOException e) {
                logger.error("Ошибка создания потоков ввода/вывода: " + e.getMessage());
            }
        }

        @Override
        public void run() {
            try {
                while (!clientSocket.isClosed() && input != null && output != null) {
                    try {
                        byte[] clientData = new byte[1024*1024*10];
                        int bytes = input.read(clientData);

                            Request request = (Request) deserializator.deserialize(clientData);
                            Response response = Router.getInstance().route(request);
                            byte[] serializedResponse = serializator.serialize(response);
                            
                            if (serializedResponse.length > 0) {
                                output.write(serializedResponse);
                                output.flush();
                                logger.trace("Запрос {} успешно обработан", request.getCommand());
                            } else {
                                logger.error("Получен ответ с нулевой длиной");
                            }

                    } catch (SocketException e) {
                        logger.info("Клиент {} отключился от сервера", clientSocket.getRemoteSocketAddress());
                        System.out.println("Клиент " + clientSocket.getRemoteSocketAddress() + " отключился от сервера");
                        Save save = new Save();
                        save.execute(new Request("save"));
                        break;
                    } catch (ClassNotFoundException e) {
                        logger.error("Несоответствие классов: {}", (Object) e.getStackTrace());
                    } catch (IOException e) {
                        logger.error("IO Ошибка: " + e.getMessage());
                        break;
                    }
                }
            } finally {
                try {
                    if (input != null) {
                        input.close();
                    }
                    if (output != null) {
                        output.close();
                    }
                    if (!clientSocket.isClosed()) {
                        clientSocket.close();
                    }
                } catch (IOException e) {
                    logger.error("Ошибка закрытия соединения: " + e.getMessage());
                }
            }
        }
    }
}
