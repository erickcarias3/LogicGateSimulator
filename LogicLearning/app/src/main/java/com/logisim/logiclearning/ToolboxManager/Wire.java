package com.logisim.logiclearning.ToolboxManager;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.logisim.logiclearning.SandboxViewTools.Position;

import java.io.IOException;
import java.io.Serializable;

/*
            :Welcome To the Wire Class:
    This Object holds information about a wire that is attached to an output and input port.
    this is how two tool Ports are linked together.
    this creates a sort of doubly linked list structure where there are pointers
    pointing to each gate
*/

public class Wire implements Serializable {

    public Port outputPort;
    public Port inputPort;

    Position bottomWireStart = new Position();
    Position bottomWireEnd = new Position();
    Position verticalWireStart = new Position();
    Position verticalWireEnd = new Position();
    Position topWireStart = new Position();
    Position topWireEnd = new Position();

    public Wire(Port outputPort, Port inputPort){
       this.outputPort = outputPort;
       this.inputPort = inputPort;
    }

    public Wire(Port outputPort){
        this.outputPort = outputPort;
        bottomWireStart.x = outputPort.getxPosition();
        bottomWireStart.y = outputPort.getyPosition();
    }
    //this method is called when a wiring to another gate has been successfully completed
    public void completeWiring(Port inputPort){
        this.inputPort = inputPort;
        topWireEnd.x = inputPort.getxPosition();
        topWireEnd.y = inputPort.getyPosition();
    }

    // this is where the wire is drawn based on the position values
    public void drawWire(Canvas canvas, Paint paint){

        if(topWireEnd.x == 0 && topWireEnd.y == 0 ){
            paint.setColor(Color.TRANSPARENT);
        }
        else{
            paint.setColor(Color.WHITE);
        }

        calculateWire();

        paint.setStrokeWidth(6f);

        canvas.drawLine(bottomWireStart.x, bottomWireStart.y, bottomWireEnd.x, bottomWireEnd.y, paint);
        canvas.drawLine(verticalWireStart.x, verticalWireStart.y, verticalWireEnd.x,verticalWireEnd.y, paint);
        canvas.drawLine(topWireStart.x, topWireStart.y, topWireEnd.x, topWireEnd.y, paint);

    }
    //this class is called whenver the signal for a wire is set to true
    public void drawSignalWire(Canvas canvas, Paint paint){
        calculateWire();

        paint.setColor(Color.CYAN);
        paint.setStrokeWidth(3.5f);

        canvas.drawLine(bottomWireStart.x, bottomWireStart.y, bottomWireEnd.x, bottomWireEnd.y, paint);
        canvas.drawLine(verticalWireStart.x, verticalWireStart.y, verticalWireEnd.x,verticalWireEnd.y, paint);
        canvas.drawLine(topWireStart.x, topWireStart.y, topWireEnd.x, topWireEnd.y, paint);
    }

    //this is where the calculation for the taxicab routing are done
    private void calculateWire(){
        bottomWireEnd.x = (bottomWireStart.x + topWireEnd.x)/2;
        bottomWireEnd.y = bottomWireStart.y;

        verticalWireStart.x = bottomWireEnd.x;
        verticalWireStart.y = bottomWireEnd.y;
        verticalWireEnd.x = bottomWireEnd.x;
        verticalWireEnd.y = topWireEnd.y;

        topWireStart.x = verticalWireEnd.x;
        topWireStart.y = verticalWireEnd.y;
    }

    public void updateOutputPortPosition(Port outputPort){
        bottomWireStart.x = outputPort.getxPosition();
        bottomWireStart.y = outputPort.getyPosition();
    }

    public void updateInputPortPosition(Port inputPort){
       topWireEnd.x = inputPort.getxPosition();
       topWireEnd.y = inputPort.getyPosition();
    }

    // this is where the evaluation request from the input port is routed to the output port
    public boolean evaluateWire(){
        return ((OutputPort) outputPort).evaluateParent();
    }

    public void deleteFromOutputPort(){
        outputPort.deleteSelectedWire(this);
    }

    public void deleteFromInput(){
        inputPort.deleteSelectedWire(this);
    }

    //unplugs itself from both portArray
    public void deleteSelf(){
        deleteFromInput();
        deleteFromOutputPort();
    }

    public void setWireStartX(float wireStartX) {
        this.bottomWireStart.x = wireStartX;
    }

    public void setWireStartY(float wireStartY) {
        this.bottomWireStart.y = wireStartY;
    }

    public void setWireEndX(float wireEndX) {
        this.topWireEnd.x = wireEndX;
    }

    public void setWireEndY(float wireEndY) {
        this.topWireEnd.y = wireEndY;
    }

    public Port getInputPort() {
        return inputPort;
    }

    public Port getOutputPort() {
        return outputPort;
    }

    private void readObject(java.io.ObjectInputStream in){
        try{
            in.defaultReadObject();
        }catch(IOException i){
            Log.d("Wire Deserialization", "Error Deserializing");
        }catch(ClassNotFoundException i){
            Log.d("Wire Deserialization", "Error Deserializing");
        }
    }

}
