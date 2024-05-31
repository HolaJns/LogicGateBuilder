package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Output extends Block {
    public Output(int x, int y) {
        super(x, y);
        super.setId();
    }

    @Override
    public String getType() {
        return "Output";
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        if(this.input1 != null) if(this.input1.output) gc.setFill(Color.LIME);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setFill(Color.BLACK);
        gc.strokeRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 20));
        if(this.input1 != null) {
            if(this.input1.output) gc.fillText("1", this.x, this.y);
            else gc.fillText("0", this.x, this.y);
        }
        else gc.fillText("0", this.x, this.y);
    }
}
