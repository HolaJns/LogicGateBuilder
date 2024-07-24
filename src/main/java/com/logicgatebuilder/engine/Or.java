package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.ApplicationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Or extends Block{
    public Or(int x, int y, ApplicationCanvas canvas) {
        super(x, y, canvas);
        super.setId();
    }

    @Override
    public void calculateOutput() {
        if(input1 != null && input2 != null) output = input1.output || input2.output;
    }

    @Override
    public types getType() {
        return types.OR;
    }

    @Override
    public void draw(GraphicsContext gc) {
        calculateOutput();
        drawSelectedFrame(gc);
        gc.setFill(Color.BLUE);
        gc.fillRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 17));
        gc.fillText("OR",this.x+ canvas.canvasOffsetX,this.y+ canvas.canvasOffsetY);
    }
}
