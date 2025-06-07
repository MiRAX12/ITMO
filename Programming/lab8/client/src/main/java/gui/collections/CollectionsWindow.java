package gui.collections;

import gui.UTF8Control;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class CollectionsWindow {

    private Stage stage;
    private CollectionsWindowController controller;
    private int localeIndex;
    private final List<Locale> supportedLocales = Arrays.asList(
            new Locale("en", "NZ"),
            new Locale("ru"),
            new Locale("pt"),
            new Locale("pl")
    );
    private ResourceBundle currentBundle;

    public CollectionsWindow(int localeIndex) {
        this.localeIndex = localeIndex;
        try {
            stage = new Stage();
            URL fxmlLocation = getClass().getResource("/collections/collectionsWindow.fxml");
            currentBundle = ResourceBundle.getBundle("MessagesBundle", supportedLocales.get(localeIndex), new UTF8Control());
            FXMLLoader loader = new FXMLLoader(fxmlLocation, currentBundle);
            loader.setControllerFactory(param -> {
                CollectionsWindowController controller = new CollectionsWindowController(currentBundle, supportedLocales);
                loader.setController(controller);
                return controller;
            });
            Parent root = loader.load();
            controller = loader.getController();
            setup();

            Scene scene = new Scene(root, 1005, 578);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        stage.setResizable(false);
        controller.setStage(stage);
//        controller.setLocale(localeIndex);

    }

    public void show() {
        stage.show();
    }
}
