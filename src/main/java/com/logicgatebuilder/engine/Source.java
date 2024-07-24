package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.ApplicationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Source extends Block {
    public Source(int x, int y, ApplicationCanvas canvas) {
        this.output = false;
        this.x = x;
        this.y = y;
        this.canvas = canvas;
        super.setId();
    }

    @Override
    public void switchState() {
        this.output = !this.output;
    }

    @Override
    public types getType() {
        return types.SOURCE;
    }

    @Override
    public void draw(GraphicsContext gc) {
        if(input1 != null) calculateOutput();
        if(this.output) gc.setFill(Color.YELLOW);
        else gc.setFill(Color.GRAY);
        gc.fillRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        drawSelectedFrame(gc);
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.strokeRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 20));
        if(this.output) gc.fillText("1", this.x+ canvas.canvasOffsetX, this.y+ canvas.canvasOffsetY);
        else gc.fillText("0", this.x+ canvas.canvasOffsetX, this.y+ canvas.canvasOffsetY);
    }
}
