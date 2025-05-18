package utility;

import Network.RequestBuilder;
import Network.Response;
import Network.User;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class LogIn {
    Scanner scanner;

    public LogIn(Scanner scanner) {
        scanner = scanner;
    }

    public void logIn(User user) throws ClassNotFoundException {
        var username = "";
        var password = "";
        User localUser;
        System.out.println("Введите логин. Чтобы зарегистрироваться, введите z");
        String input = scanner.nextLine().trim();
        if (input.equalsIgnoreCase("z")) {
            registerUser(scanner);

        } else {
            username = input;
            localUser = new User(username, null);
            client.sendToServer(new RequestBuilder().setUser(localUser).setRegisterRequired(false).build());
            if (client.receiveFromServer().getMessage().equals("Wrong")) {
                System.out.println("Такого пользователя нет");
                throw new NoSuchElementException();
            }

            password = scanner.nextLine().trim();
            localUser.setPassword(password);

            client.sendToServer(new RequestBuilder().setUser(user).setRegisterRequired(false).build());
            Response response = client.receiveFromServer();

            if (response.isUserAuthenticated()) {
                printResponse(response);
            } else {
                printResponse(response);
            }
        }
    }

}
