package com.logicgatebuilder.app;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    private BorderPane root;
    public static MainCanvas canvas;
    private Button AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton, String, Reset;

    @Override
    public void start(Stage stage) {
        root = new BorderPane();
        canvas = new MainCanvas();
        AndButton = new Buttons("And");
        NandButton = new Buttons("Nand");
        OrButton = new Buttons("Or");
        NorButton = new Buttons("Nor");
        XorButton = new Buttons("Xor");
        NotButton = new Buttons("Not");
        SourceButton = new Buttons("Source");
        OutputButton = new Buttons("Output");
        ConnectionButton = new Buttons("Connection");
        String = new Buttons("String");
        Reset = new Buttons("Reset");
        Scene scene = new Scene(root, 1000, 1200);
        scene.setFill(Color.rgb(150,150,160));
        VBox layout = new VBox(0);
        layout.setPadding(new Insets(200));
        layout.getChildren().addAll(canvas, AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton, String, Reset);
        root.setCenter(layout);
        stage.setTitle("LogicGate Builder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}