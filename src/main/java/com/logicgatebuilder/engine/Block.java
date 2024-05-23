package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;

public abstract class Block {
    protected boolean output;
    protected Block input1;
    protected Block input2;
    public int x,y;
    public int size = 50;

    public Block(Block input1, Block input2, int x, int y) {
        this.input1 = input1;
        this.input2 = input2;
        if (input1 != null && input2 != null) {
            calculateOutput();
            }
        if (x != -1 && y != -1) {
            this.x = x;
            this.y = y;
        }
    }

    protected Block() {
    }

    public void calculateOutput() {
        return;
    }

    public void switchState() {
        return;
    }

    public String getType() {
        return null;
    }

    public void draw(GraphicsContext gc) {
        return;
    }


}
