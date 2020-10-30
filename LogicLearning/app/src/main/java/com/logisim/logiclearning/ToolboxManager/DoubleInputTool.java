package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

import java.io.Serializable;

public abstract class DoubleInputTool extends Tool implements Serializable {
    public OutputPort outputPort;
    public InputPort aInputPort, bInputPort;

    public DoubleInputTool(Resources res, int image, GridManager grid){

        super(res, image, grid);

        outputPort = new OutputPort(this, connectionRadius);
        aInputPort = new InputPort(this, connectionRadius);
        bInputPort = new InputPort(this, connectionRadius);

        portArray = new Port[3];
        portArray[0] = outputPort;
        portArray[1] = aInputPort;
        portArray[2] = bInputPort;
    }

    @Override
    public Port checkPortAvailability() {
        if (aInputPort.getAttachedWire() == null) {
            return aInputPort;
        } else if (bInputPort.getAttachedWire() == null) {
            return bInputPort;
        } else {
            return null;
        }

    }

    public void drawTool(Canvas canvas, Paint paint) {

        super.drawTool(canvas,paint);

        outputPort.drawPort(canvas, paint);

        aInputPort.drawPort(canvas, paint);
        bInputPort.drawPort(canvas, paint);

    }

    public void updatePorts(float centerX , float centerY){

        float outputPositionX = centerX + (float)(defaultTool.image.getWidth()/2) - connectionRadius -  2;
        float outputPositionY = centerY;

        float aInputPositionX = centerX - (float)(defaultTool.image.getWidth()/2) + connectionRadius + 2 ;
        float aInputPositionY = centerY - (float)( defaultTool.image.getHeight()/4) ;

        float bInputPositionX = centerX - (float)(defaultTool.image.getWidth()/2) + connectionRadius + 2 ;
        float bInputPositionY = centerY + (float)( defaultTool.image.getHeight()/4) ;

        outputPort.setxPosition(outputPositionX);
        outputPort.setyPosition(outputPositionY);

        aInputPort.setxPosition(aInputPositionX);
        aInputPort.setyPosition(aInputPositionY);

        bInputPort.setxPosition(bInputPositionX);
        bInputPort.setyPosition(bInputPositionY);

    }

    @Override
    public void deleteWire() {
        if (aInputPort.getAttachedWire() != null) {
            aInputPort.getAttachedWire().deleteSelf();
        } else if (bInputPort.getAttachedWire() != null) {
            bInputPort.getAttachedWire().deleteSelf();
        } else {
            System.out.println("no wires to delete");
        }
    }


    @Override
    public Port getOutputPort() {
        return outputPort;
    }

    public Port getaInputPort(){
        return aInputPort;
    }

    public Port getbInputPort(){
        return bInputPort;
    }

}
