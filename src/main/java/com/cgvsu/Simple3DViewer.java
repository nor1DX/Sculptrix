package com.cgvsu;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));
        
        Scene scene = new Scene(viewport);
        stage.centerOnScreen();
        stage.setMinWidth(900);
        stage.setMinHeight(400);
        
        stage.setTitle("Sculptrix");
        stage.setScene(scene);
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}