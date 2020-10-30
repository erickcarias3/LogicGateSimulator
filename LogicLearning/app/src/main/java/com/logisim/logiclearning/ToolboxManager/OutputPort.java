package com.logisim.logiclearning.ToolboxManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;
import java.util.ArrayList;

/*
            :Welcome To the OutputPort Class:
        This class is a more specific version of a PORT
        This call is used to hold wire information and communicated with the input port to pass and receive
        information around from tool to tool.
*/

public class OutputPort extends Port implements Serializable {

    private ArrayList<Wire> wireConnections = new ArrayList<>();

    public OutputPort(Tool parent, float connectionRadius){
        super(parent,connectionRadius );
    }
    //Draws the port based on if it is wired or not
    public void drawPort(Canvas canvas , Paint paint){
        if(  parent.getX() == 0 && parent.getY() == 0 ){
            paint.setColor(Color.TRANSPARENT);
            canvas.drawCircle(xPosition,yPosition,connectionRadius,paint);
        }
        else if(wireConnections.isEmpty()){
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(xPosition,yPosition,connectionRadius,paint);
        }
        else{
            super.drawPort(canvas, paint);
        }

    }
    //this method unplugs all wires from attached input portArray
    public void unplugSelf(){
        for(Wire wire : wireConnections){
            wire.deleteFromInput();
        }
    }
    //deletes a passed wire from the set
    public void deleteSelectedWire(Wire wireToBeDeleted){
        wireConnections.remove(wireToBeDeleted);
    }
    //this method evaluates the parent (Gate) it is attached too
    public boolean evaluateParent(){
       return parent.evaluateSelf();
    }

    public boolean connectedToPort(Port selectedPort){
        for(Wire wire : wireConnections){
            if(wire.getInputPort() == selectedPort){
                return true;
            }
        }
        return false;
    }

    //this function updates all the positions of the wire when in movement
    public void updateWirePositions(){
        if(!wireConnections.isEmpty()){
            for (Wire wires : wireConnections) {
                wires.updateOutputPortPosition(this);
            }
        }
    }

    @Override
    public void addWire(Wire wire) {
        wireConnections.add(wire);
    }


}
