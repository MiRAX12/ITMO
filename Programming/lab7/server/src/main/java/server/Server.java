package server;

import Network.*;
//import commands.Save;
import database.Database;
import model.Worker;
import router.Router;
import managers.CollectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serializators.Deserializator;
import serializators.Serializator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    private final int port;
    private ServerSocket serverSocket;
    private final Logger logger;
    private final Deserializator deserializator = new Deserializator();
    private final Serializator serializator = new Serializator();
    private static final ExecutorService forkPool = Executors.newWorkStealingPool();
    private static final ExecutorService fixedPool = Executors.newCachedThreadPool();

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
                    if (currentInput.equals("exit")) {
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
            forkPool.execute(() -> {
                try {
                    while (!clientSocket.isClosed() && input != null && output != null) {
                        try {
                            byte[] clientData = new byte[1024 * 1024 * 10];
                            int bytes = input.read(clientData);
                            Request request = (Request) deserializator.deserialize(clientData);
                            forkPool.execute(() -> {
                                Response response;
                                if (request.getCommand() == null) {
                                    response = handleUser(request);
                                } else response = Router.getInstance().route(request);

                                fixedPool.execute(() -> {
                                    byte[] serializedResponse = serializator.serialize(response);
                                    if (serializedResponse.length > 0) {
                                        try {
                                            synchronized (output) {
                                                output.write(serializedResponse);
                                                output.flush();
                                            }
                                        } catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        logger.trace("Запрос {} успешно обработан", request.getCommand());
                                    } else {
                                        logger.error("Получен ответ с нулевой длиной");
                                    }
                                });
                            });
                        } catch (ClassNotFoundException e) {
                            logger.error("Несоответствие классов: {}", (Object) e.getStackTrace());
                        } catch (IOException e) {
                            logger.info("Клиент {} отключился от сервера", clientSocket.getRemoteSocketAddress());
                            System.out.println("Клиент " + clientSocket.getRemoteSocketAddress() + " отключился от сервера");
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
            });
        }

        private Response handleUser(Request request) {
            if (request.getUser() != null && request.getCommand() == null && request.getWorker() == null) {
                User user = request.getUser();
                String username = user.getUsername();
                String password = user.getPassword();
                if (user.getStatus().equals("login")) {
                    if (username != null && !username.isEmpty()) {
                        boolean isUserExists = Database.checkUserExistence(username);
                        if (password != null && !password.isEmpty()) {
                            System.out.println(password);
                            if (Database.checkUserPassword(user)) {
                                return new Response("ACCEPT");
                            } else {
                                return new Response("WRONG");
                            }
                        }
                        if (isUserExists) {
                            return new Response("OK");
                        } else {
                            return new Response("WRONG");
                        }
                    }
                } else if (user.getStatus().equals("signup")) {
                    if (Database.checkUserExistence(username)) {
                        return new Response("IS EXIST");
                    }
                    User user1 = request.getUser();
                    String salt = PasswordHasher.generateSalt();
                    user1.setSalt(salt);
                    Database.addUser(user1);
                    System.out.println("Пользователь добавлен в базу данных");
                    return new Response("ACCEPT");
                }
            }
//                Da
//                User user = this.userService.getUserByUsername(this.request.getUser().getUsername());
//                String password = this.request.getUser().getPassword();
//                String salt = user.getSalt();
//                String hashedPassword = Hash.hash(password, salt);
//                if (user.getPassword().equals(hashedPassword)) {
//                    return new Response("ACCEPT");
//                } else {
//                    return new Response("WRONG");
//                }
            return new Response("WRONG");
        }
    }
}

//            Response response;
//            var user = request.getUser();
//            var password = request.getUser().getPassword();
//            if (!request.userRegisterRequired()) {
//                if (Database.checkUserExistence(user.getUsername())) {
//                    if (password != null) return authenticate(user);
//                    response = new Response("Введите пароль");
//
//                } else {
//                    response = new Response("Wrong", false);
//                    logger.info("Пользователя " + user.getUsername() + " не существует");
//                }
//                return response;
//
//            } else {
//                if (Database.checkUserExistence(user.getUsername())) {
//                    response = new Response("Такой логин уже занят", false);
//
//                } else {
//                    Database.addUser(user);
//                    response = new Response("Пользователь " + user.getUsername() +
//                            " успешно зарегистрирован", true);
//                    logger.info("Пользователь " + user.getUsername() + " успешно зарегистрирован");
//                }
//                return response;
//            }
//        }

//        private Response authenticate(User user) {
//            Response response;
//            if (Database.checkUserPassword(user)) {
//                response = new Response("Приветствую, " + user.getUserName() + "\n", true);
//                logger.info("Пользователь " + user.getUserName() + " аутентифицирован");
//
//            } else {
//                response = new Response("Пароль введён неверно", false);
//                logger.info("Пользователь " + user.getUserName() + " неверно ввёл пароль");
//            }
//            return response;
//        }
//    }





