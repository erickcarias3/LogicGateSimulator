package com.logisim.logiclearning.ToolboxManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

/*
            :Welcome To the InputPort Class:
        This class is similar to an output port but only
        one wire can be connected to an input port.
        The input port also handles the drawing of the attached wire

*/

public class InputPort extends Port implements Serializable {

    Wire attachedWire;

    public InputPort(Tool parent, float connectionRadius){
        super(parent,connectionRadius );

    }

    //Draws the port based on if it is wired or not
    public void drawPort(Canvas canvas, Paint paint){
        if(  parent.getX() == 0 && parent.getY() == 0 ){
            paint.setColor(Color.TRANSPARENT);
            canvas.drawCircle(xPosition,yPosition,connectionRadius,paint);
        }
        else if(attachedWire == null){
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(xPosition,yPosition,connectionRadius,paint);
        }
        else{
            super.drawPort(canvas,paint);
        }

    }

    //draws the wire that it is attached too
    public void drawWire(Canvas canvas, Paint paint){
        if(attachedWire != null) {
            attachedWire.drawWire(canvas,paint);
            attachedWire.getOutputPort().drawPort(canvas, paint);
            checkWireSignal(canvas,paint);
        }

    }
    //this method checks for a true value in the wire
    //if the wire is true the signal for the wire will be drawn
    public void checkWireSignal(Canvas canvas, Paint paint){
        if(attachedWire != null){
            boolean signal =false;

            try{
                signal = evaluateInput();
            }
            catch (NullPointerException incompleteCircuit){
                System.out.println("Null Pointer");
            }

            if(signal){
                    attachedWire.drawSignalWire(canvas , paint);
            }
        }
    }

    //deletes/unplugs the wire that is connected to it
    public void unplugSelf(){
        if(attachedWire !=null){
            attachedWire.deleteFromOutputPort();
        }
    }

    //this method evaluates the inputs of the output of the attached wire
    public boolean evaluateInput(){

        if(attachedWire == null){
            return false;
        }
        else{
            return attachedWire.evaluateWire();
        }

    }

    //deletes a passed wire from the set
    public void deleteSelectedWire(Wire wireToBeDeleted){
        if(wireToBeDeleted == attachedWire){
            attachedWire = null;
        }
        else{
            System.out.println("ERROR");
        }

    }

    public Wire getAttachedWire(){
        return attachedWire;
    }

    @Override
    public boolean connectedToPort(Port selectedPort) {
        if(attachedWire==null){
            return false;
        }
        else if(attachedWire.getOutputPort() == selectedPort){
            return true;
        }
        else{
            return false;
        }
    }

    //updates the position of the wire end location
    public void updateWirePositions(){
        if(attachedWire != null){
            attachedWire.updateInputPortPosition(this);

        }
    }

    @Override
    public void addWire(Wire wire) {
        attachedWire = wire;
    }
}
