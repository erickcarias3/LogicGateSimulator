package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

public class GateLogicOR extends DoubleInputTool {


    public GateLogicOR(Resources res, int image, GridManager grid){
        super(res,image,grid);

    }



    public boolean evaluateSelf() {
        return aInputPort.evaluateInput() || bInputPort.evaluateInput();
    }
}
