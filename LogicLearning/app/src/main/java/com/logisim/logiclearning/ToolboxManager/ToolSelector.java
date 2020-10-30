package com.logisim.logiclearning.ToolboxManager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.logisim.logiclearning.SandboxGame;
import com.logisim.logiclearning.SandboxViewTools.GridManager;
import com.logisim.logiclearning.SandboxViewTools.Position;


public class ToolSelector {
    public boolean drawState = false;
    private boolean selectedTools;

    Position startRectPosition = new Position();
    Position endRectPosition = new Position();

    Rect rect = new Rect((int)startRectPosition.x,(int)startRectPosition.y,(int)endRectPosition.x,(int)endRectPosition.y);


    public void createSelectorTool(Position touchedPoint, MotionEvent event, GridManager entireGrid, SandboxGame currentSandboxGame){

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startRectPosition.x = touchedPoint.x;
                startRectPosition.y = touchedPoint.y;
                break;

            case MotionEvent.ACTION_MOVE:
                endRectPosition.x = touchedPoint.x;
                endRectPosition.y = touchedPoint.y;
                rect.set((int)startRectPosition.x,(int)startRectPosition.y,(int)endRectPosition.x,(int)endRectPosition.y);
                checkForSelectedTools(entireGrid,currentSandboxGame);

                break;

            case MotionEvent.ACTION_UP:
                checkForSelectedTools(entireGrid,currentSandboxGame);
                if(entireGrid.getSelectedGridSquares().isEmpty()){
                    selectedTools = false;
                }else{
                    selectedTools = true;
                }

                startRectPosition = new Position();
                endRectPosition = new Position();
                drawState = false;

                break;

        }
    }

    public void checkForSelectedTools(GridManager entireGrid, SandboxGame currentSandboxGame){
        for (Tool tool : currentSandboxGame.getAllUsedTools()) {
            if (rect.contains((int)tool.getX(),(int)tool.getY())) {
                entireGrid.addToSelectedToolArray((int)tool.getX(),(int)tool.getY());
            }
        }
    }



    public void drawSelectorTool(Paint paint, Canvas viewCanvas, SandboxGame currentSandboxGame){

        paint.setColor(Color.LTGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4f);
        viewCanvas.drawRect(startRectPosition.x,startRectPosition.y,endRectPosition.x,endRectPosition.y, paint);
    }

    public void enableSelectorDraw(){
        drawState = true;
    }

    public void setToolsSelected(boolean toolsSelected){
        this.selectedTools = toolsSelected;
    }

    public boolean getDrawState(){
        return drawState;
    }

    public boolean toolsSelected(){
        return selectedTools;
    }


}
