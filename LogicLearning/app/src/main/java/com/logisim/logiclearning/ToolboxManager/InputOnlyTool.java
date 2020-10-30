package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

import java.io.Serializable;

public abstract class InputOnlyTool extends Tool implements Serializable {
    protected InputPort oInputPort;

    public InputOnlyTool(Resources res, int image, GridManager grid){
        super(res,image,grid);

        oInputPort = new InputPort(this , connectionRadius);

        portArray = new Port[1];
        portArray[0]=oInputPort;

    }



    @Override
    public Port getOutputPort() {
        return null;
    }

    @Override
    public void deleteWire() {
        oInputPort.getAttachedWire().deleteSelf();
    }

    public void setPosition(float x, float y){
        super.setPosition(x,y);

        updatePorts(x,y);
    }

    @Override
    public Port checkPortAvailability() {
        if(oInputPort.getAttachedWire() == null){
            return oInputPort;
        }
        else
        {
            return null;
        }

    }

    public void updatePorts(float centerX , float centerY){

        float inputPositionX = centerX - (float)(defaultTool.image.getHeight()/2) - connectionRadius - 2;
        float inputPositionY = centerY;

        oInputPort.setxPosition(inputPositionX);
        oInputPort.setyPosition(inputPositionY);
    }



}
