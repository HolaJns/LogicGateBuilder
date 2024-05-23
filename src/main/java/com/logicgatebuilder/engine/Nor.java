package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Nor extends Block {
    public Nor(Block input1, Block input2, int x, int y) {
        super(input1, input2, x, y);
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
        gc.setFill(Color.DARKBLUE);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
