package com.logicgatebuilder.app;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.Objects;


public class Buttons extends Button {
    private String type;

    public Buttons(String type) {
        super(type);
        this.type = type;
        addEventHandler(ActionEvent.ACTION, this::Click);
        setMaxHeight(40);
        setMaxWidth(100);
        setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");
        setHover(true);
        final String IDLE_BUTTON_STYLE = "-fx-background-color: #dddddd;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
        setStyle(IDLE_BUTTON_STYLE);
        setOnMouseEntered(e -> setStyle(HOVERED_BUTTON_STYLE));
        setOnMouseExited(e -> setStyle(IDLE_BUTTON_STYLE));
    }

    public void Click(ActionEvent event) {
        if(this.type.equals("Save")) {
            if (Objects.equals(Application.tf.getText(), "")) return;
            FileGenerator fileGenerator = new FileGenerator(Application.tf.getText(),Application.canvas);
            Application.setFile(fileGenerator);
            Application.file.write();
        }
        else if(this.type.equals("Reset")) Application.canvas.resetCanvas();
        else Application.canvas.setCurrentSelector(type);
    }
}
