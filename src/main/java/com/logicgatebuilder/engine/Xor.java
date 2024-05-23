package com.logicgatebuilder.engine;

import javafx.scene.paint.Color;

public class Xor extends Block{
    public Xor(Block input1, Block input2, int x, int y) {
        super(input1, input2, x, y);
        color = Color.YELLOW;
    }

    @Override
    public void calculateOutput() {
        this.output = (this.input1.output ^ input2.output);
    }

    @Override
    public String getType() {
        return "Xor";
    }
}
