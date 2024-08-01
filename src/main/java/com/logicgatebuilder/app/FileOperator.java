package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileOperator {

    private File filesave;

    public FileOperator(File file) {
        this.filesave = file;
    }

    // Storing data

    // Translates the current canvas to a g8 file: Each line is a block; Connections are ignored; Format: "id, type, x, y, input1, input2, output"
    public void write() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filesave))) {
            if (Objects.equals(BlockMemory.listBlocks(), "")) return;
            writer.write(BlockMemory.listBlocks());
            writer.write("ID:" + Block.getGlobalID());
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Loading files

    // Uses an array of values to create a Block using provided values. Format: "id, type, x, y, input1, input2, output"
    private Block createBlockFromData(String[] splitted) {
        if (splitted.length < 7) {
            throw new IllegalArgumentException("Invalid block data: " + String.join(",", splitted));
        }

        Block temporary = BlockFactory.create(
                BlockFactory.translateStringToEnum(splitted[1]),
                Integer.parseInt(splitted[2]),
                Integer.parseInt(splitted[3]),
                Integer.parseInt(splitted[0]),
                Application.canvas
        );

        if (splitted[1].equals("SOURCE") && splitted[6].equals("true")) {
            temporary.switchState();
        }
        return temporary;
    }

    // Interprets the content of a g8 file using the provided path
    private List<Block> interpret() {
        List<Block> list = new ArrayList<>();
        try (Scanner scanner = new Scanner(filesave)) {
            scanner.useDelimiter("\n");
            // Iterates through file line by line and creates Blocks from provided data
            while (scanner.hasNext()) {
                String next = scanner.next().trim();
                if (!next.contains("ID")) {
                    list.add(createBlockFromData(next.split(",")));
                } else {
                    Block.setGlobalID(Integer.parseInt(next.split(":")[1]));
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Second pass to create connections between Blocks
        try (Scanner scanner = new Scanner(filesave)) {
            scanner.useDelimiter("\n");
            int iter = 0;
            while (scanner.hasNext()) {
                String[] currentIterItem = scanner.next().trim().split(",");
                if (currentIterItem.length == 7) {
                    if (BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[4]), list) != null) {
                        Connection con = new Connection(Application.canvas);
                        con.setStart(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[4]), list));
                        list.get(iter).setInput1(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[4]), list));
                        con.setEnd(list.get(iter));
                        list.add(con);
                    }
                    if (BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list) != null &&
                            !BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list).getType().equals(Block.types.NOT)) {
                        Connection con = new Connection(Application.canvas);
                        con.setStart(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list));
                        list.get(iter).setInput2(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list));
                        con.setEnd(list.get(iter));
                        list.add(con);
                    }
                    iter++;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file for connections: " + e.getMessage());
        }

        return list;
    }

    public void load() {
        BlockMemory.setMemory(interpret());
        //Application.canvas.refreshAllOutputs();
        Application.canvas.setCurrentSelector(Block.types.DEFAULT);
        Application.canvas.redrawCanvas();
    }

    public void renameTo(String name) {
        File oldFile = filesave;
        String pathWithoutFileName = filesave.getParent();
        this.filesave = new File(pathWithoutFileName + "/" + name + ".g8");
        oldFile.delete();
    }

    public String getName() {
        return filesave.getName();
    }
}
