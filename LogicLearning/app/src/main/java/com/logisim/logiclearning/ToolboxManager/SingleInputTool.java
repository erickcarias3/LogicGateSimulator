package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

import java.io.Serializable;

public abstract class SingleInputTool extends Tool implements Serializable {

    protected OutputPort outputPort;
    protected InputPort aInputPort;

    public SingleInputTool(Resources res, int image, GridManager grid){
        super(res,image,grid);
        outputPort = new OutputPort(this,connectionRadius);
        aInputPort =  new InputPort(this,connectionRadius);

        portArray = new Port[2];
        portArray[0]=outputPort;
        portArray[1]=aInputPort;
    }

    public void drawTool(Canvas canvas, Paint paint){

        super.drawTool(canvas,paint);

        outputPort.drawPort(canvas , paint);

        aInputPort.drawPort(canvas , paint);

    }

    @Override
    public Port checkPortAvailability() {
        if(aInputPort.getAttachedWire() == null){
            return aInputPort;
        }
        else
        {
            return null;

        }

    }

    public void updatePorts(float centerX , float centerY){
        float outputPositionX = centerX + (float)(defaultTool.image.getWidth()/2) - connectionRadius -  2;
        float outputPositionY = centerY;

        float aInputPositionY = centerY;
        float aInputPositionX = centerX - (float)(defaultTool.image.getWidth()/2) + connectionRadius + 2 ;


        outputPort.setxPosition(outputPositionX);
        outputPort.setyPosition(outputPositionY);

        aInputPort.setxPosition(aInputPositionX);
        aInputPort.setyPosition(aInputPositionY);
    }

    @Override
    public void deleteWire() {
        if(aInputPort.getAttachedWire() != null){
            aInputPort.getAttachedWire().deleteSelf();
        }
        else{
            System.out.println("no wires to delete");
        }
    }

    @Override
    public Port getOutputPort() {
        return outputPort;
    }


}
