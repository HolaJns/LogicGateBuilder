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

public class ApplicationCanvas extends Canvas {
    private final GraphicsContext graphics;
    private Block.types currentSelector = Block.types.DEFAULT;
    private Block blockPointer = null;
    private boolean startSelected = false;
    public int canvasSizeX = 800, canvasSizeY = 800;
    public int canvasOffsetX = canvasSizeX/2, canvasOffsetY = canvasSizeY/2, deltaX = 0, deltaY = 0;
    private int lastX, lastY;
    public boolean cordsOff = false;
    public FileOperator save;


    public ApplicationCanvas() {
        setWidth(canvasSizeX);
        setHeight(canvasSizeY);
        graphics = getGraphicsContext2D();
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        MassSelector.initCanvas(this, graphics);
        addEventHandler(MouseEvent.MOUSE_CLICKED, this::clicked);
        addEventHandler(MouseEvent.MOUSE_MOVED, this::move);
        addEventHandler(MouseEvent.MOUSE_DRAGGED, this::dragging);
        addEventHandler(MouseEvent.MOUSE_PRESSED, this::initiateCanvasMove);
        addEventHandler(MouseEvent.MOUSE_PRESSED, this::pressed);
        addEventHandler(MouseEvent.MOUSE_RELEASED, this::released);
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

    private void move(MouseEvent e) {
        redrawCanvas();
        connectionMove(e);
        drawCoords(e);
    }

    private void pressed(MouseEvent e) {
        MassSelector.massSelectorStart(e);
    }

    private void released(MouseEvent e) {
        MassSelector.massSelectorEnd(e);
    }

    private void dragging(MouseEvent e) {
        if (e.getButton() == MouseButton.SECONDARY) moveCanvas(e);
        if (e.getButton() == MouseButton.PRIMARY) MassSelector.massSelectorSelect(e);
    }

    private void placeBlock(MouseEvent e) {
        // check secondary key usage
        if (e.getButton() == MouseButton.SECONDARY) {
            // stop connection logic
            if (currentSelector.equals(Block.types.CONNECTION) && e.isControlDown()) {
                currentSelector = Block.types.DEFAULT;
                blockPointer = null;
                startSelected = false;
            }
        }
        // check primary key usage
        // place block of type currentSelector if not DEFAULT or CONNECTION
        else if (currentSelector != Block.types.DEFAULT && currentSelector != Block.types.CONNECTION) {
            Block newBlock = BlockFactory.create(currentSelector, (int) e.getX() - canvasOffsetX, (int) e.getY() - canvasOffsetY, Block.getGlobalID(),this);
            BlockMemory.add(newBlock);
            ArrayList<Block> removeHelper = new ArrayList<>();
            removeHelper.add(BlockFactory.create(newBlock.getType(), newBlock.x, newBlock.y, newBlock.blockId, this));
            UndoManager.addAction(removeHelper);
            currentSelector = Block.types.DEFAULT;
        }
        // place and concat connection
        else if (currentSelector.equals(Block.types.CONNECTION)) {
            // if start is already selected, find end Block
            if (startSelected) {
                Block block = checkInside((int) e.getX(), (int) e.getY());
                if (block != null) {
                    ((Connection) blockPointer).setEnd(block);
                    blockPointer.draw(graphics);
                    startSelected = false;
                    BlockMemory.add(blockPointer);
                    initConnection((Connection) blockPointer);
                    blockPointer = null;
                    currentSelector = Block.types.DEFAULT;
                }
            }
            // if no start is selected, find one
            else {
                blockPointer = new Connection(this);
                Block block = checkInside((int) e.getX(), (int) e.getY());
                if (block != null && !block.getType().equals(Block.types.OUTPUT)) {
                    ((Connection) blockPointer).setStart(block);
                    startSelected = true;
                }
            }
        }
        // delete, swap and move logic
        else {
            initBlockPointer(e);
            if (blockPointer != null) {
                // delete block if control is down. Removes references and connections as well
                if (e.isControlDown()) {
                    deleteBlockOnMouse(blockPointer);
                    for (Block block : BlockMemory.getBlocks()) {
                        if (block != null) {
                            if ((block).getType().equals(Block.types.CONNECTION)) {
                                if (((Connection) block).startBlock == blockPointer || ((Connection) block).endBlock == blockPointer) {
                                    BlockMemory.setByIndex(null, BlockMemory.getBlocks().indexOf(block));
                                }
                            } else {
                                if (block.input1 == blockPointer) block.input1 = null;
                                if (block.input2 == blockPointer) block.input2 = null;
                            }
                        }
                    }
                    BlockMemory.removeBlock(null);
                    blockPointer = null;
                    refreshAllOutputs();
                }
                // swap state of source on mouse click
                else if (blockPointer.getType().equals(Block.types.SOURCE) && !e.isShiftDown() && !e.isControlDown()) {
                    blockPointer.switchState();
                    blockPointer = null;
                }
                else blockPointer = null;
            }
        }
        refreshAllOutputs();
        redrawCanvas();
        drawCoords(e);
    }

    private void initBlockPointer(MouseEvent e) {
        Block block = checkInside((int) e.getX(), (int) e.getY());
        if (blockPointer == null) {
            blockPointer = block;
        } else blockPointer = null;
    }

    private void connectionMove(MouseEvent e) {
        if (currentSelector == Block.types.CONNECTION && startSelected) {
            ((Connection) blockPointer).xEnd = (int) e.getX() - canvasOffsetX;
            ((Connection) blockPointer).yEnd = (int) e.getY() - canvasOffsetY;
            blockPointer.draw(graphics);
        }
    }

    int prevX = 0, prevY = 0;
    public void drawCoords(MouseEvent e) {
        if(cordsOff) return;
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
        if(Application.darkMode) graphics.setFill(Color.web("#2c2f33"));
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
        if(cordsOff) return;
        graphics.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        graphics.setFill(Color.BLACK);
        graphics.fillRect(32,30, 31 + 12*2,30);
    }

    private void drawMovingGrid() {
        int gridSpacing = 50;
        graphics.setStroke(Color.LIGHTGRAY);
        if(Application.darkMode) graphics.setStroke(Color.BLACK);
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
        canvasOffsetX = canvasSizeX/2;
        canvasOffsetY = canvasSizeY/2;
        redrawCanvas();
    }
}
