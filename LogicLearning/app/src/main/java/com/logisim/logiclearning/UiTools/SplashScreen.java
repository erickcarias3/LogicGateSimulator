package com.logisim.logiclearning.UiTools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.logisim.logiclearning.Activities.SandboxDriver;
import com.logisim.logiclearning.R;

//Learned Splash Screen from: https://www.youtube.com/watch?v=cyDhIovbOXc
public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                Intent i = new Intent(SplashScreen.this, Menu.class); startActivity(i);
                finish(); } }, 3100);
    }

}
