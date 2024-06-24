package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.ArrayList;
import java.util.Collections;

public class MainCanvas extends Canvas {
    private final GraphicsContext graphics;
    private Block.types currentSelector = Block.types.DEFAULT;
    private Block movementPointer = null;
    private boolean startSelected = false;
    public static int canvasOffsetX = 400, canvasOffsetY = 400, deltaX = 0, deltaY = 0;
    private static int lastX, lastY;

    public MainCanvas() {
        setWidth(800);
        setHeight(800);
        graphics = getGraphicsContext2D();
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        addEventHandler(MouseEvent.MOUSE_CLICKED, this::clicked);
        addEventHandler(MouseEvent.MOUSE_MOVED, this::move);
        addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragging);
        addEventHandler(MouseEvent.MOUSE_PRESSED, this::initiateCanvasMove);
        addEventHandler(MouseEvent.MOUSE_PRESSED, this::massSelectorStart);
        addEventHandler(MouseEvent.MOUSE_RELEASED, this::massSelectorEnd);
        redrawCanvas();
    }

    private void initiateCanvasMove(MouseEvent e) {
        drawCoords(e);
        if (e.getButton() == MouseButton.SECONDARY) {
            lastX = (int) e.getX();
            lastY = (int) e.getY();
        }
    }

    private void clicked(MouseEvent e) {
        placeBlock(e);
    }

    private void placeBlock(MouseEvent e) {
        // check secondary key usage
        if (e.getButton() == MouseButton.SECONDARY) {
            // stop connection logic
            if (currentSelector.equals(Block.types.CONNECTION) && e.isControlDown()) {
                currentSelector = Block.types.DEFAULT;
                movementPointer = null;
                startSelected = false;
            }
        }
        // check primary key usage
        // place block of type currentSelector if not DEFAULT or CONNECTION
        else if (currentSelector != Block.types.DEFAULT && currentSelector != Block.types.CONNECTION) {
            Block newBlock = BlockStaticFactory.create(currentSelector, (int) e.getX() - canvasOffsetX, (int) e.getY() - canvasOffsetY, Block.getGlobalID());
            BlockMemory.add(newBlock);
            ArrayList<Block> removeHelper = new ArrayList<>();
            removeHelper.add(BlockStaticFactory.create(newBlock.getType(), -1000, -1000, newBlock.blockId));
            UndoManager.addAction(removeHelper);
            currentSelector = Block.types.DEFAULT;
        }
        // place and concat connection
        else if (currentSelector.equals(Block.types.CONNECTION)) {
            // if start is already selected, find end Block
            if (startSelected) {
                Block block = checkInside((int) e.getX(), (int) e.getY());
                if (block != null) {
                    ((Connection) movementPointer).setEnd(block);
                    movementPointer.draw(graphics);
                    startSelected = false;
                    BlockMemory.add(movementPointer);
                    initConnection((Connection) movementPointer);
                    movementPointer = null;
                    currentSelector = Block.types.DEFAULT;
                }
            }
            // if no start is selected, find one
            else {
                movementPointer = new Connection();
                Block block = checkInside((int) e.getX(), (int) e.getY());
                if (block != null && !block.getType().equals(Block.types.OUTPUT)) {
                    ((Connection) movementPointer).setStart(block);
                    startSelected = true;
                }
            }
        }
        // delete, swap and move logic
        else {
            initializeMovementPointer(e);
            if (movementPointer != null) {
                // delete block if control is down. Removes references and connections as well
                if (e.isControlDown()) {
                    deleteBlockOnMouse(movementPointer);
                    for (Block block : BlockMemory.getBlocks()) {
                        if (block != null) {
                            if ((block).getType().equals(Block.types.CONNECTION)) {
                                if (((Connection) block).startBlock == movementPointer || ((Connection) block).endBlock == movementPointer) {
                                    BlockMemory.setByIndex(null, BlockMemory.getBlocks().indexOf(block));
                                }
                            } else {
                                if (block.input1 == movementPointer) block.input1 = null;
                                if (block.input2 == movementPointer) block.input2 = null;
                            }
                        }
                    }
                    BlockMemory.removeBlock(null);
                    movementPointer = null;
                    refreshAllOutputs();
                }
                // swap state of source on mouse click
                else if (movementPointer.getType().equals(Block.types.SOURCE) && !e.isShiftDown() && !e.isControlDown()) {
                    movementPointer.switchState();
                    movementPointer = null;
                }
                else movementPointer = null;
            }
        }
        refreshAllOutputs();
        redrawCanvas();
        drawCoords(e);
    }

    private void initializeMovementPointer(MouseEvent e) {
        Block block = checkInside((int) e.getX(), (int) e.getY());
        if (movementPointer == null) {
            movementPointer = block;
        } else movementPointer = null;
    }

    private void move(MouseEvent e) {
        connectionMove(e);
        redrawCanvas();
        drawCoords(e);
    }

    private void connectionMove(MouseEvent e) {
        if (currentSelector == Block.types.CONNECTION && startSelected) {
            ((Connection) movementPointer).xEnd = (int) e.getX() - canvasOffsetX;
            ((Connection) movementPointer).yEnd = (int) e.getY() - canvasOffsetY;
            movementPointer.draw(graphics);
        }
    }

    int prevX = 0, prevY = 0;
    public void drawCoords(MouseEvent e) {
        int temp = String.valueOf((prevX)).length() + String.valueOf((prevY)).length();
        graphics.setFill(Color.BLACK);
        graphics.fillRect(32,30, 31 + 12*(temp-1),30);
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        graphics.setTextAlign(TextAlignment.LEFT);
        graphics.setFill(Color.BLACK);
        graphics.setFill(Color.WHITE);
        graphics.fillText(prevX + ", " + prevY, 40, 50);
        prevX = ((int)e.getX()-canvasOffsetX)/10;
        prevY = ((int)e.getY()-canvasOffsetY)/-10;
    }

    private void dragging(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) moveCanvas(e);
        if (e.getButton() == MouseButton.PRIMARY) massSelectorSelect(e);
    }

    private int endMassSelectorX, endMassSelectorY;
    private int startMassSelectorX, startMassSelectorY;
    private ArrayList<Block> massSelect = new ArrayList<>();
    private int initialMouseX, initialMouseY;
    private boolean isDraggingSelection = false;

    private void massSelectorStart(MouseEvent e) {
        if (e.getButton() == MouseButton.PRIMARY && massSelect.isEmpty()) {
            startMassSelectorX = (int) e.getX();
            startMassSelectorY = (int) e.getY();
        } else if (!massSelect.isEmpty() && e.getButton() == MouseButton.PRIMARY) {
            initialMouseX = (int) e.getX();
            initialMouseY = (int) e.getY();
            isDraggingSelection = true;
        }
    }

    private void massSelectorSelect(MouseEvent e) {
        if (massSelect.isEmpty() && e.isShiftDown()) {
            endMassSelectorX = (int) e.getX();
            endMassSelectorY = (int) e.getY();
            redrawCanvas();
            graphics.setStroke(Color.BLUE);
            graphics.setFill(Color.color(1, 1, 0, 0.5));

            int x = Math.min(startMassSelectorX, endMassSelectorX);
            int y = Math.min(startMassSelectorY, endMassSelectorY);
            int width = Math.abs(endMassSelectorX - startMassSelectorX);
            int height = Math.abs(endMassSelectorY - startMassSelectorY);

            graphics.strokeRect(x, y, width, height);
            graphics.fillRect(x, y, width, height);
        } else if (isDraggingSelection) {
            int deltaX = (int) e.getX() - initialMouseX;
            int deltaY = (int) e.getY() - initialMouseY;

            for (Block block : massSelect) {
                block.x += deltaX;
                block.y += deltaY;
            }

            initialMouseX = (int) e.getX();
            initialMouseY = (int) e.getY();
            redrawCanvas();
        }
        drawCoords(e);
    }

    private void massSelectorEnd(MouseEvent e) {
        if (massSelect.isEmpty() && e.isShiftDown()) {
            int x1 = Math.min(startMassSelectorX, endMassSelectorX) - canvasOffsetX;
            int y1 = Math.min(startMassSelectorY, endMassSelectorY) - canvasOffsetY;
            int x2 = Math.max(startMassSelectorX, endMassSelectorX) - canvasOffsetX;
            int y2 = Math.max(startMassSelectorY, endMassSelectorY) - canvasOffsetY;

            ArrayList<Block> temp = new ArrayList<>();
            for (Block block : BlockMemory.getBlocks()) {
                //if (block.x - block.size/2 >= x1 && block.x + block.size/2 <= x1 && block.y - block.size/2 >= y1 && block.y + block.size/2 <= y1 && block.x - block.size/2 <= x2 && block.x + block.size/2 >= x2 && block.y - block.size/2 <= y2 && block.y + block.size/2 >= y2) {
                if (block.x >= x1 && block.y >= y1 && block.x <= x2 && block.y <= y2) {
                    block.moving = true;
                    temp.add(block);
                }
            }
            massSelect = temp;
        } else {
            for (Block block : massSelect) block.moving = false;
            massSelect = new ArrayList<>();
            isDraggingSelection = false;
        }
    }

    private void moveCanvas(MouseEvent e) {
        deltaX = (int) e.getX() - lastX;
        deltaY = (int) e.getY() - lastY;
        canvasOffsetX += deltaX;
        canvasOffsetY += deltaY;
        lastX = (int) e.getX();
        lastY = (int) e.getY();
        redrawCanvas();
        drawCoords(e);
    }

    public void redrawCanvas() {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        drawMovingGrid();
        graphics.setFill(Color.LIME);
        graphics.fillRect(canvasOffsetX-3, canvasOffsetY-3, 6, 6);
        for (Block block : BlockMemory.getBlocks()) {
            if (block != null) {
                if (block.getType() == Block.types.CONNECTION) {
                    ((Connection) block).refresh();
                    block.draw(graphics);
                }
            }
        }
        for (Block block : BlockMemory.getBlocks()) {
            if (block != null) {
                if (block.getType() != Block.types.CONNECTION) block.draw(graphics);
            }
        }
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        graphics.setFill(Color.BLACK);
        graphics.fillRect(32,30, 31 + 12*2,30);
        graphics.setFill(Color.WHITE);
        graphics.setTextAlign(TextAlignment.LEFT);
        graphics.fillText("0, 0", 40, 50);
    }

    private void drawMovingGrid() {
        int gridSpacing = 50;
        graphics.setStroke(Color.LIGHTGRAY);
        graphics.setLineWidth(1);
        int startX = canvasOffsetX % gridSpacing;
        int startY = canvasOffsetY % gridSpacing;
        for (int x = startX; x < getWidth(); x += gridSpacing) {
            graphics.strokeLine(x, 0, x, getHeight());
        }
        for (int y = startY; y < getHeight(); y += gridSpacing) {
            graphics.strokeLine(0, y, getWidth(), y);
        }
    }

    private void deleteBlockOnMouse(Block detectedBlock) {
        BlockMemory.removeBlock(detectedBlock);
        ArrayList<Block> temp = new ArrayList<>();
        temp.add(detectedBlock);
        UndoManager.addAction(temp);
    }

    private Block checkInside(double currentX, double currentY) {
        currentX -= canvasOffsetX;
        currentY -= canvasOffsetY;
        Collections.reverse(BlockMemory.getBlocks());
        for (Block block : BlockMemory.getBlocks()) {
            if (block != null) {
                if (block.getType() != Block.types.CONNECTION && currentX >= block.x - block.size / 2 && currentX <= block.x + block.size / 2 && currentY >= block.y - block.size / 2 && currentY <= block.y + block.size / 2) {
                    Collections.reverse(BlockMemory.getBlocks());
                    return block;
                }
            }
        }
        Collections.reverse(BlockMemory.getBlocks());
        return null;
    }

    public void refreshAllOutputs() {
        for (int i = 0; i < 2; i++) {
            for (Block block : BlockMemory.getBlocks()) {
                if (block != null) {
                    if (block.getType() != Block.types.CONNECTION) {
                        block.calculateOutput();
                    }
                }
            }
        }
    }

    public void setCurrentSelector(Block.types currentSelector) {
        this.currentSelector = currentSelector;
    }

    public void initConnection(Connection connection) {
        Block input = checkInside(connection.x + canvasOffsetX, connection.y + canvasOffsetY);
        if (input == null) return;
        Block out = checkInside(connection.xEnd + canvasOffsetX, connection.yEnd + canvasOffsetY);
        if (out == null) return;
        out.setInput(input);
    }

    public void resetCanvas() {
        UndoManager.addAction(BlockMemory.getBlocksNoConnections());
        BlockMemory.clear();
        canvasOffsetX = 400;
        canvasOffsetY = 400;
        redrawCanvas();
    }

}
