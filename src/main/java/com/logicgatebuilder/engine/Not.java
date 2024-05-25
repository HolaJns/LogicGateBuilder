package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Not extends Block{
    public Not(int x, int y) {
        if(x != -1 && y != -1) {
            this.x = x;
            this.y = y;
        }
    }

    @Override
    public void calculateOutput() {
        this.output = !this.input1.output;
    }

    @Override
    public String getType() {
        return "Not";
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(input1 != null) calculateOutput();
        gc.setFill(Color.GREEN);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
