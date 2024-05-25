package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Source extends Block {
    public Source(int x, int y) {
        this.output = true;
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
        if(input1 != null) calculateOutput();
        if(this.output) gc.setFill(Color.YELLOW);
        else gc.setFill(Color.WHITE);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setFill(Color.BLACK);
        gc.strokeRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
