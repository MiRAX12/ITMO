package gui.commands;

import gui.collections.CollectionsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class CommandsWindow {
    private Stage stage;
    private Scene scene;
    private int localeIndex;
    private CommandsWindowController controller;

    public CommandsWindow(String actionText, String actionText1) {
        try {
            stage = new Stage();
            URL fxmlLocation = getClass().getResource("/commands/commandsWindow.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent root = loader.load();

            controller = loader.getController();
            controller.setActionText(actionText, actionText1);
            setup();

            Scene scene = new Scene(root, 470, 450);
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setup() {
        stage.setResizable(false);
        controller.setLocale(CollectionsWindowController.getCurrentLocaleIndex());
        controller.setStage(stage);
    }

    public void show() {
        stage.show();
    }
}