package com.logisim.logiclearning.UiTools;

import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.logisim.logiclearning.R;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);

        TextView[] all = new TextView[5];

        all[0] = findViewById(R.id.CreditsHead);
        all[1] = findViewById(R.id.Erick);
        all[2] = findViewById(R.id.Akshay);
        all[3] = findViewById(R.id.Ivan);
        all[4] = findViewById(R.id.Moises);;

        Typeface typeBold = Typeface.createFromAsset(getAssets(),"fonts/Cuprum-Bold.ttf");
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/Cuprum-Regular.ttf");

        for (int i = 0; i < 5; i ++)
        {
            if (i == 0)
                all[i].setTypeface(typeBold);
            else
                all[i].setTypeface(type);

        }
    }
}
