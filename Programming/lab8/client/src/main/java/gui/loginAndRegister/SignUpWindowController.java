package gui.loginAndRegister;

import authorization.Registration;
import gui.AlertUtility;
import gui.UTF8Control;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import network.User;
import сlient.Client;

import java.util.*;

public class SignUpWindowController {
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
    private String usernameExistsError;

    @FXML
    private String usernameLengthError;

    @FXML
    private String passwordLengthError;

    @FXML
    private String usernameEmptyError;

    @FXML
    private String passwordEmptyError;

    @FXML
    private String serverError;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button language;

    @FXML
    private Button toLoginButton;

    @FXML
    private Label accountLabel;

    private Map<String, Boolean> validationState = new HashMap<>();
    private Client client;
    private List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("pt"),
            new Locale("pl")
    );;
    private int currentLocaleIndex;
    private Stage stage;
    private boolean reconnectable;

    public SignUpWindowController(LoginWindow loginWindow, Stage stage, int currentLocaleIndex) {
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
        formatter();
        updateUI();
    }

    /**
     * Update LoginWindow UI
     */
    private void updateUI() {
        usernameExistsError = currentBundle.getString("usernameExistsErrorLabel");
        toLoginButton.setText(currentBundle.getString("toLoginButton"));
        language.setText(currentBundle.getString("language"));
        welcomeLabel.setText(currentBundle.getString("welcomeLabel"));
        signInButton.setText(currentBundle.getString("signUpLabel"));
        usernameLabel.setText(currentBundle.getString("usernameLabel"));
        passwordLabel.setText(currentBundle.getString("passwordLabel"));
        passwordError = currentBundle.getString("passwordErrorLabel");
        usernameError = currentBundle.getString("usernameErrorLabel");
        serverError = currentBundle.getString("serverErrorLabel");
        usernameLengthError = currentBundle.getString("usernameLengthErrorLabel");
        passwordLengthError = currentBundle.getString("passwordLengthErrorLabel");
        usernameEmptyError = currentBundle.getString("usernameEmptyErrorLabel");
        passwordEmptyError = currentBundle.getString("passwordEmptyErrorLabel");
    }

    @FXML
    protected void onGeoIconClick() {
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        currentBundle = ResourceBundle.getBundle("MessagesBundle", supportedLocales.get(currentLocaleIndex), new UTF8Control());
        updateUI();
    }

    private void formatter(){
        usernameField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 32 && !newText.contains(" ")){
                return change;
            }
            return null;
        }));
        passwordField.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (newText.length() <= 32 && !newText.contains(" ")){
                return change;
            }
            return null;
        }));
    }

    @FXML
    protected void onSignUpLabelClick() {
        client = Client.getInstance();
        if (reconnectable) client.reconnect();
        if (client.getStatus().equals("Connected")) {
            if (checkFields()) {
                try {
                    String username = usernameField.getText().trim();
                    String password = passwordField.getText().trim();
                    User user = new User(username, password);

                    Registration registration = new Registration(new Scanner(System.in), Client.getInstance());
                    registration.signUp(user);

                    if (registration.getStatus().equals("Success")) {
                        AlertUtility.infoAlert("User successfully registered");
                    } else if (registration.getStatus().equals("IsExist")) {
                        updateValidationState(usernameField, false, usernameExistsError);
                        updateValidationState(passwordField, false, usernameExistsError);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            AlertUtility.errorAlert(serverError);
            reconnectable = true;
        }
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

    private boolean checkUserField(){
        if (usernameField.getText().trim().equals("")) {
            updateValidationState(usernameField, false, usernameEmptyError);
            return false;
        }
        if (usernameField.getLength() < 2) {
            updateValidationState(usernameField, false, usernameLengthError);
            return false;
        }
        return true;
    }

    private boolean checkFields() {
        if (checkUserField()) {
            if (passwordField.getText().trim().equals("")) {
                updateValidationState(passwordField, false, usernameEmptyError);
                return false;
            }
            if (passwordField.getLength() < 2) {
                updateValidationState(passwordField, false, passwordLengthError);
                return false;
            }
        } else if (validationState.values().stream().allMatch(valid -> valid)) {
             updateValidationState(passwordField, true, passwordEmptyError);
             updateValidationState(usernameField, true, passwordError);
             return true;
        }
        return false;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onBackButtonClick() {
        loginWindow.changeCard("login", currentLocaleIndex);
    }
}
