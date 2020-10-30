package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

public class GateLogicXNOR extends DoubleInputTool {


    public GateLogicXNOR(Resources res, int image, GridManager grid) {

        super(res,image,grid);

    }

    public void updatePorts(float centerX , float centerY){

        float outputPositionX = centerX + (float)(defaultTool.image.getWidth()/2) - connectionRadius -  2;
        float outputPositionY = centerY;

        float aInputPositionX = centerX - (float)( defaultTool.image.getWidth()/2) + connectionRadius + 2 ;
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

    public boolean evaluateSelf() {
        if(aInputPort.evaluateInput() && bInputPort.evaluateInput()){
            return true;
        }
        else if((!aInputPort.evaluateInput() && !bInputPort.evaluateInput())){
            return true;
        }
        else{
            return false;
        }

    }
}
