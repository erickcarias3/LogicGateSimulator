package com.logisim.logiclearning;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.logisim.logiclearning.SandboxViewTools.Position;
import com.logisim.logiclearning.SandboxViewTools.WireConnectionManager;
import com.logisim.logiclearning.SandboxViewTools.DisplayManger;
import com.logisim.logiclearning.SandboxViewTools.GestureManager;
import com.logisim.logiclearning.SandboxViewTools.GridManager;
import com.logisim.logiclearning.SandboxViewTools.StateManager;
import com.logisim.logiclearning.ToolboxManager.Switch;
import com.logisim.logiclearning.ToolboxManager.ToolSelector;

/*
            :Welcome To the Sandbox View Manager:

    The Sandbox View Manager handles the logic for the view controller
    This view Directly Interacts with the grid and the Current Sandbox Game
    To display information and Route Button and Gesture Information
    to the correct Logic

*/

public class SandboxView extends View  {

    public Canvas backgroundCanvas;
    Context context;
    GridManager grid;
    SandboxGame currentGame;
    StateManager stateManager;
    GestureDetector motionDetector;
    WireConnectionManager wireConnectionManager;
    Rect touchedCell;
    Position touchedPoint;
    Bitmap myBitmap;
    Paint paint = new Paint();
    ToolSelector selector = new ToolSelector();
    Position mPosition = new Position();
    Position mLasttouch = new Position();
    private final MediaPlayer switchSound;



    public boolean pan = false;



