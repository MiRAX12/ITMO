package gui.create;


import constructors.parsers.LocalDateTimeParser;
import constructors.parsers.ZonedDateTimeParser;
import gui.AlertUtility;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import model.validators.*;
import network.Response;
import utility.Handler;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller class for the Create Window.
 * This class handles user interactions in the Create Window UI.
 */
public class WorkerManagementWindowController {
    @FXML
    private Label actionLabel;
    @FXML
    private Button cancelButton;
    @FXML
    private Button createButton;
    @FXML
    private TextField nameField;
    @FXML
    private TextField coordXField;
    @FXML
    private TextField coordYField;
    @FXML
    private TextField salaryField;
    @FXML
    private TextField startDateField;
    @FXML
    private TextField endDateField;
    @FXML
    private ChoiceBox<String> statusChoiceBox;
    @FXML
    private TextField passportIDField;
    @FXML
    private TextField locationXField;
    @FXML
    private TextField locationYField;
    @FXML
    private TextField locationZField;

    private Map<String, Boolean> validationState;
    private String actionText;
    private Map<Long, String> ownershipMap; // Map of (city_id, client_name)
    private Worker selectedWorker;

    @FXML
    public void initialize() {
        actionLabel.setText(actionText);
        Arrays.stream(Status.values()).forEach(value -> statusChoiceBox.getItems().add(value.name()));
        validationState = new HashMap<>();
        validation();
    }

    public void populateFields(Worker worker) {
        LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();
        ZonedDateTimeParser zonedDateTimeParser = new ZonedDateTimeParser();
        if (worker != null) {
            nameField.setText(worker.getName());

            coordXField.setText(String.valueOf(worker.getCoordinates().getX()));
            coordYField.setText(String.valueOf(worker.getCoordinates().getY()));
            salaryField.setText(String.valueOf(worker.getSalary()));
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            startDateField.setText(worker.getStartDate().format(outputFormatter));


            if (worker.getEndDate() != null) {
                DateTimeFormatter outputFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
                endDateField.setText(worker.getEndDate().format(outputFormatter1));
            }

            statusChoiceBox.setValue(worker.getStatus().toString());

            if (worker.getPerson() != null) {
                if (worker.getPerson().getPassportId() != null){
                passportIDField.setText(worker.getPerson().getPassportId());
            }
            }
            if (worker.getPerson() != null) {
                locationXField.setText(String.valueOf(worker.getPerson().getLocation().getX()));
                locationYField.setText(String.valueOf(worker.getPerson().getLocation().getY()));
                locationZField.setText(String.valueOf(worker.getPerson().getLocation().getZ()));
            }
        }
    }

    private void updateUI() {
        actionLabel.setText(actionText);
    }

    /**
     * This method is invoked when the "Create" button is clicked.
     * It validates the input fields and, if valid, creates a new City object and sends it to the server.
     * If the fields are not valid, it shows an error message.
     */
    @FXML
    protected void onSaveButtonClick() {
        Response response = null;
        if (validationState.values().stream().allMatch(valid -> valid) &&
                statusChoiceBox.getValue() != null) {
            LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();
            ZonedDateTimeParser zonedDateTimeParser = new ZonedDateTimeParser();
            LocalDate creationDate;
            if (actionText.split(" ")[0].equals("Editing")) {
                long id = selectedWorker.getId();
                creationDate = selectedWorker.getCreationDate();
            } else {
                creationDate = LocalDate.now();

            }
            String name = nameField.getText().trim();
            Coordinates coordinates = new Coordinates();
            coordinates.setX(Float.valueOf(coordXField.getText().trim()));
            coordinates.setY(Long.valueOf(coordYField.getText().trim()));
            Float salary = Float.valueOf(salaryField.getText().trim());
            LocalDateTime startDate = localDateTimeParser.getResult((startDateField.getText().trim()));
            ZonedDateTime endDate = null;
            if (!endDateField.getText().trim().isEmpty()) endDate = zonedDateTimeParser.getResult((endDateField.getText().trim()));
            Status status = statusChoiceBox.getValue() != null ? Status.valueOf(statusChoiceBox.getValue()) : null;
            Person person;
            Location location;
            if (!locationXField.getText().trim().isEmpty()&&!locationYField.getText().trim().isEmpty()
                    &&!locationZField.getText().trim().isEmpty()) {
                Float x = Float.valueOf(locationXField.getText().trim());
                Float y = Float.valueOf(locationYField.getText().trim());
                Long z = Long.valueOf(locationZField.getText().trim());
                location = new Location.Builder().x(x).y(y).z(z).build();
                person = new Person();
                person.setLocation(location);
                person.setPassportId(passportIDField.getText().trim());
            }
            else
                person = null;
            Worker worker = new Worker.Builder().name(name).coordinates(coordinates).salary(salary).creationDate(creationDate).
                    startDate(startDate).endDate(endDate).status(status).person(person).build();
            try {
                if (actionText.split(" ")[0].equals("Editing")) {
                    response = Handler.processInput("remove_by_id", selectedWorker.getId().toString());
                } else {
                    Handler.executeInsert(worker);
                }
            } catch (IOException e) {
                AlertUtility.errorAlert("Can't load commands from server. Please wait until the server will come back");
            }

            Response finalResponse = response;
            Platform.runLater(() -> {
                if (finalResponse != null && (finalResponse.getMessage().contains("you don't have permission") || finalResponse.getMessage().contains("Element added"))) {
                    AlertUtility.infoAlert(finalResponse.getMessage());
                } else if (finalResponse != null && finalResponse.getMessage().contains("Elements removed")) {
                        Handler.executeInsert(worker);
                } else if (finalResponse != null) {
                    AlertUtility.infoAlert(finalResponse.getMessage());
                } else {
                    AlertUtility.errorAlert("Server not responding");
                }
                ((Stage) nameField.getScene().getWindow()).close();
            });
        } else {
            AlertUtility.errorAlert("Please correct all errors and fill the enums before proceeding.");
        }
    }

