package com.logisim.logiclearning.ToolboxManager;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.logisim.logiclearning.SandboxViewTools.GridManager;
import com.logisim.logiclearning.R;
import com.logisim.logiclearning.SaveManager.SerialBitmap;

public class LED extends InputOnlyTool {

    private SerialBitmap lightOnImage;
    private boolean lightState = false;

    public LED(Resources res, int image, GridManager grid){
        super(res,image,grid);
        lightOnImage = new SerialBitmap(res, R.drawable.lightbulbon , grid);

    }

    public void evaluate(){
       this.lightState = oInputPort.evaluateInput();
    }

    @Override
    public void drawTool(Canvas canvas, Paint paint) {
        if(lightState) {
            canvas.drawBitmap(lightOnImage.image, getX() - (lightOnImage.image.getWidth()/2)  , getY() - (lightOnImage.image.getHeight()/2) , null);
        }
        else{
            super.drawTool(canvas,paint);
        }

        oInputPort.drawPort(canvas, paint);

    }

    public boolean evaluateSelf(){
        return lightState;
    }

    public void disableLight(){
        lightState = false;
    }

}
