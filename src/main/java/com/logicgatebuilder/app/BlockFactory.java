package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

public abstract class BlockFactory {

    public static Block create(Block.types type, int x, int y, int id, ApplicationCanvas canvas) {
        //x = ApplicationCanvas.canvasOffsetX;
        //y = ApplicationCanvas.canvasOffsetY;
        Block temp = null;
        switch (type) {
            case SOURCE: {
                temp = new Source(x,y,canvas);
                break;
            }
            case AND: {
                temp = new And(x,y,canvas);
                break;
            }
            case NAND: {
                temp = new Nand(x,y,canvas);
                break;
            }
            case OR: {
                temp = new Or(x,y,canvas);
                break;
            }
            case NOR: {
                temp = new Nor(x,y,canvas);
                break;
            }
            case NOT: {
                temp = new Not(x,y,canvas);
                break;
            }
            case XOR: {
                temp = new Xor(x,y,canvas);
                break;
            }
            case OUTPUT: {
                temp = new Output(x,y,canvas);
                break;
            }
            case ACTIVATOR: {
                temp = new Activator(x,y,canvas);
                break;
            }
            case CONNECTION: {
                temp = new Connection(canvas);
            }
            default: break;
        }
        if(temp != null) temp.forceID(id);
        return temp;
    }

    public static Block create(Block.types type, int x, int y, int id, boolean removeFlag,ApplicationCanvas canvas) {
        Block temp = BlockFactory.create(type, x, y, id,canvas);
        temp.deleted = removeFlag;
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
