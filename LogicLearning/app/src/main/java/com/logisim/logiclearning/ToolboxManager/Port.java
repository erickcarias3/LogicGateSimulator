package com.logisim.logiclearning.ToolboxManager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

/*
            :Welcome To the Port Class:
       This class Holds various methods used by the input and output port classes
*/

public abstract class Port implements Serializable {

    public Tool parent;
    float xPosition;
    float yPosition;
    float connectionRadius;

    public Port(Tool parent, float connectionRadius){
        this.parent = parent;
        this.connectionRadius = connectionRadius + 1;
    }

    public abstract void addWire(Wire wire);

    public abstract void deleteSelectedWire(Wire wireToBeDeleted);

    public abstract void unplugSelf();

    public abstract void updateWirePositions();

    public abstract boolean connectedToPort(Port selectedPort);

    //This method draws the port to the current parent
    public void drawPort(Canvas canvas, Paint paint){

        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(xPosition,yPosition,connectionRadius,paint);
    }

    //Setters and Getters for out port Information
    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getxPosition() {
        return xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public Tool getParent() {
        return parent;
    }


}