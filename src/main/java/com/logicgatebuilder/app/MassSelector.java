package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public abstract class MassSelector {
    private static int endMassSelectorX, endMassSelectorY;
    private static int startMassSelectorX, startMassSelectorY;
    private static ArrayList<Block> massSelect = new ArrayList<>();
    private static int initialMouseX, initialMouseY;
    private static boolean isDraggingSelection = false;
    private static ApplicationCanvas canvas;
    private static GraphicsContext gc;


    public static void massSelectorStart(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY && massSelect.isEmpty()) {
            startMassSelectorX = (int) e.getX();
            startMassSelectorY = (int) e.getY();
        } else if (!massSelect.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
            initialMouseX = (int) e.getX();
            initialMouseY = (int) e.getY();
            isDraggingSelection = true;
        }
    }

    public static void massSelectorSelect(MouseEvent e) {
        if (massSelect.isEmpty() && e.isShiftDown()) {
            canvas.timer.stop();
            endMassSelectorX = (int) e.getX();
            endMassSelectorY = (int) e.getY();
            canvas.redrawCanvas();
            gc.setStroke(Color.BLUE);
            gc.setFill(Color.color(1, 1, 0, 0.5));

            int x = Math.min(startMassSelectorX, endMassSelectorX);
            int y = Math.min(startMassSelectorY, endMassSelectorY);
            int width = Math.abs(endMassSelectorX - startMassSelectorX);
            int height = Math.abs(endMassSelectorY - startMassSelectorY);

            gc.strokeRect(x, y, width, height);
            gc.fillRect(x, y, width, height);
        } else if (isDraggingSelection) {
            int deltaX = (int) e.getX() - initialMouseX;
            int deltaY = (int) e.getY() - initialMouseY;

            for (Block block : massSelect) {
                block.x += deltaX;
                block.y += deltaY;
            }

            initialMouseX = (int) e.getX();
            initialMouseY = (int) e.getY();
            canvas.redrawCanvas();
        }
        canvas.setPrev(e.getX(),e.getY());
    }

    public static void massSelectorEnd(MouseEvent e) {
        if (massSelect.isEmpty() && e.isShiftDown()) {
            int x1 = Math.min(startMassSelectorX, endMassSelectorX) - canvas.canvasOffsetX;
            int y1 = Math.min(startMassSelectorY, endMassSelectorY) - canvas.canvasOffsetY;
            int x2 = Math.max(startMassSelectorX, endMassSelectorX) - canvas.canvasOffsetX;
            int y2 = Math.max(startMassSelectorY, endMassSelectorY) - canvas.canvasOffsetY;

            ArrayList<Block> temp = new ArrayList<>();
            for (Block block : BlockMemory.getBlocks()) {
                int blockLeft = block.x - block.size / 2;
                int blockRight = block.x + block.size / 2;
                int blockTop = block.y - block.size / 2;
                int blockBottom = block.y + block.size / 2;

                if (blockRight >= x1 && blockLeft <= x2 && blockBottom >= y1 && blockTop <= y2) {
                    block.moving = true;
                    temp.add(block);
                }
            }
            massSelect = temp;
            UndoManager.addAction(getMassSelect());
        } else {
            for (Block block : massSelect) block.moving = false;
            massSelect = new ArrayList<>();
            isDraggingSelection = false;
            canvas.timer.start();
        }
    }

    public static void initCanvas(ApplicationCanvas cv, GraphicsContext gr) {
        canvas = cv;
        gc = gr;
    }

    public static ArrayList<Block> getMassSelect() {
        ArrayList<Block> temp = new ArrayList<>();
        for (Block block : BlockMemory.getBlocks()) {
            Block tmp = BlockFactory.create(block.getType(), block.x, block.y, block.blockId, canvas);
            tmp.input1 = block.input1;
            tmp.input2 = block.input2;
            tmp.output = block.output;
            if (block.moving) temp.add(tmp);
        }
        return temp;
    }
}
