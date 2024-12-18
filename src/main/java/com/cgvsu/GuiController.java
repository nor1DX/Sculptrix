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
    final private float SCALE = 0.4F;
    final private float ROTATION = 0.4F;
    
    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Model mesh = null;
    float[] position = {0, 0, 100};
    float[] target = {0, 0, 0};
    private Camera camera = new Camera(new Vector3(position),
                                       new Vector3(target),
                                       1.0F, 1,
                                       0.01F, 100);

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

        float[] down = {0, TRANSLATION, 0};
        camera.movePosition(new Vector3(down));
    }


    @FXML
    public void handleModelScaleX(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 scale = mesh.getScale();
            float[] scaleM = {scale.getData(0) + SCALE, scale.getData(1), scale.getData(2)};
            mesh.setScale(new Vector3(scaleM));
        }
    }

    @FXML
    public void handleModelScaleY(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 scale = mesh.getScale();
            float[] scaleM = {scale.getData(0), scale.getData(1) + SCALE, scale.getData(2)};
            mesh.setScale(new Vector3(scaleM));
        }
    }

    @FXML
    public void handleModelScaleZ(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 scale = mesh.getScale();
            float[] scaleM = {scale.getData(0), scale.getData(1), scale.getData(2) + SCALE};
            mesh.setScale(new Vector3(scaleM));
        }
    }

    @FXML
    public void handleModelScaleXNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 scale = mesh.getScale();
            float[] scaleM = {scale.getData(0) - SCALE, scale.getData(1), scale.getData(2)};
            mesh.setScale(new Vector3(scaleM));
        }
    }

    @FXML
    public void handleModelScaleYNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 scale = mesh.getScale();
            float[] scaleM = {scale.getData(0), scale.getData(1) - SCALE, scale.getData(2)};
            mesh.setScale(new Vector3(scaleM));
        }
    }

    @FXML
    public void handleModelScaleZNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 scale = mesh.getScale();
            float[] scaleM = {scale.getData(0), scale.getData(1), scale.getData(2) - SCALE};
            mesh.setScale(new Vector3(scaleM));
        }
    }

    @FXML
    public void handleModelRotateX(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 rotation = mesh.getRotation();
            float[] rotateM = {rotation.getData(0) + ROTATION, rotation.getData(1), rotation.getData(2)};
            mesh.setRotation(new Vector3(rotateM));
        }
    }

    @FXML
    public void handleModelRotateY(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 rotation = mesh.getRotation();
            float[] rotateM = {rotation.getData(0), rotation.getData(1) + ROTATION, rotation.getData(2)};
            mesh.setRotation(new Vector3(rotateM));
        }
    }

    @FXML
    public void handleModelRotateZ(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 rotation = mesh.getRotation();
            float[] rotateM = {rotation.getData(0), rotation.getData(1), rotation.getData(2) + ROTATION};
            mesh.setRotation(new Vector3(rotateM));
        }
    }

    @FXML
    public void handleModelRotateXNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 rotation = mesh.getRotation();
            float[] rotateM = {rotation.getData(0) - ROTATION, rotation.getData(1), rotation.getData(2)};
            mesh.setRotation(new Vector3(rotateM));
        }
    }

    @FXML
    public void handleModelRotateYNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 rotation = mesh.getRotation();
            float[] rotateM = {rotation.getData(0), rotation.getData(1) - ROTATION, rotation.getData(2)};
            mesh.setRotation(new Vector3(rotateM));
        }
    }

    @FXML
    public void handleModelRotateZNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 rotation = mesh.getRotation();
            float[] rotateM = {rotation.getData(0), rotation.getData(1), rotation.getData(2) - ROTATION};
            mesh.setRotation(new Vector3(rotateM));
        }
    }

    @FXML
    public void handleModelTranslateX(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 translation = mesh.getTranslation();
            float[] translateM = {translation.getData(0) + TRANSLATION, translation.getData(1), translation.getData(2)};
            mesh.setTranslation(new Vector3(translateM));
        }
    }

    @FXML
    public void handleModelTranslateY(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 translation = mesh.getTranslation();
            float[] translateM = {translation.getData(0), translation.getData(1) + TRANSLATION, translation.getData(2)};
            mesh.setTranslation(new Vector3(translateM));
        }
    }

    @FXML
    public void handleModelTranslateZ(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 translation = mesh.getTranslation();
            float[] translateM = {translation.getData(0), translation.getData(1), translation.getData(2) + TRANSLATION};
            mesh.setTranslation(new Vector3(translateM));
        }
    }

    @FXML
    public void handleModelTranslateXNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 translation = mesh.getTranslation();
            float[] translateM = {translation.getData(0) - TRANSLATION, translation.getData(1), translation.getData(2)};
            mesh.setTranslation(new Vector3(translateM));
        }
    }

    @FXML
    public void handleModelTranslateYNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 translation = mesh.getTranslation();
            float[] translateM = {translation.getData(0), translation.getData(1) - TRANSLATION, translation.getData(2)};
            mesh.setTranslation(new Vector3(translateM));
        }
    }

    @FXML
    public void handleModelTranslateZNegative(ActionEvent actionEvent) {
        if (mesh != null) {
            Vector3 translation = mesh.getTranslation();
            float[] translateM = {translation.getData(0), translation.getData(1), translation.getData(2) - TRANSLATION};
            mesh.setTranslation(new Vector3(translateM));
        }
    }

}
