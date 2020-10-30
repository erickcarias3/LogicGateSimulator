package com.logisim.logiclearning;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import com.logisim.logiclearning.SandboxViewTools.GridManager;
import com.logisim.logiclearning.SandboxViewTools.Position;
import com.logisim.logiclearning.ToolboxManager.GateLogicAND;
import com.logisim.logiclearning.ToolboxManager.LED;
import com.logisim.logiclearning.ToolboxManager.GateLogicNAND;
import com.logisim.logiclearning.ToolboxManager.GateLogicNOR;
import com.logisim.logiclearning.ToolboxManager.GateLogicNOT;
import com.logisim.logiclearning.ToolboxManager.GateLogicOR;
import com.logisim.logiclearning.ToolboxManager.Switch;
import com.logisim.logiclearning.ToolboxManager.Tool;
import com.logisim.logiclearning.ToolboxManager.GateLogicXNOR;
import com.logisim.logiclearning.ToolboxManager.GateLogicXOR;

import java.util.HashSet;

/*
            :Welcome To the Current Sandbox Game Manager:

    The Sandbox Current Game Manager includes all the information used to
    manipulate information for the activity.
    This is the main engine that send information to the view and manipulates the
    raw data for out current game.
    Logic for the Sandbox View Draw method is stored here as well

*/


public class SandboxGame {

    private HashSet<Tool> usedTools;
    private HashSet<Tool> completedCircuits;

    //toolBuffer is used to store a tool that is about to be added to the usedTools set or if a
    //specific tool is to be moved around on the screen. This allows us to be able to visually drag
    //any tool object around on the screen.
    private Tool toolBuffer;

    //Used to save the position of the toolBuffer when a tool is going to be moved around by the
    //user.
    private Position toolBufferPos;

    public SandboxGame() {
        usedTools = new HashSet<>();
        toolBufferPos = new Position();
        completedCircuits = new HashSet<>();
    }


    public void evaluateCircuits(){
        for (Tool tool : usedTools) {
            if (tool instanceof LED) {
                ( (LED) tool).evaluate();
            }
        }
    }

    /*
        These Methods handle the logic for drawing different instances of the onDraw Method
        in the View Manager
     */
    public void drawTools(Canvas canvas, Paint paint) {
        if(!usedTools.isEmpty()){
            for(Tool tool : usedTools) {
                tool.drawWires(canvas,paint);
            }
            for(Tool tool : usedTools) {
                tool.drawTool(canvas,paint);
            }
        }
    }

    //Used for when a new tool is added, but has not been added to the usedTool Set yet.
    public void drawToolBuffer(Canvas canvas,Paint paint) {
        if(toolBuffer != null) {
            toolBuffer.drawWires(canvas,  paint);
            toolBuffer.drawTool(canvas, paint);
        }
    }


    //allows us to be able to drag and move the tool around the screen.
    //creates a temp position object to save original location.
    public void setUpMove(Rect touchedCell){
        toolBuffer = this.getTool(touchedCell);

        if(toolBuffer != null){
            this.saveToolBufferPos();
        }
    }

    //searches and deletes tool from the current game
    //this includes deleting itself from any wire connections it previously was attached too
    public void deleteTool(Rect touchedCell) {
        Tool toolToBeDeleted = this.getTool(touchedCell);
        if(toolToBeDeleted != null) {
            toolToBeDeleted.removeAllConnections();
            usedTools.remove(this.getTool(touchedCell));
            if(completedCircuits.contains(this.getTool(touchedCell))){
                completedCircuits.remove(this.getTool(touchedCell));
            }
        }

    }

    //this tool searches for a tool in the set with a passed in rectangle cell
    // if none found return nothing
    public Tool getTool(Rect touchedCell){
        if(touchedCell != null) {
            for (Tool tool : usedTools) {
                if (tool.getX() == touchedCell.centerX() && tool.getY() == touchedCell.centerY()) {
                    return tool;
                }
            }
        }
        return null;
    }

    public HashSet<Tool> getAllUsedTools(){
        return usedTools;
    }

    /*
        All the Logic for a tool buffer is handled with these 7 methods
        A toolBuffer is used as a drawable object before being added to the usedTools container.
        This is done in order to allow users to be able to drag and drop the selected tool
        in any position on the grid. Once the tool is dropped, it is then added to the usedTools
    */

    public void createToolBuffer(Resources resources , int type, GridManager grid) {

        switch (type){
            case R.drawable.andgate:
                this.toolBuffer = new GateLogicAND(resources,type,grid);
                break;
            case R.drawable.offswitch:
                this.toolBuffer = new Switch(resources,type,grid);
                break;
            case R.drawable.orgate:
                this.toolBuffer = new GateLogicOR(resources,type,grid);
                break;
            case R.drawable.notgate:
                this.toolBuffer = new GateLogicNOT(resources,type,grid);
                break;
            case R.drawable.lightbulboff:
                this.toolBuffer = new LED(resources,type,grid);
                break;
            case R.drawable.nandgate:
                this.toolBuffer = new GateLogicNAND(resources,type,grid);
                break;
            case R.drawable.norgate:
                this.toolBuffer = new GateLogicNOR(resources,type,grid);
                break;
            case R.drawable.xnorgate:
                this.toolBuffer = new GateLogicXNOR(resources,type,grid);
                break;
            case R.drawable.xorgate:
                this.toolBuffer = new GateLogicXOR(resources,type,grid);
                break;
        }
    }

    public void setToolBufferPos(float x, float y) {
        if(toolBuffer != null) {
            toolBuffer.setPosition(x, y);
            toolBuffer.updatePortWires();

        }
    }

    public void addToolBuffer(){
        usedTools.add(toolBuffer);
        resetToolBuffer();
    }

    private void saveToolBufferPos(){
        this.toolBufferPos.x = toolBuffer.getX();
        this.toolBufferPos.y = toolBuffer.getY();
    }

    public void reloadToolBufferPos() {
        this.setToolBufferPos(this.toolBufferPos.x, this.toolBufferPos.y);
    }

    public void resetToolBuffer() {
        this.toolBuffer = null;
    }

    /*
        These methods handle the logic for interacting with switches in the sandbox
     */
    public void toggleSwitch(Rect touchedCell) {
        ((Switch)getTool(touchedCell)).toggleSwitch();
    }

    public void disableAllSwitches(){
        if(!usedTools.isEmpty()) {
            for (Tool tool : usedTools) {
                if (tool instanceof Switch && ((Switch) tool).getToggleState()) {
                    ((Switch) tool).toggleSwitch();
                }
            }
        }
    }

    public void setUsedTools(HashSet<Tool> usedTools){this.usedTools = usedTools;}
}
