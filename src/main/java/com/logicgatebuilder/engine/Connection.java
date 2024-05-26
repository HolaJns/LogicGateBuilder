package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Connection extends Block {

    public int size = 10;
    public int xEnd, yEnd;
    public Block startBlock, endBlock;

    public void setStart(Block block) {
        this.startBlock = block;
        this.x = startBlock.x;
        this.y = startBlock.y;
    }

    public void setEnd(Block block) {
        this.endBlock = block;
        this.xEnd = endBlock.x;
        this.yEnd = endBlock.y;
    }

    public void refresh() {
        this.x = startBlock.x;
        this.y = startBlock.y;
        this.xEnd = endBlock.x;
        this.yEnd = endBlock.y;
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
