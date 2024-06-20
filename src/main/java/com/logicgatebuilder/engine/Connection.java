package com.logicgatebuilder.engine;

import com.logicgatebuilder.app.MainCanvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Connection extends Block {

    public int size = 10;
    public int xEnd, yEnd;
    public Block startBlock, endBlock;

    public void setStart(Block block) {
        this.startBlock = block;
        this.x = startBlock.x;
        this.y = startBlock.y;
    }

    public void setEnd(Block block) {
        this.endBlock = block;
        this.xEnd = endBlock.x;
        this.yEnd = endBlock.y;
    }

    public void refresh() {
        this.x = startBlock.x;
        this.y = startBlock.y;
        this.xEnd = endBlock.x;
        this.yEnd = endBlock.y;
    }

    @Override
    public void draw(GraphicsContext gc) {
        drawArrow(gc,x+ MainCanvas.canvasOffsetX,y+MainCanvas.canvasOffsetY,xEnd+MainCanvas.canvasOffsetX,yEnd+MainCanvas.canvasOffsetY);
    }

    private void drawArrow(GraphicsContext gc, double startX, double startY, double endX, double endY) {
        if(!startBlock.output) gc.setStroke(Color.BLACK);
        else gc.setStroke(Color.LIME);
        gc.setLineWidth(2);
        gc.strokeLine(startX, startY, endX, endY);
        double midX = (startX + endX) / 2;
        double midY = (startY + endY) / 2;
        double angle = Math.atan2(endY - startY, endX - startX);
        double arrowLength = 10;
        double arrowAngle = Math.toRadians(45);
        double x1 = midX - arrowLength * Math.cos(angle - arrowAngle);
        double y1 = midY - arrowLength * Math.sin(angle - arrowAngle);
        double x2 = midX - arrowLength * Math.cos(angle + arrowAngle);
        double y2 = midY - arrowLength * Math.sin(angle + arrowAngle);

        gc.strokeLine(midX, midY, x1, y1);
        gc.strokeLine(midX, midY, x2, y2);
        gc.setStroke(Color.BLACK);
    }


    @Override
    public types getType() {
        return types.CONNECTION;
    }
}
