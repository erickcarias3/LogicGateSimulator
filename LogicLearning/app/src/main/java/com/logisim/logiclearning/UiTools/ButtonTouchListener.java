package com.logisim.logiclearning.UiTools;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import com.logisim.logiclearning.R;
import com.logisim.logiclearning.SandboxView;
import com.logisim.logiclearning.SandboxViewTools.CustomScrollView;

public class ButtonTouchListener implements View.OnTouchListener {
    CustomScrollView scrollView;
    SandboxView locatedView;
    Resources resources;

    public ButtonTouchListener(CustomScrollView scrollView , SandboxView locatedView , Resources resources){
        this.scrollView = scrollView;
        this.locatedView = locatedView;
        this.resources = resources;
   }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        buttonHandleOnTouch(v,event);
        switch(v.getId()){

            case R.id.addSWITCH:
                locatedView.testButtonLogic(resources,R.drawable.offswitch, event);
                return true;
            case R.id.addAND:
                locatedView.testButtonLogic(resources,R.drawable.andgate, event);
                return true;
            case R.id.addOR:
                locatedView.testButtonLogic(resources,R.drawable.orgate, event);
                return true;
            case R.id.addNOT:
                locatedView.testButtonLogic(resources,R.drawable.notgate, event);
                return true;
            case R.id.addXOR:
                locatedView.testButtonLogic(resources,R.drawable.xorgate, event);
                return true;
            case R.id.addXNOR:
                locatedView.testButtonLogic(resources,R.drawable.xnorgate, event);
                return true;
            case R.id.addNOR:
                locatedView.testButtonLogic(resources,R.drawable.norgate, event);
                return true;
            case R.id.addNAND:
                locatedView.testButtonLogic(resources,R.drawable.nandgate, event);
                return true;
            case R.id.addLIGHT:
                locatedView.testButtonLogic(resources,R.drawable.lightbulboff, event);
                return true;
            default:
                return false;
        }
    }

    public void buttonHandleOnTouch(View view, MotionEvent motionEvent){
        scrollView.smoothScrollBy(0,0);
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                view.getBackground().setColorFilter(0xe0f47521, PorterDuff.Mode.SRC_ATOP);
                scrollView.setEnableScrolling(false);
                view.invalidate();
                break;
            }
            case MotionEvent.ACTION_UP: {
                scrollView.setEnableScrolling(true);

                view.getBackground().clearColorFilter();
                view.invalidate();
                break;
            }
        }
    }
}