    public SandboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        backgroundCanvas = new Canvas();
        stateManager = new StateManager();
        currentGame = new SandboxGame();
        wireConnectionManager = new WireConnectionManager();
        GestureManager gestureHandler = new GestureManager(stateManager);
        motionDetector = new GestureDetector(context, gestureHandler );
        switchSound = MediaPlayer.create(context,R.raw.switchsound);
    }

    /*
        The Draw Method is Overridden form the view superclass to include
        logic for our Sandbox animation.
        Various animations are displayed and handled correctly in this method
        We also search and save completed circuits from the Sandbox
        In this Method

     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if(myBitmap != null) {
            canvas.save();
            canvas.translate(mPosition.x, mPosition.y);
            grid.drawGrid(canvas);

            try{
                currentGame.evaluateCircuits(/*context*/);


                if (stateManager.getWiringState()) {
                    wireConnectionManager.getTempWire().drawWire(canvas, paint);
                }

                currentGame.drawTools(canvas, paint);

                if (stateManager.getDrawingState()) {
                    currentGame.drawToolBuffer(canvas, paint);
                }

                if (selector.getDrawState()) {
                    selector.drawSelectorTool(paint, canvas, currentGame);
                    grid.invalidateSelectedTools();
                }

            }
            catch(StackOverflowError e){
                clearBoard();
                Toast.makeText(context,"Infinite Loop detected, Board Cleared",Toast.LENGTH_SHORT).show();

            }
            catch (OutOfMemoryError e){
                clearBoard();
                Toast.makeText(context,"Infinite Loop detected, Board Cleared",Toast.LENGTH_SHORT).show();

            }

            canvas.restore();
        }

        invalidate();

    }

    /*
        on Creation View Configure method
        this method creates our view based on
        the device Screen information
     */

    public void setCurrentViewWithDisplayInfo(DisplayManger display) {

        myBitmap = Bitmap.createBitmap(display.getScreenWidth()*2, display.getScreenHeight()*2, Bitmap.Config.ARGB_8888);
        backgroundCanvas = new Canvas(myBitmap);

        int width = backgroundCanvas.getWidth();
        int height = backgroundCanvas.getHeight();


        grid = new GridManager(20,20,width,height);


        draw(backgroundCanvas);
    }

    /*
        Various activity Button logic is handled with the next 5 methods
     */

    public void testButtonLogic(Resources resources , int pictureID , MotionEvent event){
        if(!stateManager.getDrawingState()){
            currentGame.disableAllSwitches();
            stateManager.disableAllStates();
            stateManager.enableDrawingState();
            currentGame.createToolBuffer(resources, pictureID, grid);

        }
        this.onTouchEvent(event);
    }


    public void moveToolButtonLogic(){
        grid.invalidateHitboxes();
        stateManager.disableAllStates();
        stateManager.enableMoveState();
    }

    public void WireCuttingToggleLogic(){
        stateManager.disableAllStates();
        stateManager.enableWireCutting();

    }

    public void disableWireCutting(){
        stateManager.disableWireCutting();
    }

    public void clearBoard(){
        grid.invalidateHitboxes();
        currentGame.getAllUsedTools().clear();
    }

    public void deleteToolButtonLogic(){
        currentGame.disableAllSwitches();
        stateManager.disableAllStates();

        if(selector.toolsSelected()){
            for (Rect rect :grid.getSelectedGridSquares()) {
                deleteObject(rect);
            }
            grid.invalidateHitboxes();
            selector.setToolsSelected(false);
        }
        else if(grid.getTouchedTool() != null){
            deleteObject(grid.getTouchedTool());
        }
        else{
            return;
        }
    }

    /*
        Various gestures are handled with these methods
        Animations are also handled here

     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        touchedCell = new Rect(0,0,0,0);
        touchedPoint = new Position();

        if(pan){
            grid.invalidateHitboxes();
        }
        else if(stateManager.getDrawingState()){
            touchedPoint = translatesToGridCords(event.getRawX() ,event.getRawY() );
            touchedCell = grid.checkGrid(touchedPoint.x ,touchedPoint.y);
            touchedPoint.y -= 10;
        }
        else {
            touchedPoint = translatesToGridCords(event.getX(),event.getY());
            touchedCell = grid.checkGrid(touchedPoint.x ,touchedPoint.y);
        }

        if(pan){
            final int action = event.getAction();

            switch (action) {

                case MotionEvent.ACTION_DOWN: {

                    //get x and y cords of where we touch the screen
                    final float x = event.getX();
                    final float y = event.getY();

                    //remember where touch event started
                    mLasttouch.x = x;
                    mLasttouch.y = y;

                    break;
                }
                case MotionEvent.ACTION_MOVE: {

                    final float x = event.getX();
                    final float y = event.getY();

                    //calculate the distance in x and y directions
                    final float distanceX = x - mLasttouch.x;
                    final float distanceY = y - mLasttouch.y;

                    mPosition.x += distanceX;
                    mPosition.y += distanceY;

                    System.out.println("x: " + mPosition.x + " Y: " + mPosition.y );

                    //remember this touch position for next move event
                    mLasttouch.x = x;
                    mLasttouch.y = y;

                    mPosition = checkBounds(mPosition , myBitmap);


                    //redraw canvas call onDraw method
                    invalidate();


                    break;
                }
            }

            return true;
        }


        if(stateManager.getWireCutting()){
            grid.invalidateSelectedTools();

            return wireCutterAction(event, touchedCell);
        }


        //When all states are turned off, passiveTouchAction will be executed.
        if(stateManager.getPassiveTouchState()){
            grid.invalidateTouchedCell();


            if(event.getAction() == MotionEvent.ACTION_DOWN ){
                grid.invalidateHitboxes();
                if(currentGame.getTool(touchedCell) == null){
                    selector.enableSelectorDraw();
                }
                else{
                    grid.setTouchedTool(touchedCell);
                }
            }


            if( selector.getDrawState()  ){
                selector.createSelectorTool(touchedPoint,event,grid,currentGame);
                return true;
            }

            else{
                return passiveTouchAction(touchedPoint,event, touchedCell);
            }
        }

        if(stateManager.getDeleteState() && currentGame.getTool(touchedCell) != null) {

            stateManager.disableDeleteState();
            deleteObject(touchedCell);
        }

        if(stateManager.getMoveState()){
            if( currentGame.getTool(touchedCell) != null){
                currentGame.setUpMove(touchedCell);
                stateManager.enableToolBufferSavedState();
                stateManager.enableDrawingState();
            }

            stateManager.disableMoveState();
            grid.invalidateTouchedCell();
        }

        if (stateManager.getDrawingState()) {

            drawDraggingObject(touchedPoint, event , touchedCell);
            return true;
        }

        return false;
    }

    private boolean passiveTouchAction(Position touchedPoint,MotionEvent event, Rect touchedCell){

        if(stateManager.getWiringState()){
            drawDraggingWire(touchedPoint,event,touchedCell);
            return true;

        }

        motionDetector.onTouchEvent(event);

        if((stateManager.getSingleTapState() || stateManager.getDoubleTapState()) && currentGame.getTool(touchedCell) instanceof Switch){

            switchSound.start();

            currentGame.toggleSwitch(touchedCell);
            stateManager.disableDoubleTapState();
            stateManager.disableSingleTapState();
            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_MOVE && touchOutsideToolBounds(touchedPoint, grid.getTouchedTool()) ){

            boolean readyToWire = wireConnectionManager.prepGateConnection(currentGame.getTool(grid.getTouchedTool()));

            if(readyToWire){
                stateManager.enableWiringState();
            }
            else{
                Toast.makeText(context,"Wiring Failed",Toast.LENGTH_SHORT).show();

                grid.invalidateTouchedTool();
                stateManager.disableLongPressState();
                return true;
            }
        }
        return true;
    }



    private boolean touchOutsideToolBounds(Position touchedPoint, Rect touchedCell){
        boolean insideBounds;
        try{
            insideBounds = touchedCell.contains( (int) touchedPoint.x, (int) touchedPoint.y );
        }
        catch (NullPointerException outputOnlyTool){
            insideBounds = true;
        }

        if( insideBounds ){
            return false;
        }
        else{
            return true;
        }
    }

    private void drawDraggingWire(Position touchedPoint,MotionEvent event, Rect touchedCell){

        switch(event.getAction()) {

            case MotionEvent.ACTION_MOVE:

                wireConnectionManager.getTempWire().setWireEndX(touchedPoint.x);
                wireConnectionManager.getTempWire().setWireEndY(touchedPoint.y);
                 break;

            case MotionEvent.ACTION_UP:

                Boolean connectionSuccessful = wireConnectionManager.connectGates(currentGame.getTool(touchedCell), this , backgroundCanvas);

                if(connectionSuccessful){
                }
                else{
                    Toast.makeText(context,"connection unsuccessful",Toast.LENGTH_SHORT).show();
                }

                grid.invalidateTouchedTool();
                stateManager.disableWiringState();
                stateManager.disableLongPressState();
               break;
        }

    }

    private void drawDraggingObject(Position touchedPoint ,MotionEvent event, Rect touchedCell){

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN | MotionEvent.ACTION_MOVE :
                currentGame.setToolBufferPos(touchedPoint.x, touchedPoint.y);
                break;

            case MotionEvent.ACTION_UP:
                if(stateManager.getToolBufferSavedState()) {
                    if(uniqueGridSquareSelected(touchedCell)){
                        currentGame.setToolBufferPos(touchedCell.centerX(), touchedCell.centerY());
                    }
                    else{
                        currentGame.reloadToolBufferPos();
                    }
                    stateManager.disableToolBufferSavedState();
                }
                else if (uniqueGridSquareSelected(touchedCell)){

                    try{
                        currentGame.setToolBufferPos(touchedCell.centerX(), touchedCell.centerY());
                    }
                    catch (NullPointerException e){
                        currentGame.resetToolBuffer();
                        stateManager.disableDrawingState();
                        grid.invalidateTouchedCell();
                        break;
                    }

                    currentGame.addToolBuffer();
                    stateManager.disableToolBufferSavedState();
                }
                else if(stateManager.getToolBufferSavedState()) {
                    currentGame.reloadToolBufferPos();
                    currentGame.addToolBuffer();
                    stateManager.disableToolBufferSavedState();
                }
                else {
                    Toast.makeText(context,"Cannot add a gates on top of each other",Toast.LENGTH_SHORT).show();
                }
                currentGame.resetToolBuffer();
                stateManager.disableDrawingState();
                grid.invalidateTouchedCell();
                break;
        }
    }

    private boolean wireCutterAction(MotionEvent event , Rect touchedCell){

        if((currentGame.getTool(touchedCell) == null) || currentGame.getTool(touchedCell) instanceof Switch){
            grid.invalidateTouchedCell();
            return false;
        }

        motionDetector.onTouchEvent(event);

        if(stateManager.getSingleTapState() || stateManager.getDoubleTapState()) {

            currentGame.getTool(touchedCell).deleteWire();

            stateManager.disableDoubleTapState();
            stateManager.disableSingleTapState();

        }



        return true;
    }

    private Position checkBounds(Position mPosition , Bitmap bitmap){
        if(mPosition.x > 75 ){
            mPosition.x = 75;
        }

        if(mPosition.x < - 20  -( bitmap.getWidth() - this.getWidth() ) ){
            mPosition.x = - 20 -( bitmap.getWidth() - this.getWidth()  );
        }
        if(mPosition.y > 4 ){
            mPosition.y = 4;
        }
        if(mPosition.y < - 20  -( bitmap.getHeight() - this.getHeight() ) ){
            mPosition.y = - 20 -( bitmap.getHeight() - this.getHeight()  );
        }

        return mPosition;

    }


    //calculation to translate given values ( event x, event y) to grid coordinates/bitmap coordinates
    public Position translatesToGridCords(float x, float y){

        Position bitmapCoordinates = new Position();

        bitmapCoordinates.x = x - mPosition.x;
        bitmapCoordinates.y = y - mPosition.y;


        return bitmapCoordinates;
    }

    public void deleteObject(Rect touchedCell) {
        currentGame.deleteTool(touchedCell);
        grid.invalidateTouchedCell();
        grid.invalidateTouchedTool();
    }

    private boolean uniqueGridSquareSelected(Rect touchedCell) {
        return currentGame.getTool(touchedCell) == null;
    }

    public SandboxGame getCurrentGame(){
        return currentGame;
    }
}
