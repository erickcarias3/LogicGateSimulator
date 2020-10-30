package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.logisim.logiclearning.SandboxViewTools.GridManager;
import com.logisim.logiclearning.R;
import com.logisim.logiclearning.SaveManager.SerialBitmap;

public class Switch extends OutputOnlyTool {

    private boolean toggleState = false;
    private SerialBitmap onSwitch;

    public Switch(Resources res, int image, GridManager grid){
        super(res,image,grid);

        onSwitch = new SerialBitmap(res, R.drawable.onswitch, grid);
    }

    @Override
    public void drawTool(Canvas canvas , Paint paint){
        if(this.toggleState){
            canvas.drawBitmap(onSwitch.image, getX()-(onSwitch.image.getWidth()/2), getY()-(onSwitch.image.getHeight()/2), null);
        }
        else{
            //draw default off state.
            super.drawTool(canvas,paint);
        }

        oOutputPort.drawPort(canvas,paint);

    }


    public boolean getToggleState(){
        return toggleState;

    }

    public void toggleSwitch(){
        this.toggleState = !this.toggleState;
    }

    public boolean evaluateSelf(){
        return toggleState;
    }
}
