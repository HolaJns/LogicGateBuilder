package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Not extends Block{
    public Not(int x, int y) {
        if(x != -1 && y != -1) {
            this.x = x;
            this.y = y;
        }
        super.setId();
    }

    @Override
    public void calculateOutput() {
        if(input1 != null) this.output = !this.input1.output;
    }

    @Override
    public String getType() {
        return "Not";
    }

    @Override
    public void draw(GraphicsContext gc) {
        calculateOutput();
        gc.setFill(Color.GREEN);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 17));
        gc.fillText("NOT",this.x,this.y);
    }
}
