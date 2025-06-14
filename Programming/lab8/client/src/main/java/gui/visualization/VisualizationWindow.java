package gui.visualization;

import javafx.collections.ObservableMap;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.DragEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Worker;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class VisualizationWindow {
    private Stage stage;
    private VisualizationWindowController controller;


    public VisualizationWindow(ObservableMap<Long, Worker> worker) {
        try {
            stage = new Stage();
            stage.setTitle("Visualization");
            stage.setResizable(false);
            URL fxmlLocation = getClass().getResource("/visualization/visualizationWindow.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            loader.setController(new VisualizationWindowController());
            Parent root = loader.load();

            controller = loader.getController();
            this.stage.setScene(new Scene(root, 1024, 768));


            Node node = this.stage.getScene().getRoot();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadCollection(Map<Long, Worker> worker, Map<String, Color> colorMap,
                               Map<Long, String> ownershipMap) {
        controller.loadCollections(worker, colorMap, ownershipMap);
    }


    public void show() {
        stage.show();
    }
}
