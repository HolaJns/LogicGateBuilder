package com.logicgatebuilder.engine;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Source extends Block {
    public Source(int x, int y) {
        this.output = false;
        this.x = x;
        this.y = y;
    }

    @Override
    public void switchState() {
        this.output = !this.output;
    }

    @Override
    public String getType() {
        return "Source";
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(input1 != null) calculateOutput();
        if(this.output) gc.setFill(Color.YELLOW);
        else gc.setFill(Color.GRAY);
        gc.fillRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setFill(Color.BLACK);
        gc.strokeRect(this.x-size/2, this.y-size/2, this.size, this.size);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 20));
        if(this.output) gc.fillText("1", this.x, this.y);
        else gc.fillText("0", this.x, this.y);
    }
}
