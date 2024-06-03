package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainCanvas extends Canvas {
    private final GraphicsContext graphics;
    private String currentSelector = "";
    private List<Block> blockMemory = new ArrayList<>();
    private Block movementPointer = null;
    private boolean startSelected = false;

    public MainCanvas() {
        setWidth(800);
        setHeight(800);
        graphics = getGraphicsContext2D();
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        addEventHandler(MouseEvent.MOUSE_CLICKED, this::placeBlock);
        addEventHandler(MouseEvent.MOUSE_MOVED, this::moveBlock);
    }

    //Place Block on mouse click based on currentSelector String. Can be values: "Source", "Output", "And", "Nand", "Or", "Nor", "Not" or "Xor"
    private void placeBlock(MouseEvent e) {
        switch (currentSelector) {
            case "Source": {
                Block temp = new Source((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "And": {
                Block temp = new And((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Nand": {
                Block temp = new Nand((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Or": {
                Block temp = new Or((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Nor": {
                Block temp = new Nor((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Not": {
                Block temp = new Not((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Xor": {
                Block temp = new Xor((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Output": {
                Block temp = new Output((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Connection": {
                if(startSelected && e.getButton() == MouseButton.SECONDARY) {
                    currentSelector = "";
                    movementPointer = null;
                    startSelected = false;
                }
                else if(startSelected) {
                    Block block = checkInside((int)e.getX(),(int)e.getY());
                    if(block != null) {
                            ((Connection) movementPointer).setEnd(block);
                            movementPointer.draw(graphics);
                            startSelected = false;
                            blockMemory.add(movementPointer);
                            initConnection((Connection) movementPointer);
                            movementPointer = null;
                            currentSelector = "";
                        }
                } else {
                    movementPointer = new Connection();
                    Block block = checkInside((int)e.getX(),(int)e.getY());
                    if(block != null && !block.getType().equals("Output")) {
                        ((Connection) movementPointer).setStart(block);
                        startSelected = true;
                    }
                }
                break;

            }
            default: {
                initializeMovementPointer(e);
                if(movementPointer != null) {
                    if(e.isControlDown()) {
                        deleteBlockOnMouse(movementPointer);
                        for(Block block: blockMemory) {
                            if(block != null) {
                                if((block).getType().equals("Connection")) {
                                    if(((Connection) block).startBlock == movementPointer ||((Connection) block).endBlock == movementPointer) {
                                        blockMemory.set(blockMemory.indexOf(block), null);
                                    }
                                }
                                else {
                                    if(block.input1 == movementPointer) block.input1 = null;
                                    if(block.input2 == movementPointer) block.input2 = null;
                                }
                            }
                        }
                        blockMemory.remove(null);
                        movementPointer = null;
                        refreshAllOutputs();
                    }
                    else if(movementPointer.getType().equals("Source") && !e.isShiftDown() && !e.isControlDown()) {
                        movementPointer.switchState();
                        movementPointer = null;
                    }
                    else if (movementPointer != null && e.isShiftDown()) {
                        blockMemory.remove(movementPointer);
                        blockMemory.add(movementPointer);
                    }
                    else movementPointer = null;
                }
                break;
            }
        }
        refreshAllOutputs();
        redrawCanvas();
    }

    //verify current mouse coordinates on click and sets movementPointer to selected Block, if coordinates match with a Block in blockMemory. Else: Set movementPointer to null
    private void initializeMovementPointer(MouseEvent e) {
        Block block = checkInside((int) e.getX(), (int) e.getY());
        if(movementPointer == null) {
            movementPointer = block;
        } else movementPointer = null;
    }

    //OnMouseMove change coordinates of selected Block in blockMemory to current X and Y. Move Animation until Block is replaced with MouseClick
    private void moveBlock(MouseEvent e) {
        if (movementPointer != null && !currentSelector.equals("Connection")) {
            movementPointer.x = (int) e.getX();
            movementPointer.y = (int) e.getY();
            movementPointer.draw(graphics);
            redrawCanvas();
        }
        else if(currentSelector.equals("Connection") && startSelected ) {
            redrawCanvas();
            ((Connection)movementPointer).xEnd = (int) e.getX();
            ((Connection)movementPointer).yEnd = (int) e.getY();
            movementPointer.draw(graphics);
        }
    }

    //redraws canvas based on Blocks in blockMemory
    public void redrawCanvas() {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        for (Block block : blockMemory) {
            if(block != null) {
                if(block.getType().equals("Connection")) {
                    ((Connection) block).refresh();
                    block.draw(graphics);
                }
            }
        }
        for (Block block : blockMemory) {
            if(block != null) {
                if(!block.getType().equals("Connection")) block.draw(graphics);
            }
        }
    }

    //deletion function. Removes the selected block from blockMemory and redraws
    private void deleteBlockOnMouse(Block detectedBlock) {
        blockMemory.remove(detectedBlock);
    }

    //collision detection. Check if currentCords are within one Block in blockMemory
    private Block checkInside(double currentX, double currentY) {
        for (Block block : blockMemory) {
            if (block != null) {
                if (!Objects.equals(block.getType(), "Connection") && currentX >= block.x - block.size / 2 && currentX <= block.x + block.size / 2 && currentY >= block.y - block.size / 2 && currentY <= block.y + block.size / 2) {
                    return block;
                }
            }
        }
        return null;
    }

    //refreshes the outputs of all placed blocks
    public void refreshAllOutputs() {
        for(int i = 0; i < 2; i++) {
            for (Block block : blockMemory) {
                if (block != null) {
                    if (!block.getType().equals("Connection")) {
                        block.calculateOutput();
                    }
                }
            }
        }
    }

    //change Current Selector. Used in later processes for Button interaction
    public void setCurrentSelector(String currentSelector) {
        this.currentSelector = currentSelector;
    }

    //initializes connection. Startingpoint is input, Endingpoint is output
    public void initConnection(Connection connection) {
        Block input = checkInside(connection.x, connection.y);
        if(input == null) return;
        Block out = checkInside(connection.xEnd, connection.yEnd);
        if(out == null) return;
        out.setInput(input);
    }

    //returns a list of all Blocks in blockMemory using Block.toString()
    public String listBlocks() {
        StringBuilder oupt = new StringBuilder();
        for (Block block : blockMemory) {
            if(block != null) {
                if(!Objects.equals(block.getType(), "Connection")) {
                    oupt.append(block).append("\n");
                }
            }
        }
        return oupt.toString();
    }

    //resets the canvas by deleting blockMemory and redrawing the canvas
    public void resetCanvas() {
        blockMemory.clear();
        redrawCanvas();
    }

    public void setBlockMemory(List<Block> list) {
        this.blockMemory = list;
    }
}