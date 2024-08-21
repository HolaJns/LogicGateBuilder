package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

public abstract class BlockFactory {

    public static Block create(Block.types type, int x, int y, int id, ApplicationCanvas canvas) {
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
            case CLOCK: {
                temp = new Clock(x, y, 1000, canvas);
            }
            default: break;
        }
        if(temp != null) temp.forceID(id);
        return temp;
    }

    public static String translateEnumToString(Block.types block) {
        switch (block) {
            case SOURCE: return "SRC";
            case AND: return "AND";
            case NAND: return "NAND";
            case OR: return "OR";
            case NOR: return "NOR";
            case NOT: return "NOT";
            case XOR: return "XOR";
            case OUTPUT: return "OUT";
            case CONNECTION: return "CON";
            case ACTIVATOR: return "1";
            case CLOCK: return "CLK";
            default: return "";
        }
    }

    public static Block.types translateStringToEnum(String type) {
        switch (type) {
            case "SRC": return Block.types.SOURCE;
            case "AND": return Block.types.AND;
            case "NAND": return Block.types.NAND;
            case "OR": return Block.types.OR;
            case "NOR": return Block.types.NOR;
            case "NOT": return Block.types.NOT;
            case "XOR": return Block.types.XOR;
            case "OUT": return Block.types.OUTPUT;
            case "CON": return Block.types.CONNECTION;
            case "1": return Block.types.ACTIVATOR;
            case "CLK": return Block.types.CLOCK;
            default: return Block.types.DEFAULT;
        }
    }
}
