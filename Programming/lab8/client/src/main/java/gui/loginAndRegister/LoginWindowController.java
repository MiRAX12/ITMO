package gui.loginAndRegister;

import authorization.LogIn;
import gui.AlertUtility;
import gui.UTF8Control;
import gui.collections.CollectionsWindow;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import network.User;
import сlient.Client;


import java.util.*;


public class LoginWindowController {
    private final LoginWindow loginWindow;
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
    private String passwordError;

    @FXML
    private String usernameError;

    @FXML
    private String serverError;

    @FXML
    private Label welcomeLabel;

    @FXML
    private String usernameEmptyError;

    @FXML
    private String passwordEmptyError;

    @FXML
    private Button language;

    @FXML
    private Label accountLabel;
    private Map<String, Boolean> validationState = new HashMap<>();
    private Client client;
    private final List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("pt"),
            new Locale("pl")
    );
    private int currentLocaleIndex;
    private Stage stage;
    private boolean reconnectable;

    public LoginWindowController(LoginWindow loginWindow, Stage stage, int currentLocaleIndex) {
        this.stage = stage;
        this.loginWindow = loginWindow;
        this.currentLocaleIndex = currentLocaleIndex;
    }

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
        passwordEmptyError = currentBundle.getString("passwordEmptyErrorLabel");
        usernameEmptyError = currentBundle.getString("usernameEmptyErrorLabel");
        language.setText(currentBundle.getString("language"));
        accountLabel.setText(currentBundle.getString("accountLabel"));
        welcomeLabel.setText(currentBundle.getString("welcomeLabel"));
        signInButton.setText(currentBundle.getString("signInButton"));
        signUpLabel.setText(currentBundle.getString("signUpLabel"));
        usernameLabel.setText(currentBundle.getString("usernameLabel"));
        passwordLabel.setText(currentBundle.getString("passwordLabel"));
        passwordError = currentBundle.getString("passwordErrorLabel");
        usernameError = currentBundle.getString("usernameErrorLabel");
        serverError = currentBundle.getString("serverErrorLabel");
    }

    @FXML
    protected void onGeoIconClick() {
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        currentBundle = ResourceBundle.getBundle("MessagesBundle", supportedLocales.get(currentLocaleIndex), new UTF8Control());
        updateUI();
    }

    @FXML
    protected void onSignInButtonClick() {
        client = Client.getInstance();
        if (client.getStatus().equals("Unavailable") && reconnectable) client.reconnect();
        if (client.getStatus().equals("Connected")) {
            if (checkFields()) {
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
                    updateValidationState(usernameField, false, usernameError);
                    updateValidationState(passwordField, false, usernameError);
                } else {
                    updateValidationState(passwordField, false, passwordError);
                }
            }
        } else {
            AlertUtility.errorAlert(serverError);
            reconnectable = true;
        }
    }

    private boolean checkFields() {
        if (usernameField.getText().trim().equals("")) {
            updateValidationState(usernameField, false, usernameEmptyError);
        }
        if (passwordField.getText().trim().equals("")) {
            updateValidationState(passwordField, false, passwordEmptyError);
        }
        if (validationState.values().stream().allMatch(valid -> valid)) {
            updateValidationState(passwordField, true, usernameError);
            updateValidationState(usernameField, true, passwordError);
            return true;
        }
        return false;
    }


    @FXML
    protected void onSignUpLabelClick() {
        loginWindow.changeCard("signUp" , currentLocaleIndex);
    }

    private void updateValidationState(TextField field, boolean valid, String message) {
        if (valid) {
            field.setStyle("-fx-background-color: #00ff00;"); //green color
            field.setTooltip(null);
        } else {
            field.setStyle("-fx-background-color: #ff0000;"); //red color
            field.setTooltip(new Tooltip(message));
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
