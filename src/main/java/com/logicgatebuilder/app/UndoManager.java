package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;

import java.util.Stack;

public abstract class UndoManager {

    private static Stack<Block> undoStack = new Stack<Block>();

    public static void addAction(Block action) {
        undoStack.push(action);
    }

    public static void undo() {
        if(undoStack.isEmpty()) return;
        Block block = undoStack.pop();
        Block selected = BlockMemory.locateBlockById(block.blockId, null);
        if(block.x == -1000) BlockMemory.removeBlock(selected);
        if(selected != null) {
            selected.setInput1(block.input1);
            selected.setInput2(block.input2);
            selected.x = block.x;
            selected.y = block.y;
        }
        else BlockMemory.add(block);
    }
}
