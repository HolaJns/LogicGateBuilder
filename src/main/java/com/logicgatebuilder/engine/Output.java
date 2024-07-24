package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.ApplicationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Output extends Block {
    public Output(int x, int y, ApplicationCanvas canvas) {
        super(x, y, canvas);
        super.setId();
    }

    @Override
    public types getType() {
        return types.OUTPUT;
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.RED);
        if(this.input1 != null) if(this.input1.output) gc.setFill(Color.LIME);
        gc.fillRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        gc.setLineWidth(1);
        gc.setStroke(Color.BLACK);
        gc.setFill(Color.BLACK);
        gc.strokeRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        drawSelectedFrame(gc);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 20));
        if(this.input1 != null) {
            if(this.input1.output) gc.fillText("1", this.x+ canvas.canvasOffsetX, this.y+ canvas.canvasOffsetY);
            else gc.fillText("0", this.x+ canvas.canvasOffsetX, this.y+ canvas.canvasOffsetY);
        }
        else gc.fillText("0", this.x+ canvas.canvasOffsetX, this.y+ canvas.canvasOffsetY);
    }
}
