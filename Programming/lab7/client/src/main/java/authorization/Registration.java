package authorization;

import Network.*;
import exceptions.EmptyValueException;
import exceptions.IncorrectStringValueException;

import java.io.IOException;
import java.util.Scanner;

public class Registration extends AuthForm {
    Scanner scanner;
    private boolean isLogin = false;

    public Registration(Scanner scanner) {
        scanner = scanner;
    }

    public Registration() {
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
                throw new RuntimeException(e);
            }
        } while (!isPass);
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
                throw new RuntimeException(e);
            }
        } while (false == isPass);
        return currentInput;
    }

    public void signUp(User user) {
//        try {
//            this.client.open();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        boolean pass = false;
        do {
            try {
                this.username = this.askUsername();
                this.password = this.askPassword();
                Request request = new Request();
                user.setUserName(this.username);
                user.setPassword(this.password);
                user.setStatus("signup");
                request.se(user);
                this.client.sendRequest(request);
                Response response = (Response) this.client.receiveResponse(5000);
                if (response != null && response.getMessage().equals("ACCEPT")) {
                    System.out.println("Аккаунт успешно создан");
                    pass = true;
                    break;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        while (pass == false);
    }
}
