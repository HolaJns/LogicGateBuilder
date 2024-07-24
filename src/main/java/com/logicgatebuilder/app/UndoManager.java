package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;
import com.logicgatebuilder.engine.Connection;
import com.sun.tools.javac.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class UndoManager {

    private static Stack<List<Block>> undoStack = new Stack<List<Block>>();

    public static void addAction(ArrayList<Block> action) {
        undoStack.push(action);
    }

    public static void undo() {
        if(undoStack.isEmpty()) return;
        List<Block> block = undoStack.pop();
        for(Block b : block) {
            if(b == null) continue;
            Block selected = BlockMemory.locateBlockById(b.blockId, null);
            if (b.deleted) BlockMemory.removeBlock(selected);
            if (selected != null) {
                selected.setInput1(b.input1);
                selected.setInput2(b.input2);
                selected.x = b.x;
                selected.y = b.y;
            } else BlockMemory.add(b);
        }
        for (Block b : BlockMemory.getBlocksNoConnections()) {
            if(b.input1 != null) {
                Connection connection = new Connection(Application.canvas);
                connection.setStart(b.input1);
                connection.setEnd(b);
                BlockMemory.add(connection);
            }
            if(b.input2 != null) {
                Connection connection = new Connection(Application.canvas);
                connection.setStart(b.input2);
                connection.setEnd(b);
                BlockMemory.add(connection);
            }
        }
    }
}
