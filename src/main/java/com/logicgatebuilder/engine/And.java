package com.logicgatebuilder.engine;

import javafx.scene.paint.Color;

public class And extends Block{
    public And(Block input1, Block input2, int x, int y) {
        super(input1, input2, x, y);
        color = Color.RED;
    }

    @Override
    public void calculateOutput() {
        output = input1.output && input2.output;
    }

    @Override
    public String getType() {
        return "And";
    }
}
