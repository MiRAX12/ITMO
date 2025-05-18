package server;

import Network.RequestBuilder;
import Network.User;
import commands.Save;
import database.Database;
import router.Router;
import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializators.Deserializator;
import serializators.Serializator;
import Network.Request;
import Network.Response;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


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
    }

    public void run() throws IOException {
        this.init();

        Thread consoleThread = new Thread(() -> {
            try (BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in))) {
                String currentInput;
                while ((currentInput = consoleReader.readLine()) != null) {
                    if (currentInput.equals("save")) {
                        Save save = new Save();
                        save.execute(new RequestBuilder().setCommand("save").build());
                    } else if (currentInput.equals("exit")) {
                        Save save = new Save();
                        save.execute(new RequestBuilder().setCommand("save").build());
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
            Response response;
            try {
                while (!clientSocket.isClosed() && input != null && output != null) {
                    try {
                        byte[] clientData = new byte[1024 * 1024 * 10];
                        int bytes = input.read(clientData);

                        Request request = (Request) deserializator.deserialize(clientData);
                        if (request.getCommand() == null) {
                            response = handleUser(request);
                        } else response = Router.getInstance().route(request);

                        byte[] serializedResponse = serializator.serialize(response);

                        if (serializedResponse.length > 0) {
                            output.write(serializedResponse);
                            output.flush();
                            logger.trace("Запрос {} успешно обработан", request.getCommand());
                        } else {
                            logger.error("Получен ответ с нулевой длиной");
                        }
                    } catch (ClassNotFoundException e) {
                        logger.error("Несоответствие классов: {}", (Object) e.getStackTrace());
                    } catch (IOException e) {
                        logger.info("Клиент {} отключился от сервера", clientSocket.getRemoteSocketAddress());
                        System.out.println("Клиент " + clientSocket.getRemoteSocketAddress() + " отключился от сервера");
                        Save save = new Save();
                        save.execute(new RequestBuilder().setCommand("save").build());
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

        private Response handleUser(Request request) {
            Response response;
            var user = request.getUser();
            var password = request.getUser().getPassword();
            if (!request.userRegisterRequired()) {
                if (Database.checkUserExistence(user.getName())) {
                    if (password != null) return authenticate(user);
                    response = new Response("Введите пароль");

                } else {
                    response = new Response("Wrong", false);
                    logger.info("Пользователя " + user.getName() + " не существует");
                }
                return response;

            } else {
                if (Database.checkUserExistence(user.getName())) {
                    response = new Response("Такой логин уже занят", false);

                } else {
                    Database.addUser(user);
                    response = new Response("Пользователь " + user.getName() +
                            " успешно зарегистрирован", true);
                    logger.info("Пользователь " + user.getName() + " успешно зарегистрирован");
                }
                return response;
            }
        }

        private Response authenticate(User user) {
            Response response;
            if (Database.checkUserPassword(user)) {
                response = new Response("Приветствую, " + user.getName() + "\n", true);
                logger.info("Пользователь " + user.getName() + " аутентифицирован");

            } else {
                response = new Response("Пароль введён неверно", false);
                logger.info("Пользователь " + user.getName() + " неверно ввёл пароль");
            }
            return response;
        }
    }
}
