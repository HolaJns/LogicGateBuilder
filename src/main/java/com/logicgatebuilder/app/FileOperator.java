package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class FileOperator {

    private FileWriter file;
    private MainCanvas canvas;

    public FileOperator(String name, MainCanvas canvas) {
        this.canvas = canvas;
        try {
            this.file = new FileWriter("files/"+name+".lbg");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //storing data

    //translates the current canvas to a lgb file: Each line is a block; Connections are ignored; Format: "id, type, x, y, input1, input2, output"
    public void write() {
        try {
            if(Objects.equals(canvas.listBlocks(), "")) return;
            file.write(canvas.listBlocks());
            file.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //located an ID as String N in a provided list blocks
    private static Block locateN(String N, List blocks) {
        for(Object block : blocks) {
            if(block instanceof Block) {
                if(((Block) block).blockId == Integer.parseInt(N)) {
                    return (Block) block;
                }
            }
        }
        return null;
    }

    //loading files

    //uses an array of values to create a Block using provided values. Format: "id, type, x, y, input1, input2, output"
    private static Block createBlockFromData(String[] splitted) {
        switch(splitted[1]) {
            case "Source": {
                Block temp = new Source(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                if(Boolean.parseBoolean(splitted[6])) temp.switchState();
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "And": {
                Block temp = new And(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "Or": {
                Block temp = new Or(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "Not": {
                Block temp = new Not(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "Xor": {
                Block temp = new Xor(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "Nand": {
                Block temp = new Nand(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "Nor": {
                Block temp = new Nor(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            case "Output": {
                Block temp = new Output(Integer.valueOf(splitted[2]),Integer.valueOf(splitted[3]));
                temp.forceID(Integer.parseInt(splitted[0]));
                return temp;
            }
            default:
                return null;
        }
    }

    //interprets the content of a lgb file using the provided path
    public static List interpet(String fileName) {
        List list = new ArrayList();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            scanner.useDelimiter("\n");
            while (scanner.hasNext()) {
                list.add(createBlockFromData(scanner.next().split(",")));
            }
            scanner.close();
            scanner = new Scanner(new File(fileName));
            scanner.useDelimiter("\n");
            int iter = 0;
            while (scanner.hasNext()) {
                String[] currentIterItem = scanner.next().split(",");
                if(locateN(currentIterItem[4], list) != null) {
                    Block con = new Connection();
                    ((Connection)con).setStart(locateN(currentIterItem[4], list));
                    ((Block)list.get(iter)).input1 =  locateN(currentIterItem[4], list);
                    ((Connection)con).setEnd(((Block)list.get(iter)));
                    list.add(con);
                }
                if(locateN(currentIterItem[5], list) != null) if(!locateN(currentIterItem[5], list).getType().equals("Not")) {
                    Block con = new Connection();
                    ((Connection)con).setStart(locateN(currentIterItem[5], list));
                    ((Block)list.get(iter)).input2 =  locateN(currentIterItem[5], list);
                    ((Connection)con).setEnd(((Block)list.get(iter)));
                    list.add(con);
                }
                iter++;
            }
            return list;
        }
        catch (Exception e) {
            return null;
        }
    }
}
