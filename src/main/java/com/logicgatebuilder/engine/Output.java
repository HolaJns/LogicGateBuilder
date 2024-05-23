package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Output extends Block {
    public Output(Block input1, Block input2, int x, int y) {
        super(input1, input2, x, y);
    }

    @Override
    public String getType() {
        return "Output";
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.GRAY);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
