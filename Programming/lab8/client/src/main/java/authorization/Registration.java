package authorization;

import network.*;
import exceptions.EmptyValueException;
import сlient.Client;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Registration extends AuthForm {
    private Scanner scanner;
    private String status;
    private Client client;

    public Registration(Scanner scanner, Client client) {
        this.scanner = scanner;
        this.client = client;
    }

    public Registration() {
        super();
    }

    public String askUsername() {
        boolean isPass = false;
        String currentInput = null;
        do {
            try {
                System.out.println("Введите имя пользователя или пустое значение, чтобы вернуться в меню:\n-> ");
                currentInput = scanner.nextLine().trim();
                User user = new User();
                user.setUsername(currentInput);
                user.setStatus("signup");
                Request request = new RequestBuilder().setUser(user).build();
                this.client.sendToServer(request);
                Response response = (Response) this.client.receiveFromServer();
                if (response != null && response.getMessage().equals("IS EXIST")) {
                    System.out.println("Имя занято");
                    continue;
                } else isPass = true;
                if (currentInput.isEmpty()) {
                    throw new EmptyValueException();
                }
            } catch (NoSuchElementException e) {
                System.out.println("Программа завершает работу");
                System.exit(0);
            } catch (EmptyValueException e) {
                System.out.println("Возврат в меню выбора");
            } catch (Exception e) {
                System.out.println(e.getMessage());
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
                System.out.println("Ввод не может быть пустым");
            } catch (NoSuchElementException e) {
                System.out.println("Программа завершает работу");
                System.exit(0);
            }
        } while (!isPass);
        return currentInput;
    }

    public void signUp(User user) {
        boolean pass = false;
            try {
                if (user.getUsername() == null) this.username = this.askUsername();
                if (user.getPassword() == null) this.password = this.askPassword();
//                user.setUsername(this.username);
//                user.setPassword(this.password);
                user.setStatus("signup");
                Request request = new RequestBuilder().setUser(user).build();
                this.client.sendToServer(request);
                Response response = (Response) this.client.receiveFromServer();
                if (response != null && response.getMessage().equals("ACCEPT")) {
                    System.out.println("Аккаунт успешно создан");
                    status = "Success";
                    pass = true;
                } else if (response != null && response.getMessage().equals("IS EXIST")) {
                    System.out.println("Имя занято");
                    status = "IsExist";
                }

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

    }

    public String getStatus(){
        return status;
    }
}
