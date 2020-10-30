package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

import java.io.Serializable;

public abstract class OutputOnlyTool extends Tool implements Serializable {

    protected OutputPort oOutputPort;

    public OutputOnlyTool(Resources res, int image, GridManager grid){
        super(res,image,grid);

        oOutputPort = new OutputPort(this,connectionRadius);
        portArray = new Port[1];
        portArray[0]=oOutputPort;


    }

    @Override
    public Port getOutputPort() {
        return oOutputPort;
    }

    @Override
    public Port checkPortAvailability() {
        return null;
    }

    @Override
    public void deleteWire() {
    }


    public void updatePorts(float centerX , float centerY){

        float outputPositionX = centerX + (float)(defaultTool.image.getHeight()/4) + 15;
        float outputPositionY = centerY;

        oOutputPort.setxPosition(outputPositionX);
        oOutputPort.setyPosition(outputPositionY);

    }
}
