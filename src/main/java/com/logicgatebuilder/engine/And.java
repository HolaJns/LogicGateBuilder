package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.ApplicationCanvas;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import javax.swing.Timer;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

public class And extends Block{
    public And(int x, int y, ApplicationCanvas canvas) {
        super(x, y, canvas);
        super.setId();
    }

    @Override
    public void calculateOutput() {
        if (isCalculating) {
            return;
        }
        isCalculating = true;
        Timeline delayTimeline = new Timeline(new KeyFrame(Duration.millis(delay), event -> {
            if (input1 != null && input2 != null) output = input1.output && input2.output;
            isCalculating = false;
        }));
        delayTimeline.setCycleCount(1);
        delayTimeline.play();
    }

    @Override
    public types getType() {
        return types.AND;
    }

    @Override
    public void draw(GraphicsContext gc) {
        calculateOutput();
        drawSelectedFrame(gc);
        gc.setFill(Color.RED);
        gc.fillRect(this.x-size/2+ canvas.canvasOffsetX, this.y-size/2+ canvas.canvasOffsetY, this.size, this.size);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 17));
        gc.fillText("AND",this.x+ canvas.canvasOffsetX,this.y+ canvas.canvasOffsetY);
    }
}
