package com.logisim.logiclearning.ToolboxManager;


import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

public class GateLogicNAND extends DoubleInputTool {

    public GateLogicNAND(Resources res, int image, GridManager grid) {

        super(res, image, grid);

    }

    public boolean evaluateSelf() {
        if(aInputPort.evaluateInput() && bInputPort.evaluateInput()){
            return true;
        }
        else{
            return false;
        }

    }
}
