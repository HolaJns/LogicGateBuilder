package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Output extends Block {
    public Output(int x, int y) {
        super(x, y);
    }

    @Override
    public String getType() {
        return "Output";
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(input1 != null) calculateOutput();
        gc.setFill(Color.GRAY);
        if(this.input1 != null) if(this.input1.output) gc.setFill(Color.CYAN);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setFill(Color.BLACK);
        gc.strokeRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