    /**
     * This method is invoked when the "Cancel" button is clicked.
     * It closes the Create Window.
     */
    @FXML
    protected void onCancelButtonClick() {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private void validation() {
        LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();
        ZonedDateTimeParser zonedDateTimeParser = new ZonedDateTimeParser();

        NameValidator nameValidator = new NameValidator();
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = nameValidator.validate(newValue.trim());
            validationState.put("name", valid);
            updateValidationState(nameField, valid, "Name is not valid. " + nameValidator.getDescr());
        });

        CoordinateXValidator coordinateXValidator = new CoordinateXValidator();
        coordXField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && coordinateXValidator.validate(Float.valueOf(newValue.trim()));
                validationState.put("coordX", valid);
                updateValidationState(coordXField, valid, "CoordX is not valid. " + coordinateXValidator.getDescr());
            } catch (NumberFormatException e) {
                updateValidationState(coordXField, false, "CoordX is not valid. It should be a number.");
            }
        });

        CoordinateYValidator coordinateYValidator = new CoordinateYValidator();
        coordYField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("\\d+") && coordinateYValidator.validate(Long.valueOf(newValue.trim()));
                validationState.put("coordY", valid);
                updateValidationState(coordYField, valid, "CoordY is not valid. " + coordinateYValidator.getDescr());
            } catch (NumberFormatException e) {
                updateValidationState(coordYField, false, "CoordY is not valid. It should be a number.");
            }
        });

        SalaryValidator salaryValidator = new SalaryValidator();
        salaryField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && salaryValidator.validate(Float.valueOf(newValue.trim()));
                validationState.put("salary", valid);
                updateValidationState(salaryField, valid, "Salary is not valid. " + salaryValidator.getDescr());
            } catch (NumberFormatException e) {
                updateValidationState(salaryField, false, "Salary is not valid. It should be a number.");
            }
        });

        StartDateValidator startDateValidator = new StartDateValidator();
        startDateField.textProperty().addListener((observable, oldValue, newValue) -> {
            try{
                boolean valid = startDateValidator.validate(localDateTimeParser.getResult(newValue.trim()));
                validationState.put("startDate", valid);
                updateValidationState(startDateField, valid, "StartDate is not valid. " + startDateValidator.getDescr());
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

        passportIDField.textProperty().addListener((observable, oldValue, newValue)->{
            if (!newValue.trim().isEmpty() && locationXField.getText().trim().isEmpty()) {
                validationState.put("locationX", false);
                updateValidationState(locationXField, false, "Location can't be empty while PassportId isn't empty");
            }
            if(!newValue.trim().isEmpty() && locationYField.getText().trim().isEmpty()) {
                validationState.put("locationY", false);
                updateValidationState(locationYField, false, "Location can't be empty while PassportId isn't empty");
            }
            if (!newValue.trim().isEmpty() && locationZField.getText().trim().isEmpty()) {
                validationState.put("locationZ", false);
                updateValidationState(locationZField, false, "Location can't be empty while PassportId isn't empty");
            }
            if (newValue.trim().isEmpty()){
                validationState.put("locationX", true);
                updateValidationState(locationXField, true, "");
                validationState.put("locationZ", true);
                updateValidationState(locationZField, true, "");
                validationState.put("locationY", true);
                updateValidationState(locationYField, true, "");
            }
        });

        LocationXValidator locationXValidator = new LocationXValidator();
        locationXField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.trim().isEmpty() && locationYField.getText().trim().isEmpty()) {
                    validationState.put("locationY", false);
                    updateValidationState(locationYField, false, "Locations must be all empty or all filled");
                }
                if (!newValue.trim().isEmpty() && locationZField.getText().trim().isEmpty()) {
                    validationState.put("locationZ", false);
                    updateValidationState(locationZField, false, "Locations must be all empty or all filled");
                }
                if (!newValue.trim().isEmpty()) {
                    boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && locationXValidator.validate(Float.valueOf(newValue.trim()));
                    validationState.put("locationX", valid);
                    updateValidationState(locationXField, valid, "LocationX is not valid. " + locationXValidator.getDescr());
                }
            } catch (NumberFormatException e) {
                updateValidationState(locationXField, false, "LocationX is not valid. It should be a number.");
            }
        });

        LocationYValidator locationYValidator = new LocationYValidator();
        locationYField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.trim().isEmpty() && locationXField.getText().trim().isEmpty()) {
                    validationState.put("locationX", false);
                    updateValidationState(locationXField, false, "Locations must be all empty or all filled");
                }
                if (!newValue.trim().isEmpty() && locationZField.getText().trim().isEmpty()) {
                    validationState.put("locationZ", false);
                    updateValidationState(locationZField, false, "Locations must be all empty or all filled");
                } if (!newValue.trim().isEmpty()) {
                    boolean valid = newValue.matches("-?\\d+(\\.\\d+)?") && locationYValidator.validate(Float.valueOf(newValue.trim()));
                    validationState.put("locationY", valid);
                    updateValidationState(locationYField, valid, "LocationY is not valid. " + locationYValidator.getDescr());
                }
            } catch (NumberFormatException e) {
                updateValidationState(locationYField, false, "LocationY is not valid. It should be a number.");
            }
        });

        LocationZValidator locationZValidator = new LocationZValidator();
        locationYField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                if (!newValue.trim().isEmpty() && locationXField.getText().trim().isEmpty()) {
                    validationState.put("locationZ", false);
                    updateValidationState(locationZField, false, "Locations must be all empty or all filled");
                }
                if (!newValue.trim().isEmpty() && locationYField.getText().trim().isEmpty()) {
                    validationState.put("locationY", false);
                    updateValidationState(locationYField, false, "Locations must be all empty or all filled");
                }
                if (!newValue.trim().isEmpty()) {
                    boolean valid = newValue.matches("\\d+") && locationZValidator.validate(Long.valueOf(newValue.trim()));
                    validationState.put("locationZ", valid);
                    updateValidationState(locationZField, valid, "LocationZ is not valid. " + locationZValidator.getDescr());
                }
            } catch (NumberFormatException e) {
                updateValidationState(locationZField, false, "LocationZ is not valid. It should be a number.");
            }
        });

        statusChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean valid = newValue != null && Arrays.stream(Status.values()).map(Enum::name).toList().contains(newValue);
            validationState.put("climate", valid);
            updateValidationState(statusChoiceBox, valid, "Status is not valid. It should be one of " + Arrays.toString(Status.values()));
        });
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

    private void updateValidationState(ChoiceBox<String> choiceBox, boolean valid, String message) {
        if (valid) {
            choiceBox.setStyle("-fx-background-color: #00ff00;"); //green color
            choiceBox.setTooltip(null);
        } else {
            choiceBox.setStyle("-fx-background-color: #ff0000;"); //red color
            choiceBox.setTooltip(new Tooltip(message));
        }
    }

    public void setActionText(String actionText) {
        this.actionText = actionText;
        updateUI();
    }

    public void setEditingCity(Worker worker) {
        this.selectedWorker = worker;
        populateFields(selectedWorker);
    }
}
