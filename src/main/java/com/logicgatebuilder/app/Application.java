package com.logicgatebuilder.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Application extends javafx.application.Application {
    private BorderPane root;
    public static MainCanvas canvas;
    private Button AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton, String, Reset;

    @Override
    public void start(Stage stage) {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        root = new BorderPane();
        canvas = new MainCanvas();
        Scene scene = new Scene(root, 1000, 1200);
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
        scene.setFill(Color.rgb(150,150,160));
        VBox buttonLayout = new VBox(0);
        VBox canvasLayout = new VBox(0);
        VBox configsLayout = new VBox(0);
        buttonLayout.setSpacing(5);
        buttonLayout.setPadding(new Insets(30,0,0,0));
        canvasLayout.getChildren().addAll(canvas);
        canvasLayout.setPadding(new Insets(30,10,0,30));
        buttonLayout.getChildren().addAll(AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton);
        configsLayout.getChildren().addAll(String, Reset);
        configsLayout.setPadding(new Insets(0,0,0,30));
        configsLayout.setAlignment(Pos.CENTER);
        grid.add(canvasLayout, 0, 0);
        grid.add(buttonLayout, 1, 0);
        grid.add(configsLayout, 0, 1);
        root.setCenter(grid);
        stage.setTitle("LogicGate Builder");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}