package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Nand extends Block {
    public Nand(int x, int y) {
        super(x, y);
    }

    @Override
    public void calculateOutput() {
        if(input1 != null && input2 != null) this.output = !(this.input1.output && this.input2.output);
    }

    @Override
    public String getType() {
        return "Nand";
    }

    @Override
    public void draw(GraphicsContext gc) {
        calculateOutput();
        gc.setFill(Color.DARKRED);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 17));
        gc.fillText("NAND",this.x,this.y);
    }
}
