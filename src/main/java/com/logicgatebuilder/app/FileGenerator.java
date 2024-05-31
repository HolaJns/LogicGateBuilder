package com.logicgatebuilder.app;

import javafx.scene.canvas.Canvas;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class FileGenerator {

    private FileWriter file;
    private MainCanvas canvas;

    public FileGenerator(String name, MainCanvas canvas) {
        this.canvas = canvas;
        try {
            this.file = new FileWriter("files/"+name+".lbg");
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

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
}
