package com.diplabs.morseapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Audio;
import com.otaliastudios.cameraview.controls.Engine;

public class MainActivity extends AppCompatActivity implements SettingsFragment.OnDataPassSettings, TextFragment.OnDataPassTextTranslated{
    private static final String TAG = "morse";
    private CameraView cameraView;
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




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new TextFragment())

                .commit();



    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            item -> {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
//                    case R.id.nav_home:
//                    selectedFragment = new HomeFragment();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                                selectedFragment).commit();
//                    break;
                    case R.id.nav_text:
                    selectedFragment = new TextFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                                selectedFragment).commit();
                    break;

                    case R.id.nav_settings:

                        Bundle bundle = new Bundle();
                        bundle.putInt("timeUnit", timeUnit);

                   selectedFragment = new SettingsFragment();
                        selectedFragment.setArguments(bundle);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, selectedFragment)
                                .commit();
                     break;
                }

            return true;
            };

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




    public void toggle(String inputText) {

        if (!isRunning) {
            isRunning = true;
            thread = new Thread(() -> {
                customMorseEventHandler = new CustomMorseEventHandler(timeUnit, cameraView);
                customMorseEventHandler.translate(inputText);
                if (isRunning) runOnUiThread(() -> toggle(" "));
            });
            thread.start();




        } else {
            customMorseEventHandler.running = false;
            thread.interrupt();
            isRunning = false;

        }
    }

    public void letKnowFragmentsThatFinished(){

    }


//    @Override
//    public void onDataPassHome(String translatedData) {
//        toggle(translatedData);
//    }

    @Override
    public void onDataPassSettings(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Override
    public void onDataPassText(String translatedData) {
        toggle(translatedData);
    }

    //added in home
//    @Override
//    public void onDataAccept(String translatedData) {
//        dataFromActivityToFragment.sendData(translatedData);
//    }
    DataFromActivityToFragment dataFromActivityToFragment;
    public interface DataFromActivityToFragment {
        void sendData(String data);
    }




}