package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Nor extends Block {
    public Nor(int x, int y) {
        super(x, y);
    }

    @Override
    public void calculateOutput() {
        this.output = !(this.input1.output || this.input2.output);
    }

    @Override
    public String getType() {
        return "Nor";
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(input1 != null && input2 != null) calculateOutput();
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
