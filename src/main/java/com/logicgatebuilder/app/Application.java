package com.logicgatebuilder.app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    private BorderPane root;
    public static MainCanvas canvas;
    private Button AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton, Save, Reset, Load;
    public static FileOperator file;
    public static TextField tf, tf1;

    @Override
    public void start(Stage stage) throws IOException {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        stage.getIcons().add(new Image("file:src/main/resources/com/logicgatebuilder/textures/app/icon.png"));
        stage.setMaxHeight(1010);
        stage.setMaxWidth(1000);
        root = new BorderPane();
        canvas = new MainCanvas();
        Scene scene = new Scene(root, 965, 950);
        AndButton = new Buttons("And");
        NandButton = new Buttons("Nand");
        OrButton = new Buttons("Or");
        NorButton = new Buttons("Nor");
        XorButton = new Buttons("Xor");
        NotButton = new Buttons("Not");
        SourceButton = new Buttons("Source");
        OutputButton = new Buttons("Output");
        ConnectionButton = new Buttons("Connection");
        Save = new Buttons("Save");
        Reset = new Buttons("Reset");
        Load = new Buttons("Load");
        scene.setFill(Color.rgb(150,150,160));
        VBox buttonLayout = new VBox(0);
        VBox canvasLayout = new VBox(0);
        buttonLayout.setSpacing(5);
        buttonLayout.setPadding(new Insets(30,15,15,15));
        buttonLayout.setBackground(new Background(new BackgroundFill(Color.rgb(150,150,150),CornerRadii.EMPTY,Insets.EMPTY)));
        canvasLayout.getChildren().addAll(canvas);
        canvasLayout.setPadding(new Insets(30,0,0,30));
        buttonLayout.getChildren().addAll(AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton);
        GridPane innerGrid = new GridPane();
        tf = new TextField();
        tf.setPromptText("Save As");
        tf1 = new TextField();
        tf1.setPromptText("Load From");
        innerGrid.setHgap(10);
        innerGrid.setVgap(12);
        innerGrid.add(Save, 1, 0);
        innerGrid.add(Reset, 0, 0);
        innerGrid.add(Load, 1, 1);
        innerGrid.add(tf, 2, 0);
        innerGrid.add(tf1, 2, 1);
        innerGrid.setAlignment(Pos.CENTER);
        innerGrid.setPadding(new Insets(15,15,15,15));
        innerGrid.setBackground(new Background(new BackgroundFill(Color.rgb(150,150,150),CornerRadii.EMPTY,Insets.EMPTY)));
        grid.add(canvasLayout, 0, 0);
        grid.add(buttonLayout, 1, 0);
        grid.add(innerGrid, 0, 1);
        grid.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100),CornerRadii.EMPTY,Insets.EMPTY)));
        root.setCenter(grid);
        stage.setTitle("LogicGate Builder");
        stage.setScene(scene);
        stage.show();
    }

    public static void setFile(FileOperator fileOperator) {
        file = fileOperator;
    }

    public static void main(String[] args) {
        launch();
    }
}