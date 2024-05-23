package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Source extends Block {
    public Source(int x, int y) {
        this.input1 = null;
        this.input2 = null;
        this.output = false;
        this.x = x;
        this.y = y;
    }

    @Override
    public void switchState() {
        this.output = !this.output;
    }

    @Override
    public String getType() {
        return "Source";
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
