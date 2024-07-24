package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.ApplicationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Activator extends Block {
    public Activator(int x, int y, ApplicationCanvas canvas) {
        this.output = true;
        this.x = x;
        this.y = y;
        super.setId();
        this.canvas = canvas;
    }

    @Override
    public types getType() {
        return types.ACTIVATOR;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.HOTPINK);
        gc.fillRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        drawSelectedFrame(gc);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFill(Color.WHITE);
        gc.setFont(Font.font("Arial", 20));
        gc.fillText("1", this.x+ canvas.canvasOffsetX, this.y+ canvas.canvasOffsetY);
    }
}
