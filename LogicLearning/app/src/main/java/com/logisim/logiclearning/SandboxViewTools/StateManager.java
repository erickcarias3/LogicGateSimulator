package com.logisim.logiclearning.SandboxViewTools;


/*
            :Welcome To the StateManager:
     This is the Manager that holds information on what the system should be performing
     A Various booleans are used to traffic function in the View Manager
     Different states are enabled and disabled when the screen/buttons are interacted with

*/
public class StateManager {

    private boolean drawingState;
    private boolean deleteState;
    private boolean moveState;
    private boolean toolBufferSavedState;
    private boolean passiveTouchState;
    private boolean wiringState;
    private boolean longPress;
    private boolean singleTapState;
    private boolean doubleTapState;
    private boolean evaluations;
    private boolean wireCutting;

    public StateManager() {
        disableAllStates();
    }

    public void disableAllStates() {
        this.disableDrawingState();
        this.disableDeleteState();
        this.disableMoveState();
        this.disableToolBufferSavedState();
        this.disablePassiveTouchState();
        this.disableWiringState();
        this.disableLongPressState();
        this.disableSingleTapState();
        this.disableEvaluations();
        this.disableWireCutting();
    }

    public void enableDrawingState(){this.drawingState = true;}
    public void enableDeleteState(){this.deleteState = true;}
    public void enableMoveState(){this.moveState = true;}
    public void enableToolBufferSavedState(){this.toolBufferSavedState = true;}
    public void enablePassiveTouchState(){this.passiveTouchState = true;}
    public void enableWiringState(){this.wiringState = true;}
    public void enableLongPressState(){this.longPress = true;}
    public void enableSingleTapState(){this.singleTapState = true;}
    public void enableDoubleTapState(){this.doubleTapState = true;}
    public void enableEvaluations(){this.evaluations = true;}
    public void enableWireCutting(){this.wireCutting = true;}


    public void disableDrawingState(){this.drawingState = false;}
    public void disableDeleteState(){this.deleteState = false;}
    public void disableMoveState(){this.moveState = false;}
    public void disableToolBufferSavedState(){this.toolBufferSavedState = false;}
    public void disablePassiveTouchState(){this.passiveTouchState = false;}
    public void disableWiringState(){this.wiringState = false;}
    public void disableLongPressState(){this.longPress = false;}
    public void disableSingleTapState(){this.singleTapState = false;}
    public void disableDoubleTapState(){this.doubleTapState = false;}
    public void disableEvaluations(){ this.evaluations = false;}
    public void disableWireCutting(){this.wireCutting = false;}


    public boolean getDrawingState(){return this.drawingState;}
    public boolean getDeleteState(){return this.deleteState;}
    public boolean getMoveState(){return this.moveState;}
    public boolean getToolBufferSavedState(){return this.toolBufferSavedState;}
    public boolean getWiringState(){return this.wiringState;}
    public boolean getLongPress(){ return longPress; }
    public boolean getSingleTapState(){return this.singleTapState;}
    public boolean getDoubleTapState(){return this.doubleTapState;}
    public boolean getEvaluations(){return this.evaluations;}
    public boolean getWireCutting(){return this.wireCutting;}


    /*
        passive Touch state logic is stored in these 2 methods
        The passive Touch State is enabled when all other states are not enabled
        When the Passive Touch State is on the user can interact with the Sandbox
        this ensures no other running activity is happening at the same time
     */
    public boolean getPassiveTouchState(){
        evaluatePassiveTouchState();
        return this.passiveTouchState;
    }

    public void evaluatePassiveTouchState(){

        if(moveState||deleteState||drawingState||wireCutting){
            passiveTouchState = false;
        }
        else{
            passiveTouchState = true;
        }

    }


    public void setLongPress(boolean state){
        longPress = state;
    }


    public void printStates(){
        System.out.println(" MOVE: " + moveState + "    DELETE: " + deleteState);
        System.out.println(" DRAWING: " + drawingState + "    BUFFER: " + deleteState);
        System.out.println(" PASSIVE: " + passiveTouchState);

    }
}
