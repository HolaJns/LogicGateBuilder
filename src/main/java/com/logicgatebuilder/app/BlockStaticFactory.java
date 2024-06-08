package com.logicgatebuilder.app;

import com.logicgatebuilder.engine.*;

public class BlockStaticFactory {

    public static Block create(String type, int x, int y, int id) {
        Block temp = null;
        switch (type) {
            case "Source": {
                temp = new Source(x,y);
                break;
            }
            case "And": {
                temp = new And(x,y);
                break;
            }
            case "Nand": {
                temp = new Nand(x,y);
                break;
            }
            case "Or": {
                temp = new Or(x,y);
                break;
            }
            case "Nor": {
                temp = new Nor(x,y);
                break;
            }
            case "Not": {
                temp = new Not(x,y);
                break;
            }
            case "Xor": {
                temp = new Xor(x,y);
                break;
            }
            case "Output": {
                temp = new Output(x,y);
                break;
            }
            case "Connection": {
                temp = new Connection();
            }
            default: break;
        }
        if(temp != null) temp.forceID(id);
        return temp;
    }
}
