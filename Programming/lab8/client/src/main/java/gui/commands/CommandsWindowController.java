package gui.commands;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class CommandsWindowController {

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


    // Кнопки управления
    @FXML
    private Button backButton;
    @FXML
    private Button helpButton;

    @FXML
    private void handleCommand1() {
        System.out.println("Команда 1: " + salaryField.getText());
        // Логика обработки команды 1
    }

    @FXML
    private void handleCommand2() {
        System.out.println("Команда 2: " + greaterKeyField.getText());
        // Логика обработки команды 2
    }

    // Аналогичные методы для остальных команд...
    @FXML
    private void handleCommand3() { /* ... */ }

    @FXML
    private void handleCommand4() { /* ... */ }

    @FXML
    private void handleCommand5() { /* ... */ }

    @FXML
    private void handleCommand6() { /* ... */ }

    @FXML
    private void handleCommand7() { /* ... */ }
}