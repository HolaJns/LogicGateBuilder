package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileOperator {

    private FileWriter file;

    public FileOperator(String name) {
        try {
            this.file = new FileWriter("files/"+name+".g8");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //storing data

    //translates the current canvas to a g8 file: Each line is a block; Connections are ignored; Format: "id, type, x, y, input1, input2, output"
    public void write() {
        try {
            if(Objects.equals(BlockMemory.listBlocks(), "")) return;
            file.write(BlockMemory.listBlocks());
            file.write("ID:" + Block.getGlobalID());
            file.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //loading files

    //uses an array of values to create a Block using provided values. Format: "id, type, x, y, input1, input2, output"
    private static Block createBlockFromData(String[] splitted) {
        Block temporary =  BlockStaticFactory.create(BlockStaticFactory.translateStringToEnum(splitted[1]),Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]),Integer.parseInt(splitted[0]));
        if(splitted[1].equals("Source")) if(splitted[6].equals("true")) temporary.switchState();
        return temporary;
    }

    //interprets the content of a g8 file using the provided path
    public static List<Block> interpet(String fileName) {
        List<Block> list = new ArrayList<>();
        try {
            if(fileName.charAt(fileName.length()-3) == '.' && fileName.charAt(fileName.length()-2) == 'g' && fileName.charAt(fileName.length()-1) == '8') {
                Scanner scanner = new Scanner(new File(fileName));
                scanner.useDelimiter("\n");
                //iterates through file line by line and creates Blocks from provided data
                while (scanner.hasNext()) {
                    String next = scanner.next();
                    if(!next.contains("ID")) list.add(createBlockFromData(next.split(",")));
                    else Block.setGlobalID(Integer.parseInt(next.split(":")[1]));
                }
                scanner.close();
                //creates connections between Blocks based on provided input1- and input2-ids
                scanner = new Scanner(new File(fileName));
                scanner.useDelimiter("\n");
                int iter = 0;
                while (scanner.hasNext()) {
                    String[] currentIterItem = scanner.next().split(",");
                    if (BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[4]), list) != null) {
                        Connection con = new Connection();
                        con.setStart(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[4]), list));
                        list.get(iter).setInput1(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[4]), list));
                        con.setEnd(list.get(iter));
                        list.add(con);
                    }
                    if (BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list) != null) if (!BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list).getType().equals(Block.types.NOT)) {
                        Connection con = new Connection();
                        con.setStart(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list));
                        list.get(iter).setInput2(BlockMemory.locateBlockById(Integer.parseInt(currentIterItem[5]), list));
                        con.setEnd(list.get(iter));
                        list.add(con);
                    }
                    iter++;
                }
            }
            return list;
        }
        catch (Exception e) {
            //if file signature does not match draw empty canvas
            return list;
        }
    }
}
