package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class TimerManager {
    private static TimerManager instance;
    private final Timeline timeline;

    public TimerManager() {
        timeline = new Timeline(new KeyFrame(Duration.millis(100), event -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static TimerManager getInstance() {
        if (instance == null) {
            instance = new TimerManager();
        }
        return instance;
    }

    private void update() {
        Application.canvas.refreshAllOutputs();
        Application.canvas.redrawCanvas();
    }

    public void stop() {
        timeline.stop();
    }

    public void start() {
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
}
