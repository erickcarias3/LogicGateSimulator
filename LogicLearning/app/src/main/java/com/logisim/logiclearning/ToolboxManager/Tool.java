package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.logisim.logiclearning.SandboxViewTools.GridManager;
import com.logisim.logiclearning.SandboxViewTools.Position;
import com.logisim.logiclearning.SaveManager.SerialBitmap;

import java.io.IOException;
import java.io.Serializable;

public abstract class Tool implements Serializable {

    Port portArray[];
    protected SerialBitmap defaultTool;
    protected Position toolPosition = new Position();
    public float connectionRadius;
    //public Paint paint = new Paint();

    //create a bitmap with the passed image and set the spawn coordinates
    public Tool(Resources res, int image, GridManager grid){
        defaultTool = new SerialBitmap(res, image, grid);
        toolPosition.x = 0;
        toolPosition.y = 0;
        connectionRadius = grid.getCellWidth()/16;

    }

    public void drawWires(Canvas canvas, Paint paint){
        for(int i = 0; i < portArray.length ; i++){
            if(portArray[i] instanceof InputPort){
                ((InputPort) portArray[i]).drawWire(canvas,paint);
            }
        }
    }

    public void drawTool(Canvas canvas, Paint paint){
        if( !(toolPosition.x == 0 & toolPosition.y == 0) ){
            canvas.drawBitmap(defaultTool.image, toolPosition.x - (defaultTool.image.getWidth() / 2), toolPosition.y - (defaultTool.image.getHeight() / 2), null);        }
        else{
            paint.setColor(Color.TRANSPARENT);
        }

    }

    public void updatePortWires(){
        for(int i = 0; i < portArray.length ; i++){
            portArray[i].updateWirePositions();
        }
    }

    public void removeAllConnections(){
        for(int i = 0; i < portArray.length; i++){
            portArray[i].unplugSelf();
        }
    }

    private void readObject(java.io.ObjectInputStream in){
        try{
            in.defaultReadObject();

        }catch(IOException i){
            Log.d("Tool Deserialization", "Error Deserializing");
        }catch(ClassNotFoundException i){
            Log.d("Tool Deserialization", "Error Deserializing");
        }
    }

    public void setPosition(float x, float y){
        setImagePosition(x,y);
        updatePorts(x,y);
    }

    public abstract void deleteWire();
    public abstract boolean evaluateSelf();
    public abstract Port checkPortAvailability();
    public abstract Port getOutputPort();
    public abstract void updatePorts(float centerX , float centerY);

    public void setImagePosition(float x, float y){this.toolPosition.x = x;this.toolPosition.y = y;}
    public float getX(){return this.toolPosition.x;}
    public float getY(){return this.toolPosition.y;}
    public Port[] getPortArray() {
        return portArray;
    }
}

