package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

public abstract class BlockStaticFactory {

    public static Block create(Block.types type, int x, int y, int id) {
        //x = MainCanvas.canvasOffsetX;
        //y = MainCanvas.canvasOffsetY;
        Block temp = null;
        switch (type) {
            case SOURCE: {
                temp = new Source(x,y);
                break;
            }
            case AND: {
                temp = new And(x,y);
                break;
            }
            case NAND: {
                temp = new Nand(x,y);
                break;
            }
            case OR: {
                temp = new Or(x,y);
                break;
            }
            case NOR: {
                temp = new Nor(x,y);
                break;
            }
            case NOT: {
                temp = new Not(x,y);
                break;
            }
            case XOR: {
                temp = new Xor(x,y);
                break;
            }
            case OUTPUT: {
                temp = new Output(x,y);
                break;
            }
            case ACTIVATOR: {
                temp = new Activator(x,y);
                break;
            }
            case CONNECTION: {
                temp = new Connection();
            }
            default: break;
        }
        if(temp != null) temp.forceID(id);
        return temp;
    }

    public static String translateEnumToString(Block.types block) {
        switch (block) {
            case SOURCE: return "Source";
            case AND: return "And";
            case NAND: return "Nand";
            case OR: return "Or";
            case NOR: return "Nor";
            case NOT: return "Not";
            case XOR: return "Xor";
            case OUTPUT: return "Output";
            case CONNECTION: return "Connection";
            case ACTIVATOR: return "Activator";
            default: return "";
        }
    }

    public static Block.types translateStringToEnum(String type) {
        switch (type) {
            case "Source": return Block.types.SOURCE;
            case "And": return Block.types.AND;
            case "Nand": return Block.types.NAND;
            case "Or": return Block.types.OR;
            case "Nor": return Block.types.NOR;
            case "Not": return Block.types.NOT;
            case "Xor": return Block.types.XOR;
            case "Output": return Block.types.OUTPUT;
            case "Connection": return Block.types.CONNECTION;
            case "Activator": return Block.types.ACTIVATOR;
            default: return Block.types.DEFAULT;
        }
    }
}
