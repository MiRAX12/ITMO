package gui.visualization;

import gui.create.WorkerManagementWindow;
import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.Worker;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class VisualizationWindowController {
    @FXML
    private Canvas canvas;
    private ObservableMap<String, Color> colorMap = FXCollections.observableHashMap();
    private ObservableMap<Long, String> workerCreatorMap = FXCollections.observableHashMap();
    private ObservableMap<Long, Worker> collection = FXCollections.observableHashMap();
    private final Map <Worker, DoubleProperty> opacityMap = new HashMap<>();
    private double currentX;
    private double currentY;
    private double zoomFactor = 1D;
    private Double initialX;
    private Double initialY;
    private double meshLeftX;
    private double meshRightX;
    private double meshTopY;
    private double meshBottomY;
    private Double prevDragX;
    private Double prevDragY;
    private boolean playAnimation = false;

    public VisualizationWindowController() {
    }

    @FXML
    public void initialize() {
        canvas.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                zoomFactor = 1D;
                redraw();
            }
            workerClicked(event.getX(), event.getY());
        });
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> drag(canvas));
        canvas.addEventHandler(ScrollEvent.ANY, event -> scroll(canvas));
        collection.addListener((MapChangeListener<Long, Worker>) change ->{
            if (change.wasAdded()){
            DoubleProperty opacity = new SimpleDoubleProperty(0);
            opacityMap.put(change.getValueAdded(), opacity);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(4),
                            new KeyValue(opacity, 1.0, Interpolator.EASE_OUT))
            );
            timeline.play();
            if (!playAnimation){
                firstDraw();
                playAnimation = true;
            }
        }
        if (change.wasRemoved()) {
            opacityMap.remove(change.getValueRemoved());
        }
        redraw();
                });
        currentY = 0;
        currentX = 0;
        meshLeftX = -canvas.getWidth() * 2;
        meshRightX = canvas.getHeight() * 2;
        meshTopY = -canvas.getHeight() * 2;
        meshBottomY = canvas.getHeight() * 2;
        drawMesh();
    }

    private void drawMesh() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setStroke(Color.web("#797979"));
        gc.setLineWidth(1);
        double cellSize = 16.0;
        double width = canvas.getWidth();
        double height = canvas.getHeight();


        for (double x = meshLeftX - currentX; x < meshRightX - currentX; x += cellSize) {
            gc.strokeLine(x, 0 - height, x, height * 2);
        }
        for (double y = meshTopY - currentY; y < meshBottomY - currentY; y += cellSize) {
            gc.strokeLine(0-width, y, width*2, y);
        }

        gc.setFill(Color.RED);
        gc.fillOval(-currentX - 5,-currentY - 5,10,10);
        gc.setFill(Color.web("#000000"));
        gc.setLineWidth(3);
        gc.strokeLine(-currentX,0 - height,-currentX,height * 2);
        gc.strokeLine(0-width, -currentY,width*2,-currentY);
        drawWorkers();
    }

    private void workerClicked(double x, double y) {
        Worker clickedWorker = null;
        for (Worker worker : collection.values()) {
            double workerX = worker.getCoordinates().getX();
            double workerY = worker.getCoordinates().getY();
            if (Math.abs(workerX - currentX) < 1024/ (zoomFactor * 0.5) &&
                    Math.abs(workerY - currentY) < 768/ (zoomFactor * 0.5)) {
                double size = Math.log(worker.getSalary()) * 5;
                double canvasX = (workerX - currentX);
                double canvasY = (workerY - currentY);
                //max distance where worker can be clicked
                double distance = Math.sqrt(Math.pow(x - canvasX, 2) + Math.pow(y - canvasY, 2));
                if (distance <= size / 2) {
                    clickedWorker = worker;
                    break;
                }
            }
        }

        if (clickedWorker != null) {
            WorkerManagementWindow workerManagementWindow = new WorkerManagementWindow("Editing");
            workerManagementWindow.show();
            workerManagementWindow.setWorker(clickedWorker);
        }
    }

    private void firstDraw(){
        for (Worker worker : collection.values()) {
            SimpleDoubleProperty opacity = new SimpleDoubleProperty(0);
            opacityMap.put(worker, opacity);
        }
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                drawWorkers();

                boolean animated = opacityMap.values().stream().allMatch(opacity -> opacity.get() ==1);
                if (animated) { stop();}
            }
        };
        timer.start();
    }

    private void drawWorkers(){
        GraphicsContext gc = canvas.getGraphicsContext2D();
        for (Worker worker : collection.values()) {
            double workerX = worker.getCoordinates().getX();
            double workerY = worker.getCoordinates().getY();
            if (Math.abs(workerX - currentX) < 1024/ (zoomFactor * 0.5) &&
                    Math.abs(workerY - currentY) < 768/ (zoomFactor * 0.5)) {

                Color color = colorMap.get(workerCreatorMap.get(worker.getId()));
                double canvasX = (workerX - currentX);
                double canvasY = (workerY - currentY);
                double size = Math.log(worker.getSalary()) * 5;

                gc.setStroke(color.deriveColor(0, 1, 1, opacityMap.get(worker).get()));
                gc.setLineWidth(2);
                gc.strokeOval(canvasX - size / 2, canvasY - size / 2, size, size);

                Image workerIcon = getWorkerIcon();
                int iconSize = (int) Math.round(size - 4);
                if (iconSize <= 0) {
                    iconSize = 1;
                }

                ImageView imageView = new ImageView(workerIcon);
                imageView.setPreserveRatio(true);
                imageView.setFitWidth(iconSize);
                imageView.setFitHeight(iconSize);
                imageView.setOpacity(opacityMap.get(worker).get());

                SnapshotParameters sp = new SnapshotParameters();
                sp.setFill(Color.TRANSPARENT);

                Image scaledWorkerIcon = imageView.snapshot(sp, null);

                PixelReader pr = scaledWorkerIcon.getPixelReader();
                WritableImage coloredIcon = new WritableImage(iconSize, iconSize);
                PixelWriter pw = coloredIcon.getPixelWriter();
                for (int y = 0; y < scaledWorkerIcon.getHeight(); y++) {
                    for (int x = 0; x < scaledWorkerIcon.getWidth(); x++) {
                        Color pixelColor = pr.getColor(x, y);
                        if (pixelColor.getOpacity() > 0) {
                            pw.setColor(x, y, Color.color(pixelColor.getRed(), pixelColor.getGreen(), pixelColor.getBlue(), opacityMap.get(worker).get()));
                        } else {
                            pw.setColor(x, y, Color.TRANSPARENT);
                        }
                    }
                }
                gc.drawImage(coloredIcon, canvasX - iconSize / 2, canvasY - iconSize / 2);
            }
        }
    }

    private void scroll(Node node){
        node.setOnScroll(event -> {
            double deltaY = event.getDeltaY();
            if (deltaY > 0 && zoomFactor<=2) {
                zoomFactor = Math.min(zoomFactor + 0.1, 2);

            } else if (zoomFactor >= 0.5){
                zoomFactor = Math.max(zoomFactor - 0.1, 0.5);
            }

            redraw();
        });
    }

    private void redraw() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        if (gc == null) return;

        Canvas canvas = gc.getCanvas();
        double w = canvas.getWidth();
        double h = canvas.getHeight();
        gc.setTransform(1, 0, 0, 1, 0, 0);
        gc.clearRect(0, 0, w, h);

        double z = zoomFactor;
        gc.setTransform(zoomFactor, 0, 0, zoomFactor, (w - w * z) / 2.0, (w - w * z) / 2.0);
        drawMesh();
    }

    private void drag(Node canvas) {
        canvas.setOnMouseDragged(event -> {
            if (initialX == null) {
                initialX = event.getX();
                initialY = event.getY();
                return;
            }
            double dragDeltaX = initialX - event.getX();
            double dragDeltaY = initialY - event.getY();
            initialX = event.getX();
            initialY = event.getY();
            currentX += dragDeltaX;
            currentY += dragDeltaY;

            if (dragDeltaX > 0 && meshRightX - currentX < 2048) {
                meshRightX += 2048;
                meshLeftX = meshRightX - 4096;
            } else if (meshLeftX - currentX > -1024) {
                meshLeftX -= 2048;
                meshRightX = meshLeftX + 6144;
            }
            if (dragDeltaY > 0 && meshBottomY - currentY < 1536) {
                meshBottomY += 1536;
                meshTopY = meshBottomY - 3072;
            } else if (meshTopY - currentY > -768) {
                meshTopY -= 768;
                meshBottomY = meshTopY + 3072;
            }
            redraw();
        });
        canvas.setOnMouseReleased(event -> {
            initialX = null;
            initialY = null;
        });
    }

    public void loadCollections(Map<Long, Worker> workerMap,
                                 Map<String, Color> colorMap, Map<Long, String> ownershipMap) {
        if (!this.colorMap.equals(colorMap)) this.colorMap.putAll(colorMap);
        if (!workerCreatorMap.equals(ownershipMap)) workerCreatorMap.putAll(ownershipMap);
        if (workerMap != null) {
            if (!workerMap.equals(this.collection)) {
                collection.clear();
                collection.putAll(workerMap);
            }
        }
    }

    private Image getWorkerIcon() {
        return new Image(Objects.requireNonNull(getClass().getResource("/icons/worker.png")).toExternalForm());
    }
}

