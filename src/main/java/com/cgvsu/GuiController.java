package com.cgvsu;

import com.cgvsu.math.Vector3;
import com.cgvsu.model.Model;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.render_engine.Camera;
import com.cgvsu.render_engine.RenderEngine;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class GuiController {

    final private float TRANSLATION = 0.5F;
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;
    float[] position = {0, 0, 100};
    float[] target = {0, 0, 0};
    private Camera camera = new Camera(
            new Vector3(position),

            new Vector3(target),

            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }


    @FXML
    private void onOpenModelMenuItemClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog((Stage) canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            mesh.prepareForRendering();
            // todo: обработка ошибок
        } catch (IOException exception) {

        }
    }

    @FXML
    private TextField textFieldSx;

    @FXML
    private TextField textFieldSy;

    @FXML
    private TextField textFieldSz;

    @FXML
    private TextField textFieldRx;

    @FXML
    private TextField textFieldRy;

    @FXML
    private TextField textFieldRz;

    @FXML
    private TextField textFieldTx;

    @FXML
    private TextField textFieldTy;

    @FXML
    private TextField textFieldTz;

    @FXML
    public void handleCameraForward(ActionEvent actionEvent) {
        float[] forward = {0F, 0F, -0.5F};
        camera.movePosition(new Vector3(forward));
    }

    @FXML
    public void handleCameraBackward(ActionEvent actionEvent) {
        float[] backward = {0, 0, TRANSLATION};
        camera.movePosition(new Vector3(backward));
    }

    @FXML
    public void handleCameraLeft(ActionEvent actionEvent) {
        float[] left = {TRANSLATION, 0, 0};
        camera.movePosition(new Vector3(left));
    }

    @FXML
    public void handleCameraRight(ActionEvent actionEvent) {
        float[] right = {-TRANSLATION, 0, 0};
        camera.movePosition(new Vector3(right));
    }

    @FXML
    public void handleCameraUp(ActionEvent actionEvent) {
        float[] up = {0, TRANSLATION, 0};
        camera.movePosition(new Vector3(up));
    }

    @FXML
    public void handleCameraDown(ActionEvent actionEvent) {

        float[] down = {0, -TRANSLATION, 0};
        camera.movePosition(new Vector3(down));
    }

    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void handleModelTranslation(ActionEvent actionEvent) {

        if (mesh != null) {
            // текущий положение модели
            Vector3 translation = mesh.getTranslation();

            // значения из текстовых полей, если они не пустые, иначе используем 0
            float tx = textFieldTx.getText().isEmpty() ? 0 : Float.parseFloat(textFieldTx.getText());
            float ty = textFieldTy.getText().isEmpty() ? 0 : Float.parseFloat(textFieldTy.getText());
            float tz = textFieldTz.getText().isEmpty() ? 0 : Float.parseFloat(textFieldTz.getText());

            if (tx == 0 || ty == 0 || tz == 0) {
                showErrorDialog("Ошибка", "Перемещение не может быть нулевым.");
                return;
            }

            // значения из текстовых полей к текущему положению
            float[] newTranslation = {
                    translation.getData(0) + tx,
                    translation.getData(1) + ty,
                    translation.getData(2) + tz
            };

            // новое положение модели
            mesh.setTranslation(new Vector3(newTranslation));
        }
    }

    @FXML
    public void handleModelScale(ActionEvent actionEvent) {
        if (mesh != null) {
            // текущий размер модели
            Vector3 scale = mesh.getScale();

            // значения из текстовых полей, если они не пустые, иначе используем 1
            float sx = textFieldSx.getText().isEmpty() ? 1 : Float.parseFloat(textFieldSx.getText());
            float sy = textFieldSy.getText().isEmpty() ? 1 : Float.parseFloat(textFieldSy.getText());
            float sz = textFieldSz.getText().isEmpty() ? 1 : Float.parseFloat(textFieldSz.getText());

            if (sx <= 0 || sy <= 0 || sz <= 0) {
                showErrorDialog("Ошибка", "Масштабирование не может быть отрицательным или нулевым.");
                return;
            }
            // значения из текстовых полей к текущему размеру
            float[] newScale = {
                    scale.getData(0) * sx,
                    scale.getData(1) * sy,
                    scale.getData(2) * sz
            };

            // новый размер модели
            mesh.setScale(new Vector3(newScale));
        }
    }


    @FXML
    public void handleModelRotate(ActionEvent actionEvent) {
        if (mesh != null) {
            // текущий поворот модели
            Vector3 rotation = mesh.getRotation();

            // значения из текстовых полей, если они не пустые, иначе используем 0
            float rx = textFieldRx.getText().isEmpty() ? 0 : Float.parseFloat(textFieldRx.getText());
            float ry = textFieldRy.getText().isEmpty() ? 0 : Float.parseFloat(textFieldRy.getText());
            float rz = textFieldRz.getText().isEmpty() ? 0 : Float.parseFloat(textFieldRz.getText());

            if (rx == 0 || ry == 0 || rz == 0) {
                showErrorDialog("Ошибка", "Поворот не может быть нулевым.");
                return;
            }

            // значения из текстовых полей к текущему повороту
            float[] newRotation = {
                    rotation.getData(0) + rx,
                    rotation.getData(1) + ry,
                    rotation.getData(2) + rz
            };

            // новый поворот модели
            mesh.setRotation(new Vector3(newRotation));
        }
    }

    public void handleLightColorChange(ActionEvent actionEvent) {
    }

    public void handleModelDelete(ActionEvent actionEvent) {
    }

    public void handleModelHide(ActionEvent actionEvent) {
    }

    public void handleLightCreate(ActionEvent actionEvent) {
    }

    public void handleLightDelete(ActionEvent actionEvent) {
    }

    public void handleLightHide(ActionEvent actionEvent) {
    }
}
