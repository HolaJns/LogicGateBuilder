package com.logicgatebuilder.engine;

import javafx.scene.paint.Color;

public class Or extends Block{
    public Or(Block input1, Block input2, int x, int y) {
        super(input1, input2, x, y);
        color = Color.BLUE;
    }

    @Override
    public void calculateOutput() {
        output = input1.output || input2.output;
    }

    @Override
    public String getType() {
        return "Or";
    }
}
