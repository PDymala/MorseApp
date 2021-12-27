package com.diplabs.morseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Audio;
import com.otaliastudios.cameraview.controls.Engine;
import com.otaliastudios.cameraview.controls.Flash;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "morse";
    private CameraView cameraView;
    private Button buttonOn;
    private EditText editTextInput;
    private Thread thread;
    private int timeUnit = 300;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initalizeCamera();
        initalizeUI();

    }


    private void initalizeUI() {
        buttonOn = findViewById(R.id.buttonOn);

        editTextInput = findViewById(R.id.editTextInput);

    }


    private void initalizeCamera() {
        cameraView = new CameraView(this);
        cameraView.setLifecycleOwner(this);
        cameraView.setEngine(Engine.CAMERA2);
        cameraView.setAudio(Audio.OFF);
        cameraView.setRequestPermissions(true);

    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        cameraView.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cameraView.destroy();
    }

    boolean isRunning = false;
    CustomMorseEventHandler customMorseEventHandler;


    public void toggle(View view) {
        toggle();
    }

    public void toggle() {

            if (!isRunning) {
                isRunning = true;
                thread = new Thread(() -> {
                    customMorseEventHandler = new CustomMorseEventHandler(timeUnit, cameraView);
                    customMorseEventHandler.translate(editTextInput.getText().toString());
                    if (isRunning) runOnUiThread(() -> toggle()); });
                thread.start();


                buttonOn.setText("OFF");
                editTextInput.setEnabled(false);

            } else {
                customMorseEventHandler.running = false;
                thread.interrupt();
                isRunning = false;

                buttonOn.setText("ON");
                editTextInput.setEnabled(true);

            }
        }







}