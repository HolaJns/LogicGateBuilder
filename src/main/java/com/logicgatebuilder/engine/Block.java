package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.BlockStaticFactory;
import com.logicgatebuilder.app.MainCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public abstract class Block {
    public enum types {
        AND, NAND, OR, NOR, XOR, NOT, SOURCE, OUTPUT, CONNECTION, DEFAULT, ACTIVATOR
    }

    public static boolean delayed = false;
    public static int delay = 100;
    public boolean output;
    public Block input1 = null;
    public Block input2 = null;
    public int x,y;
    public int size = 50;
    private static int id;
    public int blockId;
    public boolean moving = false;

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

    public types getType() {
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
        return blockId + "," + BlockStaticFactory.translateEnumToString(getType()) + "," + x + "," + y + "," + inp1 + "," + inp2 + "," + output;
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

    public void drawSelectedFrame(GraphicsContext gc) {
        if(moving && getType() != types.CONNECTION) {
            gc.setLineWidth(10);
            gc.setStroke(Color.BLACK);
            gc.strokeRect(this.x - size / 2 + MainCanvas.canvasOffsetX, this.y - size / 2 + MainCanvas.canvasOffsetY, this.size, this.size);
        }
    }

    public static void setGlobalID(int ID) {
        id = ID;
    }

    public static int getGlobalID() {
        return id;
    }
}
