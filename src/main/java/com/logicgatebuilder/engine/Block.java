package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;

public abstract class Block {
    public enum types {
        AND, NAND, OR, NOR, XOR, NOT, SOURCE, OUTPUT, CONNECTION
    }
    public boolean output;
    public Block input1 = null;
    public Block input2 = null;
    public int x,y;
    public int size = 50;
    private static int id;
    public int blockId;

    public Block(int x, int y) {
        if (x != -1 && y != -1) {
            this.x = x;
            this.y = y;
        }
        setId();
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

    public void setInput(Block input) {
        if (this.input1 == null) this.input1 = input;
        else if (this.input2 == null) this.input2 = input;
        if(this.input1 != null && this.input2 != null) calculateOutput();
    }

    public void setId() {
        blockId = id;
        id++;
    }

    public String toString() {
        int inp1 = -1;
        int inp2 = -1;
        if(input1 != null) inp1 = input1.blockId;
        if(input2 != null) inp2 = input2.blockId;
        return blockId + "," + getType() + "," + x + "," + y + "," + inp1 + "," + inp2 + "," + output;
    }

    public void setInput1(Block input) {
        this.input1 = input;
    }

    public void setInput2(Block input) {
        this.input2 = input;
    }

    public void forceID(int ID) {
        this.blockId = ID;
    }

    public static void setGlobalID(int ID) {
        id = ID;
    }

    public static int getGlobalID() {
        return id;
    }
}
