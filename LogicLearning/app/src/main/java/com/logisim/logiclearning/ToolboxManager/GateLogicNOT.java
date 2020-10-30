package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;


public class GateLogicNOT extends SingleInputTool {

    public GateLogicNOT(Resources res, int image, GridManager grid){
        super(res,image,grid);
    }

    @Override
    public boolean evaluateSelf(){
        return !aInputPort.evaluateInput();
    }

}
