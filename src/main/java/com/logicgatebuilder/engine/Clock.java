package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.ApplicationCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.util.Timer;
import java.util.TimerTask;

public class Clock extends Block {
    private int delay;
    private boolean running = false;
    private Timer timer;
    private TimerTask task;

    public Clock(int x, int y, int delay, ApplicationCanvas canvas) {
        super(x, y, canvas);
        this.delay = delay;
        startClock();
    }

    private void startClock() {
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                output = !output;
            }
        };
        timer.scheduleAtFixedRate(task, 0, delay);
    }

    @Override
    public void calculateOutput() {

    }

    @Override
    public types getType() {
        return types.CLOCK;
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawSelectedFrame(gc);
        gc.setFill(Color.WHITE);
        gc.fillRect(this.x - size / 2 + canvas.canvasOffsetX, this.y - size / 2 + canvas.canvasOffsetY, this.size, this.size);
        gc.setFill(Color.BLACK);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setFont(Font.font("Arial", 17));
        gc.fillText("CLK", this.x + canvas.canvasOffsetX, this.y + canvas.canvasOffsetY);
    }

    public void stopClock() {
        if (task != null) {
            task.cancel();
        }
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        running = false;
    }
}
