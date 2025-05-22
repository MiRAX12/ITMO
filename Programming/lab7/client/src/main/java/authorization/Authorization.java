package authorization;

import Network.User;
import Network.UserBuilder;
import сlient.Client;

import java.util.Scanner;

public class Authorization {
    private final User user = new UserBuilder().createUser();
    private final Client client;
    private final Scanner scanner;
    boolean isAuthorized = false;


    public Authorization(Scanner scanner, User user, Client client) {
        this.scanner = scanner;
        this.client = client;
    }

    public void authorize() {
        while (!isAuthorized) {
            System.out.println("Введите 1 login или 2 reg");
            var input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    LogIn logIn = new LogIn(scanner, client);
                    logIn.logIn(user);
                    isAuthorized = true;
                    break;
                case "2":
                    Registration registration = new Registration(scanner, client);
                    registration.signUp(user);
                    isAuthorized = true;
                    break;
                default:
                    this.isAuthorized = false;
                    break;
            }
        }
    }
}
