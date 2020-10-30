package com.logisim.logiclearning.Activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.ViewUtils;

import com.logisim.logiclearning.SandboxViewTools.CustomScrollView;
import com.logisim.logiclearning.SandboxViewTools.DisplayManger;
import com.logisim.logiclearning.SandboxView;
import com.logisim.logiclearning.R;
import com.logisim.logiclearning.UiTools.ButtonTouchListener;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/*
            :Welcome To the Sandbox Driver:

    The SandboxDriver is the main activity for running the SandboxMode.
    All The buttons for the Sandbox Mode are instantiated and hooked up
    to the Sandbox View where the view information is displayed
    and manipulated.

*/
public class SandboxDriver extends Activity {

    SandboxView mainView;
    DisplayManger display;
    ArrayList<String> saveNames;
    String selectedSave;
    ToggleButton wireCutter, panToggle;
    Button saveButton, loadButton;
    Button addAND,addOR,addNOT,addXOR,addXNOR,addNOR,addNAND,addSWITCH,addLIGHT;
    Resources toolImages;
    CustomScrollView scrollView;
    ButtonTouchListener customTouchListener;

    int i=2;



    /*
        On creation of the activity SandBox,
        all buttons necessary will be created and
        a new Sandbox view is created with
        the screen information from the current device
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        display = new DisplayManger(getWindowManager());
        setContentView(R.layout.activity_main);
        toolImages = getResources();
        mainView = findViewById(R.id.mainView);
        saveNames = new ArrayList<>();
        saveNames.addAll(Arrays.asList(fileList()));

        mainView.setCurrentViewWithDisplayInfo(display);
        scrollView = findViewById(R.id.scrollView1);
        wireCutter = findViewById(R.id.wireCutter);
        panToggle = findViewById(R.id.panScreen);
        saveButton = findViewById(R.id.Save);
        loadButton = findViewById(R.id.loadButton);

        customTouchListener = new ButtonTouchListener(scrollView,mainView,getResources());


        addSWITCH = findViewById(R.id.addSWITCH);
        addLIGHT = findViewById(R.id.addLIGHT);


        addAND = findViewById(R.id.addAND);
        addOR = findViewById(R.id.addOR);
        addNOT = findViewById(R.id.addNOT);
        addXOR = findViewById(R.id.addXOR);
        addXNOR = findViewById(R.id.addXNOR);
        addNOR = findViewById(R.id.addNOR);
        addNAND = findViewById(R.id.addNAND);



        addSWITCH.setOnTouchListener(customTouchListener);
        addLIGHT.setOnTouchListener(customTouchListener);

        addAND.setOnTouchListener(customTouchListener);
        addOR.setOnTouchListener(customTouchListener);
        addNOT.setOnTouchListener(customTouchListener);
        addXOR.setOnTouchListener(customTouchListener);
        addXNOR.setOnTouchListener(customTouchListener);
        addNOR.setOnTouchListener(customTouchListener);
        addNAND.setOnTouchListener(customTouchListener);




        panToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    wireCutter.setChecked(false);
                    mainView.pan =true;
                } else {
                    mainView.pan=false;
                }
            }
        });


        wireCutter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    panToggle.setChecked(false);
                    mainView.WireCuttingToggleLogic();
                } else {
                    mainView.disableWireCutting();
                }
            }
        });

        //setter
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableToggleButtons();

                AlertDialog.Builder builder =
                        new AlertDialog.Builder(SandboxDriver.this, R.style.CustomDialogTheme);
                builder.setCancelable(false);
                builder.setTitle("Enter your desired Save name");
                // Set up the input
                final EditText input = new EditText(SandboxDriver.this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(input);
                // Set up the buttons
                builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            String entered_name = input.getText().toString();
                            if(entered_name.trim().equals("")){
                                Toast.makeText(getApplicationContext(), "Please enter a non empty Save name", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                if(saveNames.contains(entered_name)){
                                    entered_name += " " +i;
                                    i++;
                                }
                                saveNames.add(entered_name);
                                FileOutputStream file = openFileOutput(entered_name, Context.MODE_PRIVATE);
                                ObjectOutputStream out = new ObjectOutputStream(file);
                                out.writeObject(mainView.getCurrentGame().getAllUsedTools());
                                file.close();
                                out.close();
                            }
                        }catch (IOException i){
                            Log.d("Serialize: ", "Objects not serialized");
                        }
                    }
                });
                builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disableToggleButtons();
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(SandboxDriver.this,R.style.CustomDialogTheme);
                builderSingle.setCancelable(false);
                builderSingle.setTitle("Select a Save to load");

                builderSingle.setSingleChoiceItems(saveNames.toArray(new String[0]), -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        selectedSave = saveNames.get(which);
                    }
                });

                builderSingle.setPositiveButton("Load", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try{
                            if(selectedSave != null) {
                                FileInputStream file = openFileInput(selectedSave);
                                ObjectInputStream in = new ObjectInputStream(file);
                                mainView.getCurrentGame().setUsedTools((HashSet) in.readObject());

                                in.close();
                                file.close();
                                selectedSave = "";
                            }
                        }catch (IOException i){
                            Log.d("Deserialize: ", "Objects not deserialized");
                        }
                        catch (ClassNotFoundException i){
                            Log.d("Deserialize: ", "Objects not deserialized");
                        }
                    }
                });

                builderSingle.setNeutralButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteFile(selectedSave);
                        saveNames.remove(selectedSave);
                        selectedSave = "";
                    }
                });

                builderSingle.setNegativeButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        selectedSave = "";
                    }
                });

                builderSingle.show();
            }
        });


    }


    public void showAddToolMenu(View view){
        this.disableToggleButtons();

        findViewById(R.id.addToolButton).setVisibility(View.GONE);
        findViewById(R.id.deletebutton).setVisibility(View.GONE);
        findViewById(R.id.movebutton).setVisibility(View.GONE);
        findViewById(R.id.panScreen).setVisibility(View.GONE);
        findViewById(R.id.wireCutter).setVisibility(View.GONE);
        saveButton.setVisibility(View.GONE);
        loadButton.setVisibility(View.GONE);
        findViewById(R.id.clear).setVisibility(View.GONE);



        findViewById(R.id.editMenu).setVisibility(View.VISIBLE);
        addAND.setVisibility(View.VISIBLE);
        addOR.setVisibility(View.VISIBLE);
        addNOT.setVisibility(View.VISIBLE);
        addXOR.setVisibility(View.VISIBLE);
        addXNOR.setVisibility(View.VISIBLE);
        addNOR.setVisibility(View.VISIBLE);
        addNAND.setVisibility(View.VISIBLE);
        addSWITCH.setVisibility(View.VISIBLE);
        addLIGHT.setVisibility(View.VISIBLE);

    }

    public void showEditMenu(View view){
        findViewById(R.id.addToolButton).setVisibility(View.VISIBLE);
        findViewById(R.id.deletebutton).setVisibility(View.VISIBLE);
        findViewById(R.id.movebutton).setVisibility(View.VISIBLE);
        findViewById(R.id.panScreen).setVisibility(View.VISIBLE);
        findViewById(R.id.wireCutter).setVisibility(View.VISIBLE);
        findViewById(R.id.clear).setVisibility(View.VISIBLE);
        saveButton.setVisibility(View.VISIBLE);
        loadButton.setVisibility(View.VISIBLE);


        findViewById(R.id.editMenu).setVisibility(View.GONE);
        addAND.setVisibility(View.GONE);
        addOR.setVisibility(View.GONE);
        addNOT.setVisibility(View.GONE);
        addXOR.setVisibility(View.GONE);
        addXNOR.setVisibility(View.GONE);
        addNOR.setVisibility(View.GONE);
        addNAND.setVisibility(View.GONE);
        addSWITCH.setVisibility(View.GONE);
        addLIGHT.setVisibility(View.GONE);

    }

    public void move(View view){
        this.disableToggleButtons();
        Toast.makeText(getApplicationContext(), "Touch and drag and item to move.", Toast.LENGTH_SHORT).show();
        mainView.moveToolButtonLogic();
    }

    public void delete(View view){
        disableToggleButtons();
        mainView.deleteToolButtonLogic();
    }

    public void clear(View view){
        disableToggleButtons();
        mainView.clearBoard();
    }

    public HashSet getTools() {
        return mainView.getCurrentGame().getAllUsedTools();
    }

    public void disableToggleButtons() {
        if (panToggle.isChecked() || wireCutter.isChecked()) {
           wireCutter.setChecked(false);
           panToggle.setChecked(false);
        }
    }

}