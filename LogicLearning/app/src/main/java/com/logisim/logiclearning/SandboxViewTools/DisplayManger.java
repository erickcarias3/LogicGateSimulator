package com.logisim.logiclearning.SandboxViewTools;

import android.view.Display;
import android.graphics.Point;
import android.view.WindowManager;

/*
            :Welcome To the Display Manager:
       This class is to store information on the current display
       information from this class is used in the view to draw and positions items
       to the correct scale of the device

*/
public class DisplayManger {

    private int numberHorizontalPixels;
    private int numberVerticalPixels;

    public DisplayManger(WindowManager window) {
        Display display = window.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        numberHorizontalPixels = size.x;
        numberVerticalPixels = size.y;
    }

    public int getScreenWidth() { return numberHorizontalPixels; }
    public int getScreenHeight() { return numberVerticalPixels; }
}
