package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.Block;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class Buttons extends Button {
    private String type;

    public Buttons(String type) {
        super(type);
        this.type = type;
        final String IDLE_BUTTON_STYLE = "-fx-background-color: #dddddd;";
        final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
        final String IDLE_BUTTON_STYLE_DARK = "-fx-background-color: #454545;";
        final String HOVERED_BUTTON_STYLE_DARK = "-fx-background-color: #555555;";
        final String IDLE_BUTTON_STYLE_AND = "-fx-background-color: #ff0000;";
        final String IDLE_BUTTON_STYLE_NAND = "-fx-background-color: #8B0000;";
        final String IDLE_BUTTON_STYLE_OR = "-fx-background-color: #0000ff;";
        final String IDLE_BUTTON_STYLE_NOR = "-fx-background-color: #00008B;";
        final String IDLE_BUTTON_STYLE_XOR = "-fx-background-color: #FFA500;";
        final String IDLE_BUTTON_STYLE_SOURCE = "-fx-background-color: #FFFF00;";
        final String IDLE_BUTTON_STYLE_ACTIVATOR = "-fx-background-color: #FF69B4;";
        final String IDLE_BUTTON_STYLE_NOT = "-fx-background-color: #008000;";
        final String IDLE_BUTTON_STYLE_OUTPUT = "-fx-background-color: #00FF00;";
        final String IDLE_BUTTON_STYLE_CLOCK = "-fx-background-color: #FFFFFF;";
        addEventHandler(ActionEvent.ACTION, this::Click);
        setHover(true);
        setTextAlignment(TextAlignment.CENTER);
        setTextFill(Color.WHITE);
        setFont(Font.font("Arial", 11));
        if(type == "Reset") {
            setMaxHeight(40);
            setMaxWidth(100);
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
        } else {
            String style = "";
            switch(type) {
                case "AND": style = IDLE_BUTTON_STYLE_AND; break;
                case "NAND": style = (IDLE_BUTTON_STYLE_NAND ); break;
                case "OR": style = (IDLE_BUTTON_STYLE_OR); break;
                case "NOR": style = (IDLE_BUTTON_STYLE_NOR); break;
                case "XOR": style = (IDLE_BUTTON_STYLE_XOR); break;
                case "SRC": style = (IDLE_BUTTON_STYLE_SOURCE); setTextFill(Color.BLACK); break;
                case "1": style = (IDLE_BUTTON_STYLE_ACTIVATOR); break;
                case "NOT": style = (IDLE_BUTTON_STYLE_NOT); break;
                case "OUT": style = (IDLE_BUTTON_STYLE_OUTPUT); setTextFill(Color.BLACK); break;
                case "CON": setTextFill(Color.BLACK); if(Application.darkMode) { style = IDLE_BUTTON_STYLE_DARK; } else style = IDLE_BUTTON_STYLE; break;
                case "CLK": setTextFill(Color.BLACK); style = (IDLE_BUTTON_STYLE_CLOCK); break;
            }
            setStyle(" -fx-background-radius: 0; -fx-border-color: #000000; -fx-border-width: 0px; -fx-border-radius: 0px;" + style);
            setMinSize(50,50);
            setMaxSize(50,50);
        }
    }

    public void Click(ActionEvent event) {
        if(this.type.equals("Reset")) Application.canvas.resetCanvas();
        else Application.canvas.setCurrentSelector(BlockFactory.translateStringToEnum(type));
    }
}
