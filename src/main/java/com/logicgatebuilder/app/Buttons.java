package com.logicgatebuilder.app;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;


public class Buttons extends Button {
    private String type;

    public Buttons(String type) {
        super(type);
        this.type = type;
        addEventHandler(ActionEvent.ACTION, this::Click);
        setMaxHeight(40);
        setMaxWidth(100);
        setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-background-color: #dddddd;");
        setHover(true);
    }

    public void Click(ActionEvent event) {
        if(!this.type.equals("String") && !this.type.equals("Reset")) {
            Application.canvas.setCurrentSelector(type);
        }
        else if(this.type.equals("String")) Application.canvas.listBlocks();
        else if(this.type.equals("Reset")) Application.canvas.resetCanvas();
    }
}
