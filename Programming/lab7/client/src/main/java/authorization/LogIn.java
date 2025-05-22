package authorization;

import Network.Request;
import Network.Response;
import Network.User;
import exceptions.EmptyValueException;
import exceptions.IncorrectStringValueException;

import java.io.IOException;
import java.util.Scanner;

public class LogIn {
    Scanner scanner;

    public LogIn(Scanner scanner) {
        scanner = scanner;
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
                if (currentInput.matches(".*\\d.*")) {
                    throw new IncorrectStringValueException();
                }
                isPass = true;
            } catch (IncorrectStringValueException e) {
                System.out.println(e.getMessage());
            } catch (EmptyValueException e) {
                System.out.println(e.getMessage());
            }
        } while(false == isPass);
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
            }
        } while(false == isPass);
        return currentInput;
    }

    public void logIn(User user) {
//        try {
//            this.client.open();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        boolean pass = false;
        do {
            try {
                this.username = this.askUsername();
                Request request = new Request();
                user.setUserName(this.username);
                user.("login");
                request.setUser(user);
                this.client.sendRequest(request);
                Response response = (Response) this.client.receiveResponse(5000);
                if (response != null && response.getMessage().equals("OK")) {
                    this.password = this.askPassword();
                    user.setPassword(this.password);
                    request.setUser(user);
                    this.client.sendRequest(request);
                    response = (Response) this.client.receiveResponse(5000);
                    if(response != null && response.getMessage().equals("ACCEPT")) {
                        System.out.println("Вход успешно выполнен");
                        pass = true;
                        break;
                    }
                } else {
                    System.out.println("Пользователя с таким именем не существует");
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
        while(pass == false);
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

}
