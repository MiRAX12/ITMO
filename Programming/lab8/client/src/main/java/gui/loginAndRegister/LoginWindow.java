package gui.loginAndRegister;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LoginWindow {
    private Stage stage;
    private Map<String, Parent> cards = new HashMap<>();
    private LoginWindowController loginWindowController;
    private SignUpWindowController signUpWindowController;
    private int currentLocaleIndex = 0;

    public LoginWindow(Stage stage) {
        try {
            this.stage = stage;
            stage.setTitle("Collection");
            stage.setResizable(false);

            loadSignUp();
            loadLogin();

            Parent loginCard = cards.get("login");

            this.stage.setScene(new Scene(loginCard, 400, 500));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeCard(String cardName, int currentLocaleIndex) {
        if (cards.containsKey(cardName)) stage.getScene().setRoot(cards.get(cardName));
        stage.setTitle(cardName);
        this.currentLocaleIndex = currentLocaleIndex;
    }

    public void show() {
        stage.show();
    }

    private void loadLogin() throws IOException {
        stage.setTitle("Login");
        URL fxmlLocation = getClass().getResource("/loginAndRegister/loginWindow.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> {
            LoginWindowController controller = new LoginWindowController(this, stage, currentLocaleIndex);
            loader.setController(controller);
            return controller;
        });
        Parent loginCard = loader.load();
        cards.put("login", loginCard);
    }

    private void loadSignUp() throws IOException {
        stage.setTitle("signUp");
        URL fxmlLocation = getClass().getResource("/loginAndRegister/registerWindow.fxml");
        FXMLLoader loader = new FXMLLoader(fxmlLocation);
        loader.setControllerFactory(param -> {
            SignUpWindowController controller = new SignUpWindowController(this, stage,
                    currentLocaleIndex);
            loader.setController(controller);
            return controller;
        });
        Parent loginCard = loader.load();
        cards.put("signUp", loginCard);
    }


}