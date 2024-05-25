package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainCanvas extends Canvas {
    private GraphicsContext graphics;
    private String currentSelector = "";
    public List blockMemory = new ArrayList();
    private Block movementPointer = null;
    private boolean startSelected = false;

    public MainCanvas() {
        setWidth(600);
        setHeight(600);
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
                if(startSelected) {
                    redrawCanvas();
                    ((Connection) movementPointer).setEnd((int) e.getX(), (int) e.getY());
                    movementPointer.draw(graphics);
                    startSelected = false;
                    blockMemory.add(movementPointer);
                    initConnection((Connection) movementPointer);
                    movementPointer = null;
                    currentSelector = "";
                } else {
                    movementPointer = new Connection();
                    if(checkInside((int)e.getX(),(int)e.getY()) != null) {
                        ((Connection) movementPointer).setStart((int) e.getX(), (int) e.getY());
                        startSelected = true;
                    }
                }
                break;

            }
            default: {
                initializeMovementPointer(e);
                if(movementPointer != null && e.isControlDown()) deleteBlockOnMouse(movementPointer);
                else if(movementPointer != null) if(Objects.equals(movementPointer.getType(), "Source") && !e.isShiftDown() && !e.isControlDown()) {
                    movementPointer.switchState();
                    movementPointer = null;
                }
                break;
            }
        }
        redrawCanvas();
    }

    //verify current mouse coordinates on click and sets movementPointer to selected Block, if coordinates match with a Block in blockMemory. Else: Set movementPointer to null
    private void initializeMovementPointer(MouseEvent e) {
        Block block = checkInside((int) e.getX(), (int) e.getY());
        if(movementPointer == null) {
            movementPointer = (Block) block;
        } else movementPointer = null;
    }

    //OnMouseMove change coordinates of selected Block in blockMemory to current X and Y. Move Animation until Block is replaced with MouseClick
    private void moveBlock(MouseEvent e) {
        if (movementPointer != null && !currentSelector.equals("Connection") && e.isShiftDown()) {
            movementPointer.x = (int) e.getX();
            movementPointer.y = (int) e.getY();
            movementPointer.draw(graphics);
            redrawCanvas();
        }
        else if(currentSelector.equals("Connection") && startSelected) {
            redrawCanvas();
            if(checkInside((int)e.getX(),(int)e.getY()) != null) {
                ((Connection) movementPointer).setEnd((int) e.getX(), (int) e.getY());
                movementPointer.draw(graphics);
            }
        }
    }

    //redraws canvas based on Blocks in blockMemory
    public void redrawCanvas() {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        for (Object block : blockMemory) {
            if(block instanceof Block) {
                ((Block) block).draw(graphics);
            }
        }
    }

    //deletion function. Removes the selected block from blockMemory and redraws
    public void deleteBlockOnMouse(Block detectedBlock) {
        blockMemory.remove(detectedBlock);
    }

    //collision detection. Check if currentCords are within one Block in blockMemory
    private Block checkInside(int currentX, int currentY) {
        for (Object block : blockMemory) {
            if (block instanceof Block) {
                if(Objects.equals(((Block) block).getType(), "Connection")) continue;
                /*
                    int slope = (((Connection)block).xEnd - ((Connection)block).x)/(((Connection)block).yEnd - ((Connection)block).y);
                    int stepX = (((Connection) block).xEnd - ((Connection)block).x)/30;
                    int stepY = (((Connection) block).yEnd - ((Connection)block).y)/30;
                    for(int x = ((Connection)block).x; x < ((Connection)block).xEnd; x += stepX) {
                        if(currentX >= x-stepX/2 && currentX <= x+stepX/2) {
                            for(int y = ((Connection)block).y; y < ((Connection)block).yEnd; y += stepY) {
                                if(currentY >= y-stepY/2 && currentY <= y+stepY) {
                                    System.out.printf("Con");
                                    return (Block) block;
                                }
                            }
                        }
                    }
                }
                else */if (currentX >= ((Block) block).x - ((Block) block).size / 2 && currentX <= ((Block) block).x + ((Block) block).size / 2 && currentY >= ((Block) block).y - ((Block) block).size / 2 && currentY <= ((Block) block).y + ((Block) block).size / 2) {
                    return (Block) block;
                }
            }
        }
        return null;
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
    public void listBlocks() {
        for (Object block : blockMemory) {
            if(block instanceof Block) {
                if(!Objects.equals(((Block) block).getType(), "Connection")) {
                    System.out.printf(block.toString());
                }
            }
        }
    }

    public void resetCanvas() {
        blockMemory.clear();
        redrawCanvas();
    }
}