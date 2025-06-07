package gui.commands;

import constructors.parsers.LocalDateTimeParser;
import constructors.parsers.ZonedDateTimeParser;
import gui.AlertUtility;
import gui.UTF8Control;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.validators.CoordinateYValidator;
import model.validators.SalaryValidator;
import model.validators.StartDateValidator;
import network.RequestBuilder;
import network.User;
import utility.Handler;
import сlient.Client;

import java.io.IOException;
import java.time.format.DateTimeParseException;
import java.util.*;

public class CommandsWindowController {
    Client client = Client.getInstance();
    private int currentLocaleIndex = 0;
    private Stage stage;
    private ResourceBundle currentBundle;
    private final List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("pt"),
            new Locale("pl")
    );

    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button deleteBySalary;
    @FXML
    private Button deleteGreaterKey;
    @FXML
    private Button deleteLowerKey;
    @FXML
    private Button deleteByStartDate;
    @FXML
    private Button deleteByEndDate;
    @FXML
    private VBox helpPanel;
    @FXML
    private Label actionLabel;
    @FXML
    private Label actionLabel1;

    // Текстовые поля
    @FXML
    private TextField salaryField;
    @FXML
    private TextField greaterKeyField;
    @FXML
    private TextField lowerKeyField;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private TextField executeScriptField;

    // Кнопки управления
    @FXML
    private Button exitButton;
    @FXML
    private Button helpButton;

    private Map<String, Boolean> validationState;

    public void initialize() {
        helpPanel.setVisible(false);
        validationState = new HashMap<>();
        validation();
    }

    private void updateUI() {
        clearButton.setText(currentBundle.getString("language"));
        executeScriptButton.setText(currentBundle.getString("executeScriptButton"));
        deleteBySalary.setText(currentBundle.getString("deleteBySalary"));
        deleteGreaterKey.setText(currentBundle.getString("deleteGreaterKey"));
        deleteLowerKey.setText(currentBundle.getString("deleteLowerKey"));
        deleteByStartDate.setText(currentBundle.getString("deleteByStartDate"));
        deleteByEndDate.setText(currentBundle.getString("deleteByEndDate"));
        helpButton.setText(currentBundle.getString("helpButton"));
        exitButton.setText(currentBundle.getString("exitButton"));
    }

    public void setActionText(String text, String text1) {
        this.actionLabel.setText(text);
        this.actionLabel1.setText(text1);
    }

    @FXML
    private void onClearButtonClick() {
        client.sendToServer(new RequestBuilder().setUser(Client.getInstance().getUser()).setCommand("clear").build());
    }

    @FXML
    private void onExecuteScriptButtonClick() {
        var scriptPath = executeScriptField.getText().trim();
        try {
            Handler.processInput("execute_script", scriptPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void onDeleteSalaryClick() {
        User user;
        if (validationState.entrySet().stream().filter(entry ->
                entry.getKey().equals("salary")).allMatch(Map.Entry::getValue)) {
            user = Client.getInstance().getUser();
            var arg = salaryField.getText().trim();
            client.sendToServer(new RequestBuilder().setUser(user).setCommand("remove_greater").setArg(arg).build());
        }
        else AlertUtility.errorAlert("Incorrect salary");
    }

    @FXML
    private void onDeleteGreaterKeyClick() {
        User user;
        if (validationState.entrySet().stream().filter(entry ->
                entry.getKey().equals("greaterKey")).allMatch(Map.Entry::getValue)) {
            user = Client.getInstance().getUser();
            var arg = greaterKeyField.getText().trim();
            client.sendToServer(new RequestBuilder().setUser(user).setCommand("remove_greater_key").setArg(arg).build());
        }
        else AlertUtility.errorAlert("Incorrect key");
    }

    @FXML
    private void onDeleteLowerKeyClick() {
        if (validationState.entrySet().stream().filter(entry ->
                entry.getKey().equals("lowerKey")).allMatch(Map.Entry::getValue)) {
            User user = Client.getInstance().getUser();
            var arg = lowerKeyField.getText().trim();
            client.sendToServer(new RequestBuilder().setUser(user).setCommand("remove_lower_key").setArg(arg).build());
        }
        else AlertUtility.errorAlert("Incorrect key");
    }

    @FXML
    private void onDeleteStartDateClick() {
        if (validationState.entrySet().stream().filter(entry ->
                entry.getKey().equals("startDate")).allMatch(Map.Entry::getValue)) {
            User user = Client.getInstance().getUser();
            var arg = startDateField.getText().trim();
            client.sendToServer(new RequestBuilder().setUser(user).setCommand("remove_all_by_start_date").setArg(arg).build());
        }
        else AlertUtility.errorAlert("Incorrect startDate");
    }

    @FXML
    private void onDeleteEndDateClick() {
        if (validationState.entrySet().stream().filter(entry ->
                entry.getKey().equals("endDate")).allMatch(Map.Entry::getValue)) {
            User user = Client.getInstance().getUser();
            var arg = endDateField.getText().trim();
            client.sendToServer(new RequestBuilder().setUser(user).setCommand("remove_all_by_end_date").setArg(arg).build());
        }
        else AlertUtility.errorAlert("Incorrect endDate");
    }

    @FXML
    private void onBackButtonClick() {
        stage.close();
    }

    @FXML
    private void onHelpButtonClick() {
        helpPanel.setVisible(!helpPanel.isVisible());
        stage.setWidth(helpPanel.isVisible() ? 950 : 500);
    }

    private void validation() {
        LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();
        ZonedDateTimeParser zonedDateTimeParser = new ZonedDateTimeParser();

        SalaryValidator salaryValidator = new SalaryValidator();
        salaryField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && salaryValidator.validate(Float.valueOf(newValue.trim()));
                if (!newValue.trim().isEmpty()) {
                    validationState.put("salary", valid);
                    updateValidationState(salaryField, valid, "Salary is not valid");
                } else {
                    validationState.put("salary", true);
                    updateValidationState(salaryField, true, "Salary is not valid");
                }
            } catch (NumberFormatException e) {
                updateValidationState(salaryField, false, "Salary is not valid. It should be a number.");
            }

        });
        StartDateValidator startDateValidator = new StartDateValidator();
        startDateField.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if (!newValue.trim().isEmpty()) {
                    boolean valid = startDateValidator.validate(localDateTimeParser.getResult(newValue.trim()));
                    validationState.put("startDate", valid);
                    updateValidationState(startDateField, valid, "StartDate is not valid. " + startDateValidator.getDescr());
                } else {
                    validationState.put("startDate", true);
                    updateValidationState(startDateField, true, "StartDate is not valid.");
                }
            }catch (DateTimeParseException e) {
                updateValidationState(startDateField, false,
                        "Enter StartDate in format 'yyyy-MM-dd HH:mm:ss' (Example: '2023-10-05 14:30:00)");
            }
        });

        endDateField.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                if (!newValue.trim().isEmpty()) {
                    zonedDateTimeParser.getResult(newValue.trim());
                    validationState.put("endDate", true);
                    updateValidationState(endDateField, true, "");
                }
                else {
                    validationState.put("endDate", true);
                    updateValidationState(endDateField, true, "");
                }
            }catch (DateTimeParseException e) {
                updateValidationState(endDateField, false,
                        "Enter StartDate in format 'yyyy-MM-dd HH:mm:ss z' (Example: '2023-10-05 14:30:00 UTC)");
            }
        });

        lowerKeyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {
                boolean valid = newValue.matches("\\d+");
                validationState.put("lowerKey", valid);
                updateValidationState(lowerKeyField, valid, "Key is not valid. It should be a number");
            }
            else {
                validationState.put("lowerKey", true);
                updateValidationState(lowerKeyField, true, "Key is not valid.");
            }
        });

        greaterKeyField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.trim().isEmpty()) {

                boolean valid = newValue.matches("\\d+");
                validationState.put("greaterKey", valid);
                updateValidationState(greaterKeyField, valid, "Key is not valid. It should be a number");
            }
            else{
                validationState.put("greaterKey", true);
                updateValidationState(greaterKeyField, true, "Key is not valid.");
            }
        });
    }

    private void updateValidationState(TextField field, boolean valid, String message) {
        if (valid) {
            field.setStyle("-fx-background-color: #ffffff; -fx-border-color: #bfbfbf; -fx-border-width: 1;" +
                    "-fx-border-radius: 3;");
            field.setTooltip(null);
        }
        else {
            field.setStyle("-fx-background-color: #ff0000;");
            field.setTooltip(new Tooltip(message));
        }
    }

        public void setLocale(int index) {
        this.currentLocaleIndex = index;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}