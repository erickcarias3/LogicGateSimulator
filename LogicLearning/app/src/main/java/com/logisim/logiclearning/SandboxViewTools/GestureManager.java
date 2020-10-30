package com.logisim.logiclearning.SandboxViewTools;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
/*
            :Welcome To the Gesture Manger:
        The Gesture Manger uses a View state manager
        to enable and disable states.
*/

public class GestureManager extends GestureDetector.SimpleOnGestureListener {

    private StateManager viewStateManager;

    public GestureManager (StateManager stateManager){
        viewStateManager = stateManager;
    }


    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        Log.i("TAG", "onSingleTapConfirmed: ");
        viewStateManager.enableSingleTapState();
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        viewStateManager.setLongPress(true);
        Log.i("TAG", "onLongPress: ");
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        viewStateManager.enableDoubleTapState();
        Log.i("TAG", "onDoubleTap: ");
        return true;
    }


}
