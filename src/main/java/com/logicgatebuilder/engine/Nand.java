package com.logicgatebuilder.engine;

import javafx.scene.paint.Color;

public class Nand extends Block {
    public Nand(Block input1, Block input2, int x, int y) {
        super(input1, input2, x, y);
        color = Color.DARKRED;
    }

    @Override
    public void calculateOutput() {
        this.output = !(this.input1.output && this.input2.output);
    }

    @Override
    public String getType() {
        return "Nand";
    }
}
