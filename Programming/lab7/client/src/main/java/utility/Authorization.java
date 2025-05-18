package utility;

import Network.User;

import java.util.Scanner;

public class Authorization {
    private User user;
    private Scanner scanner;
    private final LogIn logIn = new LogIn(scanner);
    private final Registration registration = new Registration(scanner);
    boolean isAuthorized = false;

    public Authorization(Scanner scanner, User user) {
        this.user = user;
        this.scanner = scanner;
    }

    public void authorize(Scanner scanner) {
        while (!isAuthorized) {
            System.out.println("Введите 1 login или 2 reg");
            var input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    logIn.logIn(user);
                    isAuthorized = true;
                    break;
                case "2":
                    registration.registerUser(user);
                    isAuthorized = true;
                    break;
                default:
                    this.isAuthorized = false;
                    break;
            }
        }
    }
}
