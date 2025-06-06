package authorization;

import network.User;
import network.UserBuilder;
import сlient.Client;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Authorization {
    private final User user = new UserBuilder().createUser();
    private final Client client;
    private final Scanner scanner = new Scanner(System.in);
    boolean isAuthorized = false;


    public Authorization(Scanner scanner, User user, Client client) {
        this.client = client;
    }

    public User authorize() {
        try {
            while (!isAuthorized) {
                System.out.println("Введите 1 login или 2 reg");
                var input = scanner.nextLine().trim();
                switch (input) {
                    case "1":
                        LogIn logIn = new LogIn(scanner, client);
                        logIn.logIn(user);
                        isAuthorized = true;
                        return user;
                    case "2":
                        Registration registration = new Registration(scanner, client);
                        registration.signUp(user);
                        isAuthorized = true;
                        return user;
                    default:
                        this.isAuthorized = false;
                        break;
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Программа завершает работу");
            System.exit(0);
        }
        return null;
    }
}
