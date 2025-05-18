package utility;

import Network.PasswordHasher;
import Network.RequestBuilder;
import Network.Response;
import Network.User;

import java.util.Scanner;

public class Registration {
    Scanner scanner;

    public Registration(Scanner scanner) {
        scanner = scanner;
    }

    public boolean registerUser(User user) throws ClassNotFoundException {
        var username = "";
        var password = "";

        System.out.println("Введите логин: ");
        username = scanner.nextLine();

        System.out.println("Введите пароль: ");
        password = scanner.nextLine();

        String salt = PasswordHasher.generateSalt();
        user = new User(username, password, salt);

        var userAuthenticationRequest = new RequestBuilder().setUser(user).setRegisterRequired(true).build();
        client.sendToServer(userAuthenticationRequest);
        Response response = client.receiveFromServer();

        printResponse(response);
        return response.isUserAuthenticated();
    }
}
