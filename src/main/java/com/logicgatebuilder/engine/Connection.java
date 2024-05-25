package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Connection extends Block {

    public int size = 10;
    public int xEnd, yEnd;

    public void setStart(int xStart, int yStart) {
        this.x = xStart;
        this.y = yStart;
    }

    public void setEnd(int xEnd, int yEnd) {
        this.xEnd = xEnd;
        this.yEnd = yEnd;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.HOTPINK);
        gc.strokeLine(x, y, xEnd, yEnd);
    }

    @Override
    public String getType() {
        return "Connection";
    }
}
