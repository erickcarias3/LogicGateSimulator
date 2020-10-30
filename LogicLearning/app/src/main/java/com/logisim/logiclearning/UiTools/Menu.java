package com.logisim.logiclearning.UiTools;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.logisim.logiclearning.Activities.SandboxDriver;
import com.logisim.logiclearning.R;

public class Menu extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TextView Topic = findViewById(R.id.topic);
        Button OpenSim = findViewById(R.id.openSim);
        //Button OpenSand = findViewById(R.id.openSand);
        Button OpenManual = findViewById(R.id.openManual);
        Button OpenCredits = findViewById(R.id.openCredits);

        Typeface typeBold = Typeface.createFromAsset(getAssets(),"fonts/Cuprum-Bold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Cuprum-Regular.ttf");

        Topic.setTypeface(typeBold);
        OpenSim.setTypeface(type);
        //OpenSand.setTypeface(type);
        OpenManual.setTypeface(type);
        OpenCredits.setTypeface(type);

        OpenSim.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                open_sim();
            }
        });


        /*
        OpenSand.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                open_sand();
            }
        });*/


        OpenCredits.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                open_credits();
            }
        });
        OpenManual.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View V){
                open_manual();
            }
        });
    }

    void open_sim()
    {
        Intent intent = new Intent(this, SandboxDriver.class);
        startActivity(intent);
    }

    /*void open_sand()
    {
        Intent intent = new Intent(this, tutorial.class);
        startActivity(intent);
    }*/

    void open_credits()
    {
        Intent intent = new Intent(this, Credits.class);
        startActivity(intent);
    }

    void open_manual()
    {
        Intent intent = new Intent(this, Manual.class);
        startActivity(intent);
    }
}
