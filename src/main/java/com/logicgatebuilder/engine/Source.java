package com.logicgatebuilder.engine;

import javafx.scene.paint.Color;

public class Source extends Block{
    public Source(int x, int y) {
        this.input1 = null;
        this.input2 = null;
        this.output = false;
        color = Color.BLACK;
        this.x = x;
        this.y = y;
    }

    @Override
    public void switchState() {
        this.output = !this.output;
    }

    @Override
    public String getType() {
        return "Src";
    }
}
