package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.Objects;


public class Buttons extends Button {
    private String type;

    public Buttons(String type) {
        super(type);
        this.type = type;
        addEventHandler(ActionEvent.ACTION, this::Click);
        setMaxHeight(40);
        setMaxWidth(100);
        setStyle("-fx-border-color: #000000; -fx-border-width: 1px; -fx-border-radius: 5px;");
        setHover(true);
        final String IDLE_BUTTON_STYLE = "-fx-background-color: #dddddd;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
        final String IDLE_BUTTON_STYLE_DARK = "-fx-background-color: #232323;";
        final String HOVERED_BUTTON_STYLE_DARK = "-fx-background-color: #555555;";
        if(Application.darkMode) {
            setStyle(IDLE_BUTTON_STYLE_DARK);
            setOnMouseEntered(e -> setStyle(HOVERED_BUTTON_STYLE_DARK));
            setOnMouseExited(e -> setStyle(IDLE_BUTTON_STYLE_DARK));
            setTextFill(Color.WHITE);
        }
        else {
            setStyle(IDLE_BUTTON_STYLE);
            setOnMouseEntered(e -> setStyle(HOVERED_BUTTON_STYLE));
            setOnMouseExited(e -> setStyle(IDLE_BUTTON_STYLE));
            setTextFill(Color.BLACK);
        }
    }

    public void Click(ActionEvent event) {
        if(this.type.equals("Save")) {
            save();
        }
        else if(this.type.equals("Load")) {
            if (Objects.equals(Application.tf1.getText(), "")) return;
            List<Block> list = FileOperator.interpet(Application.tf1.getText());
            BlockMemory.setMemory(list);
            Application.canvas.refreshAllOutputs();
            Application.canvas.setCurrentSelector(Block.types.DEFAULT);
            Application.canvas.redrawCanvas();
            Application.tf.setText(Application.tf1.getText().split("/")[Application.tf1.getText().split("/").length - 1].replaceFirst(".g8",""));
            Application.tf1.setText("");
        }
        else if(this.type.equals("Reset")) Application.canvas.resetCanvas();
        else Application.canvas.setCurrentSelector(BlockFactory.translateStringToEnum(type));
    }

    public void save() {
        if (Objects.equals(Application.tf.getText(), "")) return;
        FileOperator fileOperator = new FileOperator(Application.tf.getText());
        Application.setFile(fileOperator);
        Application.file.write();
    }
}
