package gui.collections;

import constructors.parsers.LocalDateParser;
import constructors.parsers.LocalDateTimeParser;
import constructors.parsers.ZonedDateTimeParser;
import gui.AlertUtility;
import gui.UTF8Control;
import gui.commands.CommandsWindow;
import gui.create.WorkerManagementWindow;
import gui.loginAndRegister.LoginWindow;
import gui.visualization.VisualizationWindow;
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
import javafx.scene.paint.Color;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionsWindowController {
    @FXML
    private Button visualizeButton;
    @FXML
    private Button editButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button commandsButton;
    @FXML
    private Button createButton;
    @FXML
    private Label filterByLabel;
    @FXML
    private Label workerCountLabel;

    private ResourceBundle currentBundle;
    private Stage stage;
    private boolean playAnimation = true;

    private String scriptPath;
    private Client client = Client.getInstance();
    private List<Locale> supportedLocales;

    private static int currentLocaleIndex = 0;
    private static final ObservableMap<Long, Worker> collection = FXCollections.observableHashMap();
    private Map<Long, Worker> workerMap;
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

    private Map<Long, String> workerCreatorMap;
    private Map<String, Color> clientColorMap = new HashMap<>();


    private Worker selectedWorker;

    public CollectionsWindowController(ResourceBundle bundle, List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
        this.currentBundle = bundle;
    }

    @FXML
    public void initialize() {
        loadWorkerCreatorMap();
        updateUI();
        loadCollection();
        setCollection(collection);

        comboBox.getItems().clear();
        comboBox.getItems().addAll("id", "name", "coordinateX", "coordinateY", "salary",
                "startDate", "endDate", "status", "passportId", "locationX", "locationY", "locationZ");

        table.setRowFactory(tv -> {
            TableRow<Worker> row = new TableRow<>() {
                @Override
                protected void updateItem(Worker worker, boolean empty) {
                    super.updateItem(worker, empty);
                    if (worker == null) {
                        setStyle("");
                    } else {
                        double opacity = 0.5;
                        Color color = clientColorMap.get(workerCreatorMap.get(worker.getId()));
                        String rgba = String.format("#%02X%02X%02X%02X",
                                (int) (color.getRed() * 255),
                                (int) (color.getGreen() * 255),
                                (int) (color.getBlue() * 255),
                                (int) (opacity * 255));
                                setStyle("-fx-background-color: " + rgba + ";");
                    }

                    if (playAnimation && getIndex() >= 0 && getIndex() < table.getItems().size()) {
                        setTranslateY(350);
                        setOpacity(0);

                        Timeline timeline = new Timeline(
                                new KeyFrame(Duration.millis(1000 + getIndex() * 100),
                                        new KeyValue(translateYProperty(), 0, Interpolator.EASE_OUT),
                                        new KeyValue(opacityProperty(), 1, Interpolator.EASE_OUT)
                                ));

                        timeline.setOnFinished(event -> playAnimation = false);
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
        LocalDateParser localDateParser = new LocalDateParser();
        creationDate.setCellValueFactory(cellData ->
                new SimpleStringProperty(localDateParser.formatter(cellData.getValue().getCreationDate().toString(), currentBundle)));
        salaryColumn.setCellValueFactory(cellData ->
                new SimpleFloatProperty(cellData.getValue().getSalary()).asObject());
        LocalDateTimeParser localDateTimeParser = new LocalDateTimeParser();
        startDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(localDateTimeParser.formatter(cellData.getValue().getStartDate().toString(), currentBundle)));
        ZonedDateTimeParser zonedDateTimeParser = new ZonedDateTimeParser();
        endDateColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getEndDate() != null
                ? zonedDateTimeParser.formatter(cellData.getValue().getEndDate().toString(), currentBundle) : ""));
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

        table.getSelectionModel().selectedItemProperty().addListener(((observableValue, worker, t1) ->{
        if (t1 != null) {
            playAnimation = false;
        }}));

        filterByText.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.isEmpty()) {
                String selectedValue = comboBox.getSelectionModel().getSelectedItem();
                filterWorkers(selectedValue, newValue);
            } else {
                List<Worker> workersList = new ArrayList<>(collection.values());
                table.setItems(FXCollections.observableArrayList(workersList));
            }
        });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event -> {
            loadCollection();
            loadWorkerCreatorMap();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        collection.addListener((MapChangeListener<Long, Worker>) change -> {
                    setCollection(collection);
                    playAnimation = true;
        });
    }

    private void loadWorkerCreatorMap() {
        client = Client.getInstance();
        boolean flag = false;
        while (!flag ){
        try {
            workerCreatorMap = Handler.processInput("get_worker_creator_map", null).getWorkerCreatorMap();
            if (workerCreatorMap != null) {
                for (String clientName : new HashSet<>(workerCreatorMap.values())) {
                    if (client.getUser() != null && clientName.equals(Client.getInstance().getUser().getUsername())) {
                        clientColorMap.put(clientName, Color.rgb(1, 255, 1));
                    } else if (!clientColorMap.containsKey(clientName)) {
                        Color randomColor = Color.color(Math.random(), 0, Math.random());
                        clientColorMap.put(clientName, randomColor);
                    }
                    flag = true;

                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        }
    }

    /**
     * Load collection from server
     */
    private void loadCollection() {
        workerMap = Handler.getMap();
        if (workerMap != null) {
                collection.clear();
                collection.putAll(workerMap);
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
                workerCountLabel.setText(collection.size() + " " + currentBundle.getString("workerCountLabel"));
            });
        }
    }

    /**
     * Update CollectionsWindow UI
     */
    private void updateUI() {
        // TableView
        editButton.setText(currentBundle.getString("editButton"));
        deleteButton.setText(currentBundle.getString("deleteButton"));
        visualizeButton.setText(currentBundle.getString("visualizeButton"));
        workerCountLabel.setText(collection.size() + " " + currentBundle.getString("workerCountLabel"));
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
        filterByLabel.setText(currentBundle.getString("filterByLabel"));
        language.setText(currentBundle.getString("language"));
        exitButton.setText(currentBundle.getString("exit"));
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
        filterWorkers(" ", " ");
    }

    @FXML
    protected void onCreateButtonClick() {
        WorkerManagementWindow workerManagementWindow = new WorkerManagementWindow("Inserting Worker");
        workerManagementWindow.show();
    }

    @FXML
    protected void onEditButtonClick() {
        selectedWorker = table.getSelectionModel().getSelectedItem();
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
        selectedWorker = table.getSelectionModel().getSelectedItem();
        if (selectedWorker != null) {
            try {
                Response response = Handler.processInput("remove_by_id", selectedWorker.getId().toString());
                if (response.getMessage().contains("Elements removed")){
                    AlertUtility.infoAlert("Элемент удален");
                    table.getItems().remove(selectedWorker);
                } else if (response.getMessage().contains("don't have permission")) {
                    AlertUtility.errorAlert("У вас нет прав на удаление");
                }

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

    @FXML
    protected void onVisualizeButtonClick() {
        VisualizationWindow visualizationWindow = new VisualizationWindow(collection);
        visualizationWindow.loadCollection(collection, clientColorMap, workerCreatorMap);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(4), event ->
                visualizationWindow.loadCollection(workerMap, clientColorMap, workerCreatorMap)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        visualizationWindow.show();
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
        if (property != null && Handler.getMap().values().stream() != null) {
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
                    DateTimeFormatter formatter;
                    switch (currentBundle.getLocale().toString()) {
                        case "en_NZ":
                            formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", currentBundle.getLocale());
                            break;
                        case "pl":
                            formatter = DateTimeFormatter.ofPattern("EEEE, yyyy MMMM d", currentBundle.getLocale());
                            break;
                        case "ru":
                            formatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy 'г.'", currentBundle.getLocale());
                            break;
                        case "pt":
                            formatter = DateTimeFormatter.ofPattern("EEEE, d. MMMM yyyy", currentBundle.getLocale());
                            break;
                        default:
                            formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy", Locale.US);
                            break;
                    }
                    LocalDate date = LocalDate.parse(value, formatter);
                    workerStream = workerStream.filter(worker -> {
                        LocalDate workerCreationDate = LocalDate.parse(value, formatter);
                        return workerCreationDate.equals(date);
                    });
                case "salary":
                    float salary = Float.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getSalary() == salary);
                    break;
                case "startDate":
                    DateTimeFormatter formatter1;
                    switch (currentBundle.getLocale().toString()) {
                        case "en_NZ":
                            formatter1 = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss", currentBundle.getLocale());
                            break;
                        case "pl":
                            formatter1 = DateTimeFormatter.ofPattern("yyyy MMMM d HH:mm:ss", currentBundle.getLocale());
                            break;
                        case "ru":
                            formatter1 = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.' HH:mm:ss", currentBundle.getLocale());
                            break;
                        case "pt":
                            formatter1 = DateTimeFormatter.ofPattern("d. MMMM yyyy HH:mm:ss", currentBundle.getLocale());
                            break;
                        default:
                            formatter1 = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss", Locale.US);
                            break;
                    }
                    LocalDateTime startDate = LocalDateTime.parse(value, formatter1);
                    workerStream = workerStream.filter(worker -> {
                        LocalDateTime workerStartDate = LocalDateTime.parse(value, formatter1);
                        return workerStartDate.equals(startDate);
                    });
                case "endDate":
                    DateTimeFormatter formatter2;
                    switch (currentBundle.getLocale().toString()) {
                        case "en_NZ":
                            formatter2 = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss", currentBundle.getLocale());
                            break;
                        case "pl":
                            formatter2 = DateTimeFormatter.ofPattern("yyyy MMMM d HH:mm:ss", currentBundle.getLocale());
                            break;
                        case "ru":
                            formatter2 = DateTimeFormatter.ofPattern("d MMMM yyyy 'г.' HH:mm:ss", currentBundle.getLocale());
                            break;
                        case "pt":
                            formatter2 = DateTimeFormatter.ofPattern("d. MMMM yyyy HH:mm:ss", currentBundle.getLocale());
                            break;
                        default:
                            formatter2 = DateTimeFormatter.ofPattern("MMMM d, yyyy HH:mm:ss", Locale.US);
                            break;
                    }
                    ZonedDateTime endDate = ZonedDateTime.parse(value, formatter2);
                    workerStream = workerStream.filter(worker -> {
                        LocalDate workerCreationDate = LocalDate.parse(value, formatter2);
                        return workerCreationDate.equals(endDate);
                    });
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
                    Long locationZ = Long.valueOf(value);
                    workerStream = workerStream.filter(worker -> worker.getPerson().getLocation().getZ().equals(locationZ));
                    break;
                default:
                    break;
            }
            table.setItems(FXCollections.observableArrayList(workerStream.collect(Collectors.toList())));
        }
    }

    public static ObservableMap<Long, Worker> getCollection() {
        return collection;

    }

    public void setCurrentBundle(ResourceBundle currentBundle) {
        this.currentBundle = currentBundle;
    }
    public void setSupportedLocales(List<Locale> supportedLocales) {
        this.supportedLocales = supportedLocales;
    }
}
