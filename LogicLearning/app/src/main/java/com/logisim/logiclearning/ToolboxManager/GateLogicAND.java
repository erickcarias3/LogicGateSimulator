
package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;

import com.logisim.logiclearning.SandboxViewTools.GridManager;

public class GateLogicAND extends DoubleInputTool {

    public GateLogicAND(Resources res, int image, GridManager grid) {

        super(res,image,grid);


    }


    public boolean evaluateSelf() {

        return (aInputPort.evaluateInput() && bInputPort.evaluateInput());

    }

}
