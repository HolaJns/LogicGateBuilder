package com.logicgatebuilder.app;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;

public class Application extends javafx.application.Application {
    private BorderPane root;
    public static ApplicationCanvas canvas;
    private Button AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ConnectionButton, ActivatorButton, Save, Reset, Load;
    public static FileOperator file;
    public static TextField tf, tf1;
    public GridPane grid, innerGrid;
    public static boolean darkMode = true;
    public static boolean dynamicConnections = false;

    @Override
    public void start(Stage stage) throws IOException {
        stage.getIcons().add(new Image("file:src/main/resources/com/logicgatebuilder/textures/app/icon2.png"));
        stage.setMaxHeight(1010);
        stage.setMaxWidth(1000);
        root = new BorderPane();
        canvas = new ApplicationCanvas();
        buildMenuBar();
        Scene scene = new Scene(root, 965, 950);
        scene.setFill(Color.rgb(150,150,160));
        stage.setTitle("LogicGate Builder");
        stage.setScene(scene);
        buildButtons();
        buildTextFields();
        buildInnerGrid();
        buildGrid();
        stage.show();
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.isControlDown() && event.getCode().toString().equalsIgnoreCase("s")) {
                    save();
                }
                if(event.isControlDown() && event.getCode().toString().equalsIgnoreCase("z")) {
                    UndoManager.undo();
                    Application.canvas.redrawCanvas();
                }
            }
        });
    }

    private void buildMenuBar() {
        MenuBar menuBar = new MenuBar();
        Menu fileM = new Menu("File");
        Menu settings = new Menu("Settings");
        Menu view = new Menu("View");
        MenuItem save = new MenuItem("Save");
        MenuItem saveAs = new MenuItem("Save as");
        MenuItem importP = new MenuItem("Import");
        MenuItem rename = new MenuItem("Rename");
        MenuItem toggleCords = new MenuItem("Toggle coordinates");
        MenuItem dark = new MenuItem("Dark");
        MenuItem light = new MenuItem("Light");
        Menu subThemes = new Menu("Themes");
        subThemes.getItems().addAll(dark, new SeparatorMenuItem(), light);
        Menu subArrows = new Menu("Arrows");
        MenuItem dynamic = new MenuItem("Dynamic");
        MenuItem dfault = new MenuItem("Static");
        subArrows.getItems().addAll(dynamic, dfault);
        fileM.getItems().addAll(save, new SeparatorMenuItem(), saveAs, new SeparatorMenuItem(), importP, new SeparatorMenuItem(), rename);
        settings.getItems().addAll(toggleCords, new SeparatorMenuItem(), subThemes, new SeparatorMenuItem(), subArrows);
        menuBar.getMenus().addAll(fileM, view, settings);
        root.setTop(menuBar);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Logic Gate Files", "*.g8"));

        toggleCords.setOnAction(e-> {
            canvas.cordsOff = !canvas.cordsOff;
            canvas.redrawCanvas();
        });
        dark.setOnAction(e -> {
            darkMode = true;
            buildButtons();
            buildInnerGrid();
            buildGrid();
        });
        light.setOnAction(e -> {
            darkMode = false;
            buildButtons();
            buildInnerGrid();
            buildGrid();
        });
        save.setOnAction(e -> {
            save();
        });
        dfault.setOnAction(e -> {
            dynamicConnections = false;
        });
        dynamic.setOnAction(e -> {
            dynamicConnections = true;
        });
        importP.setOnAction(e -> {
            Application.file = new FileOperator(fileChooser.showOpenDialog(null));
            Application.file.load();
        });
        rename.setOnAction(e -> {
            if(file == null) return;
            TextInputDialog dialog = new TextInputDialog();
            dialog.setHeaderText(null);
            dialog.setGraphic(null);
            dialog.setTitle("Rename");
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                file.renameTo(result.get());
                save();
            }
            dialog.close();
        });
    }

    private void buildButtons() {
        AndButton = new Buttons("And");
        NandButton = new Buttons("Nand");
        OrButton = new Buttons("Or");
        NorButton = new Buttons("Nor");
        XorButton = new Buttons("Xor");
        NotButton = new Buttons("Not");
        SourceButton = new Buttons("Source");
        OutputButton = new Buttons("Output");
        ConnectionButton = new Buttons("Connection");
        ActivatorButton = new Buttons("Activator");
        Reset = new Buttons("Reset");
    }

    private void buildGrid() {
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(12);
        VBox buttonLayout = new VBox(0);
        VBox canvasLayout = new VBox(0);
        buttonLayout.setSpacing(5);
        buttonLayout.setPadding(new Insets(30,15,15,15));
        buttonLayout.setBackground(new Background(new BackgroundFill(Color.rgb(150,150,150),CornerRadii.EMPTY,Insets.EMPTY)));
        canvasLayout.getChildren().addAll(canvas);
        canvasLayout.setPadding(new Insets(30,0,0,30));
        buttonLayout.getChildren().addAll(AndButton, NandButton, OrButton, NorButton, XorButton, NotButton, SourceButton, OutputButton, ActivatorButton, ConnectionButton);
        grid.add(canvasLayout, 0, 0);
        grid.add(buttonLayout, 1, 0);
        buildButtons();
        grid.add(innerGrid, 0, 1);
        grid.setBackground(new Background(new BackgroundFill(Color.rgb(100,100,100),CornerRadii.EMPTY,Insets.EMPTY)));
        if(darkMode) {
            buttonLayout.setBackground(new Background(new BackgroundFill(Color.rgb(20,20,20),CornerRadii.EMPTY,Insets.EMPTY)));
            grid.setBackground(new Background(new BackgroundFill(Color.rgb(20,20,20),CornerRadii.EMPTY,Insets.EMPTY)));
        }
        root.setCenter(grid);
    }

    private void buildInnerGrid() {
        innerGrid = new GridPane();
        innerGrid.setHgap(10);
        innerGrid.setVgap(12);
        innerGrid.add(Reset, 0, 0);
        innerGrid.add(tf, 2, 0);
        innerGrid.add(tf1, 2, 1);
        innerGrid.setAlignment(Pos.CENTER);
        innerGrid.setPadding(new Insets(15,15,15,15));
        innerGrid.setBackground(new Background(new BackgroundFill(Color.rgb(150,150,150),CornerRadii.EMPTY,Insets.EMPTY)));
        if(darkMode) innerGrid.setBackground(new Background(new BackgroundFill(Color.rgb(20,20,20),CornerRadii.EMPTY,Insets.EMPTY)));
    }

    private void buildTextFields() {
        tf = new TextField();
        tf.setPromptText("Save As");
        tf1 = new TextField();
        tf1.setPromptText("Load From");
    }

    public void save() {
        if(file != null) {
            file.write();
        }
        else {
            try {
                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("Save File As");
                directoryChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
                String temp;
                TextInputDialog dialog = new TextInputDialog();
                dialog.setHeaderText(null);
                dialog.setGraphic(null);
                dialog.setTitle("Save as");
                Optional<String> result = dialog.showAndWait();
                dialog.close();
                temp = directoryChooser.showDialog(null).getAbsolutePath();
                if (result.isPresent()) {
                    if(!result.get().isEmpty() && result != null) temp += "\\" + result.get() + ".g8";
                } else {
                    temp += "\\" + (int)(Math.random() * 20000) + ".g8";
                    System.out.printf(temp);
                }
                file = new FileOperator(new File(temp));
                file.write();
            } catch (NullPointerException e) {}
        }
    }

    public static void main(String[] args) {
        launch();
    }
}