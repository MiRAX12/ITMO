package authorization;

import Network.Request;
import Network.RequestBuilder;
import Network.Response;
import Network.User;
import exceptions.EmptyValueException;
import exceptions.IncorrectStringValueException;
import сlient.Client;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LogIn extends AuthForm {
    private Scanner scanner;
    private Client client;

    public LogIn(Scanner scanner, Client client) {
        this.scanner = scanner;
        this.client = client;
    }

    public LogIn() {
        super();
    }

    public String askUsername() {
        boolean isPass = false;
        String currentInput = null;
        do {
            try {
                System.out.println("Введите имя пользователя:\n-> ");
                currentInput = scanner.nextLine().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                isPass = true;
            } catch (NoSuchElementException e) {
                System.out.println("Программа завершает работу");
                System.exit(0);
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            }
        } while (false == isPass);
        return currentInput;
    }

    public String askPassword() {
        boolean isPass = false;
        String currentInput = null;
        do {
            try {
                System.out.println("Введите пароль:\n-> ");
                currentInput = scanner.nextLine().trim();
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
                isPass = true;
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("Программа завершает работу");
                System.exit(0);
            }
        } while (false == isPass);
        return currentInput;
    }

    public void logIn(User user) {
        boolean pass = false;
        do {
            try {
                this.username = this.askUsername();
                user.setUsername(this.username);
                user.setStatus("login");
                Request request = new RequestBuilder().setUser(user).build();
                this.client.sendToServer(request);
                Response response = (Response) this.client.receiveFromServer();
                if (response != null && response.getMessage().equals("OK")) {
                    this.password = this.askPassword();
                    user.setPassword(this.password);
                    request.setUser(user);
                    this.client.sendToServer(request);
                    response = (Response) this.client.receiveFromServer();
                    if (response != null && response.getMessage().equals("ACCEPT")) {
                        pass = true;
                        break;
                    } else {
                        user.setPassword(null);
                        System.out.println("Неверный пароль");
                    }
                } else {
                    System.out.println("Пользователя с таким именем не существует");
                }
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        while (pass == false);
    }
//        var username = "";
//        var password = "";
//        User localUser;
//        System.out.println("Введите логин. Чтобы зарегистрироваться, введите z");
//        String input = scanner.nextLine().trim();
//        if (input.equalsIgnoreCase("z")) {
//            registerUser(scanner);
//
//        } else {
//            username = input;
//            localUser = new User(username, null);
//            client.sendToServer(new RequestBuilder().setUser(localUser).setRegisterRequired(false).build());
//            if (client.receiveFromServer().getMessage().equals("Wrong")) {
//                System.out.println("Такого пользователя нет");
//                throw new NoSuchElementException();
//            }
//
//            password = scanner.nextLine().trim();
//            localUser.setPassword(password);
//
//            client.sendToServer(new RequestBuilder().setUser(user).setRegisterRequired(false).build());
//            Response response = client.receiveFromServer();
//
//            if (response.isUserAuthenticated()) {
//                printResponse(response);
//            } else {
//                printResponse(response);
//            }
//        }
}

