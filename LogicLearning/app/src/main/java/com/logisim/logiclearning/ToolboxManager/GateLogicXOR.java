package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

public class GateLogicXOR extends DoubleInputTool {

    public GateLogicXOR(Resources res, int image, GridManager grid) {

        super(res,image,grid);

    }

    public boolean evaluateSelf() {
        if(!aInputPort.evaluateInput() && bInputPort.evaluateInput()){
            return true;
        }
        else if(aInputPort.evaluateInput() && !bInputPort.evaluateInput()){
            return true;
        }
        else{
            return false;
        }

    }
}
