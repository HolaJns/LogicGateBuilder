package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.MainCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Xor extends Block{
    public Xor(int x, int y) {
        super(x, y);
        super.setId();
    }

    @Override
    public void calculateOutput() {
        if(input1 != null && input2 != null) this.output = (this.input1.output ^ input2.output);
    }

    @Override
    public types getType() {
        return types.XOR;
    }

    @Override
    public void draw(GraphicsContext gc) {
        calculateOutput();
        drawSelectedFrame(gc);
        gc.setFill(Color.ORANGE);
        gc.fillRect(this.x-size/2+MainCanvas.canvasOffsetX, this.y-size/2+MainCanvas.canvasOffsetY, this.size, this.size);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 17));
        gc.fillText("XOR",this.x+ MainCanvas.canvasOffsetX,this.y+MainCanvas.canvasOffsetY);
    }
}
