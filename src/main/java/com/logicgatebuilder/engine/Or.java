package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Or extends Block{
    public Or(int x, int y) {
        super(x, y);
    }

    @Override
    public void calculateOutput() {
        output = input1.output || input2.output;
    }

    @Override
    public String getType() {
        return "Or";
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(input1 != null && input2 != null) calculateOutput();
        gc.setFill(Color.BLUE);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
    }
}
