package gui.collections;

import constructors.parsers.LocalDateTimeParser;
import constructors.parsers.ZonedDateTimeParser;
import gui.AlertUtility;
import gui.UTF8Control;
import gui.commands.CommandsWindow;
import gui.create.WorkerManagementWindow;
import gui.login.LoginWindow;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Status;
import model.Worker;
import network.RequestBuilder;
import network.Response;
import utility.Handler;
import сlient.Client;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionsWindowController {
    @FXML
    private Button clearButton;
    @FXML
    private Button executeScriptButton;
    @FXML
    private Button exitButton;
    @FXML
    private ToolBar commandsToolBar;
    @FXML
    private Button commandsButton;
    @FXML
    private Button createButton;
    @FXML
    private Label filterByLabel;
    @FXML
    private Label workerCountLabel;
    @FXML
    private Label workersLabel;
    private ResourceBundle currentBundle;
    private Stage stage;

    private String scriptPath;
    private Client client = Client.getInstance();
    private List<Locale> supportedLocales;

    private static int currentLocaleIndex = 0;
    private final ObservableMap<Long, Worker> collection = FXCollections.observableHashMap();
    @FXML
    private TableView<Worker> table;
    @FXML
    private TableColumn<Worker, Long> idColumn;
    @FXML
    private TableColumn<Worker, String> nameColumn;
    @FXML
    private TableColumn<Worker, Float> coordXColumn;
    @FXML
    private TableColumn<Worker, Long> coordYColumn;
    @FXML
    private TableColumn<Worker, String> creationDate;
    @FXML
    private TableColumn<Worker, Float> salaryColumn;
    @FXML
    private TableColumn<Worker, String> startDateColumn;
    @FXML
    private TableColumn<Worker, String> endDateColumn;
    @FXML
    private TableColumn<Worker, String> statusColumn;
    @FXML
    private TableColumn<Worker, String> passportIdColumn;
    @FXML
    private TableColumn<Worker, String> locationXColumn;
    @FXML
    private TableColumn<Worker, String> locationYColumn;
    @FXML
    private TableColumn<Worker, String> locationZColumn;

    @FXML
    private Text usernameText;

    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private TextField filterByText;
    @FXML
    private Button language;

    private String currentUsername;

    private Worker selectedWorker;

    public CollectionsWindowController(ResourceBundle bundle, List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
        this.currentBundle = bundle;
    }

    @FXML
    public void initialize() {
        // handle locales
        updateUI();
        loadCollection();
        setCollection(collection);

        // init graphics stuff
        comboBox.getItems().clear();
        comboBox.getItems().addAll("id", "name", "coordinateX", "coordinateY", "salary",
                "startDate", "endDate", "status", "passportId", "locationX", "locationY", "locationZ");
        Font.loadFont(getClass().getResourceAsStream("/fonts/ZCOOLXiaoWei-Regular.ttf"), 12);
        Font.loadFont(getClass().getResourceAsStream("/fonts/YouSheBiaoTiHei Regular.ttf"), 14);
        commandsToolBar.setVisible(false);

        table.setRowFactory(tv -> {
            TableRow<Worker> row = new TableRow<>() {
                @Override
                protected void updateItem(Worker item, boolean empty) {
                    super.updateItem(item, empty);
                    if (getIndex() >= 0 && getIndex() < table.getItems().size()) {
                        setTranslateY(550); // Начальное положение (ниже)
                        setOpacity(0);     // Начальная прозрачность

                        // Анимация появления
                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.millis(500 + getIndex() * 100),
                                        new KeyValue(translateYProperty(), 0, Interpolator.EASE_OUT),
                                        new KeyValue(opacityProperty(), 1, Interpolator.EASE_OUT)
                                ));
                        timeline.play();
                    }
                }
            };
            return row;
        });

        // Setup cellValueFactories
        idColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getId()).asObject());
        nameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        coordXColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getCoordinates().getX()).asObject());
        coordYColumn.setCellValueFactory(cellData ->
                new SimpleLongProperty(cellData.getValue().getCoordinates().getY()).asObject());
        creationDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCreationDate().toString()));
        salaryColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getSalary()).asObject());
        startDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStartDate().toString()));
        endDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndDate() != null
                ? cellData.getValue().getEndDate().toString() : ""));
        statusColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getStatus().toString()));
        passportIdColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPerson() != null &&
                cellData.getValue().getPerson().getPassportId() != null ? cellData.getValue().getPerson().getPassportId(): ""));
        locationXColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPerson() != null
                ? cellData.getValue().getPerson().getLocation().getX().toString() : ""));
        locationYColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPerson() != null
                ? cellData.getValue().getPerson().getLocation().getX().toString() : ""));
        locationZColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getPerson() != null
                ? cellData.getValue().getPerson().getLocation().getX().toString() : ""));

        filterByText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String selectedValue = comboBox.getSelectionModel().getSelectedItem();
                filterWorkers(selectedValue, newValue);
            } else {
                List<Worker> workersList = new ArrayList<>(collection.values());
                table.setItems(FXCollections.observableArrayList(workersList));
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> loadCollection()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        collection.addListener((MapChangeListener<Long, Worker>) change ->
                setCollection(collection));
    }

    /**
     * Load collection from server
     */
    private void loadCollection() {
        Map<Long, Worker> newData = Handler.getMap();
        if (newData != null) {
            if (!newData.equals(collection)) {
                collection.clear();
                collection.putAll(newData);
            }
        }
    }

    /**
     * Set just loaded collection to this class
     * Update TableView with that collection
     * Update counter with collection.size()
     *
     * @param collection - TreeSet<City> collection of City objects
     */
    public void setCollection(Map<Long, Worker> collection) {
        if (collection != null) {
            Platform.runLater(() -> {
                table.setItems(FXCollections.observableArrayList(collection.values()));
                table.refresh();
                workerCountLabel.setText(Integer.toString(collection.size()));
            });
        }
    }

    /**
     * Update CollectionsWindow UI
     */
    private void updateUI() {
        // TableView

        idColumn.setText(currentBundle.getString("id"));
        nameColumn.setText(currentBundle.getString("name"));
        coordXColumn.setText(currentBundle.getString("coordX"));
        coordYColumn.setText(currentBundle.getString("coordY"));
        creationDate.setText(currentBundle.getString("creationDate"));
        salaryColumn.setText(currentBundle.getString("salary"));
        startDateColumn.setText(currentBundle.getString("startDate"));
        endDateColumn.setText(currentBundle.getString("endDate"));
        statusColumn.setText(currentBundle.getString("status"));
        passportIdColumn.setText(currentBundle.getString("passportId"));
        locationXColumn.setText(currentBundle.getString("locationX"));
        locationYColumn.setText(currentBundle.getString("locationY"));
        locationZColumn.setText(currentBundle.getString("locationZ"));

        // buttons & labels
        currentUsername = client.getUser().getUsername();
        usernameText.setText(currentBundle.getString("usernameText") + currentUsername);
        workersLabel.setText(currentBundle.getString("workerCountLabel"));
        filterByLabel.setText(currentBundle.getString("filterByLabel"));
        language.setText(currentBundle.getString("language"));
        ObservableList<String> localizedItems = FXCollections.observableArrayList(
                currentBundle.getString("id"),
                currentBundle.getString("name"),
                currentBundle.getString("coordX"),
                currentBundle.getString("coordY"),
                currentBundle.getString("creationDate"),
                currentBundle.getString("salary"),
                currentBundle.getString("startDate"),
                currentBundle.getString("endDate"),
                currentBundle.getString("status"),
                currentBundle.getString("passportId"),
                currentBundle.getString("locationX"),
                currentBundle.getString("locationY"),
                currentBundle.getString("locationZ")
        );
        comboBox.getItems().setAll(localizedItems);
        commandsButton.setText(currentBundle.getString("commandsButton"));
        createButton.setText(currentBundle.getString("createButton"));

    }

    @FXML
    protected void onCreateButtonClick() {
        WorkerManagementWindow workerManagementWindow = new WorkerManagementWindow("Inserting Worker");
        workerManagementWindow.show();
    }

    @FXML
    protected void onEditButtonClick() {
        Worker selectedWorker = table.getSelectionModel().getSelectedItem();
        if (selectedWorker != null) {
            WorkerManagementWindow workerManagementWindow = new WorkerManagementWindow("Editing Worker");
            workerManagementWindow.show();
            workerManagementWindow.setWorker(selectedWorker);
        } else {
            AlertUtility.infoAlert("Please, select any city to edit it!)");
        }
    }

    @FXML
    protected void onDeleteButtonClick() {
        Worker selectedWorker = table.getSelectionModel().getSelectedItem();
        if (selectedWorker != null) {
            table.getItems().remove(selectedWorker);
            try {
                Handler.processInput("remove_by_id", selectedWorker.getId().toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    protected void onCommandsButtonClick() {
        CommandsWindow commandsWindow = new CommandsWindow("Commands", "Delete");
        commandsWindow.show();
    }

    @FXML
    protected void onGeoIconClick() {
        currentLocaleIndex = (currentLocaleIndex + 1) % supportedLocales.size();
        currentBundle = ResourceBundle.getBundle("MessagesBundle", supportedLocales.get(currentLocaleIndex), new UTF8Control());
        updateUI();
    }

    public static int getCurrentLocaleIndex() {
        return currentLocaleIndex;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void onExecuteScriptButtonClick() {
        try {
            Handler.processInput("execute_script", scriptPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void onExitButtonClick() {
        Client.getInstance().setUser(null);
        stage.close();
        LoginWindow loginWindow = new LoginWindow(new Stage());
        loginWindow.show();
    }

    @FXML
    protected void onClearButtonClick() {
            client.sendToServer(new RequestBuilder().setUser(Client.getInstance().getUser()).setCommand("clear").build());
    }

    private void filterWorkers(String property, String value) {
        if (property != null) {
            Stream<Worker> workerStream = Handler.getMap().values().stream();
            switch (property) {
                case "id":
                    long id = Long.parseLong(value);
                    workerStream = workerStream.filter(worker -> worker.getId() == id);
                    break;
                case "coordinateX":
                    float coordinateX = Float.parseFloat(value);
                    workerStream = workerStream.filter(worker -> worker.getCoordinates().getX() == coordinateX);
                    break;
                case "coordinateY":
                    long coordinateY = Long.parseLong(value);
                    workerStream = workerStream.filter(worker -> worker.getCoordinates().getY() == coordinateY);
                    break;
                case "creationDate":
                    LocalDate creationDate = LocalDate.parse(value);
                    workerStream = workerStream.filter(worker -> worker.getCreationDate() == creationDate);
                    break;
                case "salary":
                    float salary = Float.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getSalary() == salary);
                    break;
                case "startDate":
                    LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();
                    LocalDateTime startDate = localDateTimeParser.getResult(value);
                    workerStream = workerStream.filter(worker -> worker.getStartDate().equals(startDate));
                    break;
                case "endDate":
                    ZonedDateTimeParser zonedDateTimeParser = new ZonedDateTimeParser();
                    ZonedDateTime endDate = zonedDateTimeParser.getResult(value);
                    workerStream = workerStream.filter(worker -> worker.getEndDate().equals(endDate));
                    break;
                case "name":
                    workerStream = workerStream.filter(worker -> worker.getName().equals(value));
                    break;
                case "status":
                    Status status = Status.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getStatus().equals(status));
                    break;
                case "passportId":
                    workerStream = workerStream.filter(worker -> worker.getPerson().getPassportId().equals(value));
                    break;
                case "locationX":
                    Float locationX = Float.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getPerson().getLocation().getX().equals(locationX));
                    break;
                case "locationY":
                    Float locationY = Float.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getPerson().getLocation().getY().equals(locationY));
                    break;
                case "locationZ":
                    Float locationZ = Float.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getPerson().getLocation().getZ().equals(locationZ));
                    break;
                default:
                    break;
            }
            table.setItems(FXCollections.observableArrayList(workerStream.collect(Collectors.toList())));
        }
    }

    public void setCurrentBundle(ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
    }

    public void setSupportedLocales(List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
    }
}
