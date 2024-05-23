package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MainCanvas extends Canvas {
    private GraphicsContext graphics;
    private String currentSelector = "Src";
    public List blockMemory = new ArrayList();
    private Block movementPointer = null;

    public MainCanvas() {
        setWidth(600);
        setHeight(600);
        graphics = getGraphicsContext2D();
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        addEventHandler(MouseEvent.MOUSE_CLICKED, this::placeBlock);
        addEventHandler(MouseEvent.MOUSE_MOVED, this::moveBlock);
    }


    //Place Block on mouse click based on currentSelector String. Can be values: "Src", "And", "Nand", "Or", "Nor", "Not" or "Xor"
    private void placeBlock(MouseEvent e) {
        switch (currentSelector) {
            case "Src": {
                Block temp = new Source((int) e.getX(), (int) e.getY());
                graphics.setFill(Color.BLACK);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(new Source((int) e.getX(), (int) e.getY()));
                currentSelector = "";
                break;
            }
            case "And": {
                Block temp = new And(null,null,(int) e.getX(), (int) e.getY());
                graphics.setFill(temp.color);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Nand": {
                Block temp = new Nand(null,null,(int) e.getX(), (int) e.getY());
                graphics.setFill(temp.color);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Or": {
                Block temp = new Or(null,null,(int) e.getX(), (int) e.getY());
                graphics.setFill(temp.color);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Nor": {
                Block temp = new Nor(null,null,(int) e.getX(), (int) e.getY());
                graphics.setFill(temp.color);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "not": {
                Block temp = new Not(null,(int) e.getX(), (int) e.getY());
                graphics.setFill(temp.color);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Xor": {
                Block temp = new Xor(null,null,(int) e.getX(), (int) e.getY());
                graphics.setFill(temp.color);
                graphics.fillRect(e.getX()-temp.size/2, e.getY()-temp.size/2, temp.size, temp.size);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            default: {
                initializeMovementPointer(e);
                break;
            }
        }
    }

    //verify current mouse coordinates on click and sets movementPointer to selected Block, if coordinates match with a Block in blockMemory. Else: Set movementPointer to null
    private void initializeMovementPointer(MouseEvent e) {
        if(movementPointer == null) {
            int currentX = (int) e.getX();
            int currentY = (int) e.getY();
            for (Object block : blockMemory) {
                if (block instanceof Block) {
                    if (currentX >= ((Block) block).x - ((Block) block).size / 2 && currentX <= ((Block) block).x + ((Block) block).size / 2 && currentY >= ((Block) block).y - ((Block) block).size / 2 && currentY <= ((Block) block).y + ((Block) block).size / 2) {
                        movementPointer = (Block) block;
                    }
                }
            }
        } else movementPointer = null;
    }

    //OnMouseMove change coordinates of selected Block in blockMemory to current X and Y. Move Animation until Block is replaced with MouseClick
    private void moveBlock(MouseEvent e) {
        if (movementPointer != null) {
            graphics.setFill(movementPointer.color);
            movementPointer.x = (int) e.getX();
            movementPointer.y = (int) e.getY();
            graphics.fillRect(movementPointer.x, movementPointer.y, movementPointer.size, movementPointer.size);
            redrawCanvas();
        }
    }

    //redraws canvas based on Blocks in blockMemory
    public void redrawCanvas() {
        graphics.setFill(Color.WHITE);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        for (Object block : blockMemory) {
            if(block instanceof Block) {
                graphics.setFill(((Block) block).color);
                graphics.fillRect(((Block) block).x- ((Block) block).size/2, ((Block) block).y-((Block) block).size/2, ((Block) block).size, ((Block) block).size);
            }
        }
    }

    //change Current Selector. Used in later processes for Button interaction
    public void setCurrentSelector(String currentSelector) {
        this.currentSelector = currentSelector;
    }
}