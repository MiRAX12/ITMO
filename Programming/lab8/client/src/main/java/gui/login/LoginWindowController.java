package gui.login;

import authorization.LogIn;
import authorization.Registration;
import gui.AlertUtility;
import gui.UTF8Control;
import gui.collections.CollectionsWindow;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import network.User;
import utility.Handler;
import сlient.Client;


import java.util.*;


public class LoginWindowController {
    private ResourceBundle currentBundle;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label signUpLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label detailsLabel;

    @FXML
    private Label accountLabel;
    private final List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("hr"),
            new Locale("cs")
    );
    private int currentLocaleIndex = 0;

    @FXML
    public void initialize() {
        currentBundle = ResourceBundle.getBundle(
                "MessagesBundle",
                supportedLocales.get(currentLocaleIndex),
                new UTF8Control());
        updateUI();
    }

    /**
     * Update LoginWindow UI
     */
    private void updateUI() {
        accountLabel.setText(currentBundle.getString("accountLabel"));
        welcomeLabel.setText(currentBundle.getString("welcomeLabel"));
        detailsLabel.setText(currentBundle.getString("detailsLabel"));
        signInButton.setText(currentBundle.getString("signInButton"));
        signUpLabel.setText(currentBundle.getString("signUpLabel"));
        usernameLabel.setText(currentBundle.getString("usernameLabel"));
        passwordLabel.setText(currentBundle.getString("passwordLabel"));
    }

    @FXML
    protected void onGeoIconClick() {
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        currentBundle = ResourceBundle.getBundle("MessagesBundle", supportedLocales.get(currentLocaleIndex), new UTF8Control());
        updateUI();
    }

    @FXML
    protected void onDeleteButtonClick() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            User user = new User(username, password);

            Registration registration = new Registration(new Scanner(System.in), Client.getInstance());
            registration.signUp(user);

            if (registration.getStatus() == "Success") {
                AlertUtility.infoAlert("User successfully registered");
            } else if (registration.getStatus() == "IsExist") {
                AlertUtility.errorAlert("User already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @FXML
    protected void onSignInButtonClick() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            User user = new User(username, password);
            LogIn logIn = new LogIn(new Scanner(System.in), Client.getInstance());
            logIn.logIn(user);
            if (logIn.getStatus().equals("Success")) {
                Stage stage = (Stage) signInButton.getScene().getWindow();
                Client.getInstance().setUser(user);
                stage.close();

                CollectionsWindow collectionsWindow = new CollectionsWindow(currentLocaleIndex);
                collectionsWindow.show();
            } else if (logIn.getStatus().equals("NotExist")) {
                AlertUtility.errorAlert("There is no user with this name");
            } else {
                AlertUtility.errorAlert("password is incorrect");
            }
        } catch (Exception e) {
            //errorAlert("Server is dead :(");
        }
    }

    @FXML
    protected void onSignUpLabelClick() {
        try {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            User user = new User(username, password);

            Registration registration = new Registration(new Scanner(System.in), Client.getInstance());
            registration.signUp(user);

            if (registration.getStatus() == "Success") {
                AlertUtility.infoAlert("User successfully registered");
            } else if (registration.getStatus() == "IsExist") {
                AlertUtility.errorAlert("User already exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
