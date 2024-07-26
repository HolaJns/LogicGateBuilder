package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;
import com.logicgatebuilder.engine.Connection;

import java.util.ArrayList;
import java.util.List;

public abstract class BlockMemory {
    private static List<Block> blocks = new ArrayList<>();

    public static void add(Block block) {
        blocks.add(block);
    }

    public static List<Block> getBlocks() {
        return blocks;
    }

    public static ArrayList<Block> getBlocksNoConnections() {
        ArrayList<Block> temp = new ArrayList<>();
        for (Block block : BlockMemory.getBlocks()) {
            if (block.getType() != Block.types.CONNECTION) temp.add(block);
        }
        return temp;
    }

    public static void removeBlock(Block block) {
        blocks.remove(block);
    }

    public static Block locateBlockById(int id, List<Block> list) {
        if (list == null) list = blocks;
        for (Block block : list) {
            if (block != null) {
                if (block.blockId == id) return block;
            }
        }
        return null;
    }

    public static String listBlocks() {
        StringBuilder output = new StringBuilder();
        for (Block block : blocks) {
            if (block != null) {
                if (block.getType() != Block.types.CONNECTION) {
                    output.append(block).append("\n");
                }
            }
        }
        return output.toString();
    }

    public static void setByIndex(Block block, int index) {
        blocks.set(index, block);
    }

    public static void clear() {
        blocks.clear();
    }

    public static void setMemory(List<Block> blocks) {
        BlockMemory.blocks = blocks;
    }
}
