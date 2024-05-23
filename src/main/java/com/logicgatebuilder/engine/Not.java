package com.logicgatebuilder.engine;

import javafx.scene.paint.Color;

public class Not extends Block{
    public Not(Block input, int x, int y) {
        this.input1 = input;
        calculateOutput();
        if(x != -1 && y != -1) {
            this.x = x;
            this.y = y;
        }
        color = Color.GREEN;
    }

    @Override
    public void calculateOutput() {
        this.output = !this.input1.output;
    }

    @Override
    public String getType() {
        return "Not";
    }
}
