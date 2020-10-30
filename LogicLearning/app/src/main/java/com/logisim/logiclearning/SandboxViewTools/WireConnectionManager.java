package com.logisim.logiclearning.SandboxViewTools;


import android.graphics.Canvas;
import android.view.View;

import com.logisim.logiclearning.ToolboxManager.InputPort;
import com.logisim.logiclearning.ToolboxManager.LED;
import com.logisim.logiclearning.ToolboxManager.OutputOnlyTool;
import com.logisim.logiclearning.ToolboxManager.Port;
import com.logisim.logiclearning.ToolboxManager.Tool;
import com.logisim.logiclearning.ToolboxManager.Wire;

/*
            :Welcome To the Connection Handler:
       This class manages the logic for connecting two gates together
       The logic for creating wires is here
       In order to create a wire connection the wires need to be hooked up to a
       Output Port from the Output gate the wire is being dragged from and
       and input port from the port the will will be connected to

*/

public final class WireConnectionManager {

    private Wire tempWire;
    private Port toolOutputPort;


    public void ConnectionHandler(){

    }
    //prepares the wiring from the output tool
    //this catches instances of an LED because
    //wires cannot be drawn from LED's
    public boolean prepGateConnection(Tool tool){

        if(tool == null){
            return false;
        }
        else if(tool instanceof LED){
            return false;
        }
        else{
            toolOutputPort = tool.getOutputPort();
            tempWire = new Wire(toolOutputPort);
            return true;
        }

    }

    //This method handles the logic for connecting a selected gate is one is passed in
    //If there is a valid tool with an open port available the wire is hooked up to either port
    public boolean connectGates(Tool inputTool, View currentGame , Canvas canvas){

        if(inputTool == null || inputTool instanceof OutputOnlyTool){
            disableWiring();
            return false;
        }

        Port openPort = inputTool.checkPortAvailability();

        if(openPort == null || inputAndOutputSameParent(openPort) || catchInfiniteLoop(openPort)){
            disableWiring();
            return false;
        }

        tempWire.completeWiring(openPort);
        toolOutputPort.addWire(tempWire);
        openPort.addWire(tempWire);


        if(infiniteLoopLastCheck(currentGame,canvas)){
            disableWiring();
            return false;
        }

        disableWiring();
        return true;
    }

    private boolean inputAndOutputSameParent(Port openPort){
        if(openPort.getParent() == toolOutputPort.getParent()){
            return true;
        }
        else {
            return false;
        }
    }

    private boolean infiniteLoopLastCheck(View currentGame, Canvas canvas){
        try{
            currentGame.draw(canvas);
        }
        catch(StackOverflowError e){
            tempWire.deleteSelf();
            return true;

        }
        catch (OutOfMemoryError e){
            tempWire.deleteSelf();
            return true;
        }
        return false;
    }

    private boolean catchInfiniteLoop(Port openPort){
        boolean infiniteLoopChecker = false;

        Port[] outputParentPorts = toolOutputPort.getParent().getPortArray();

        for(int i = 0; i < outputParentPorts.length ; i++){
             if(outputParentPorts[i] instanceof InputPort){
                 infiniteLoopChecker = outputParentPorts[i].connectedToPort(openPort.getParent().getOutputPort());
                 return infiniteLoopChecker;
             }
        }

        return infiniteLoopChecker;
    }

    //This method disables the wiring for the connection handler by setting the temp wire to null
    private void disableWiring(){
        tempWire = null;
        toolOutputPort = null;
    }

    public Wire getTempWire() {
        return tempWire;
    }
}
