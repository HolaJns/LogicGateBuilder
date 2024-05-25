package com.logicgatebuilder.app;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;


public class Buttons extends Button {
    private String type;

    public Buttons(String type) {
        super(type);
        this.type = type;
        addEventHandler(ActionEvent.ACTION, this::Click);
    }

    public void Click(ActionEvent event) {
        if(!this.type.equals("String") && !this.type.equals("Reset")) {
            Application.canvas.setCurrentSelector(type);
        }
        else if(this.type.equals("String")) Application.canvas.listBlocks();
        else if(this.type.equals("Reset")) Application.canvas.resetCanvas();
    }
}
