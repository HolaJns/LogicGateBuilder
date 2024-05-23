package com.logicgatebuilder.app;

import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;


public class Buttons extends Button {
    private String type;

    public Buttons(String type) {
        super(type);
        this.type = type;
        addEventHandler(ActionEvent.ACTION, this::Click);
    }

    public void Click(ActionEvent event) {
        Application.canvas.setCurrentSelector(type);
    }
}
