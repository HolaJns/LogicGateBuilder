package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class BlockMemory {
    private static List<Block> blocks = new ArrayList<>();

    public static void add(Block block) {
        blocks.add(block);
    }

    public static List<Block> getBlocks() {
        return blocks;
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
                if (!Objects.equals(block.getType(), "Connection")) {
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

    public static void filterFillerBlocks() {
        for(Block b : blocks) {
            if(b.x == -1000 || b.y == -1000) {
                removeBlock(b);
            }
        }
    }
}
