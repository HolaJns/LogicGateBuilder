package com.logicgatebuilder.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    private BorderPane root;
    private Canvas canvas;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        canvas = new MainCanvas();
        Scene scene = new Scene(root, 1000, 1000);
        scene.setFill(Color.rgb(150,150,160));
        VBox layout = new VBox(0);
        layout.setPadding(new Insets(200));
        layout.getChildren().addAll(canvas);
        root.setCenter(layout);
        stage.setTitle("LogicGate Builder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}