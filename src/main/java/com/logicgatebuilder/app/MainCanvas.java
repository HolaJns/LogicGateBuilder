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
    private String currentSelector = "";
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
            case "Source": {
                Block temp = new Source((int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "And": {
                Block temp = new And(null,null,(int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Nand": {
                Block temp = new Nand(null,null,(int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Or": {
                Block temp = new Or(null,null,(int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Nor": {
                Block temp = new Nor(null,null,(int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Not": {
                Block temp = new Not(null,(int) e.getX(), (int) e.getY());
                temp.draw(graphics);
                blockMemory.add(temp);
                currentSelector = "";
                break;
            }
            case "Xor": {
                Block temp = new Xor(null,null,(int) e.getX(), (int) e.getY());
                temp.draw(graphics);
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
            movementPointer.x = (int) e.getX();
            movementPointer.y = (int) e.getY();
            movementPointer.draw(graphics);
            redrawCanvas();
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

    //change Current Selector. Used in later processes for Button interaction
    public void setCurrentSelector(String currentSelector) {
        this.currentSelector = currentSelector;
    }
}