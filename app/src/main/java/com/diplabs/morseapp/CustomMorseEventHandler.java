package com.diplabs.morseapp;


import com.diplabs.morsecode.MorseTranslator;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.controls.Flash;

public class CustomMorseEventHandler implements MorseTranslator {

    private int timeUnit;
    private  CameraView cameraView;
    public boolean running = true;


    public CustomMorseEventHandler( int timeUnit, CameraView cameraView) {

        this.timeUnit = timeUnit;
        this.cameraView = cameraView;
    }

    @Override
    public void startOfTranslation() {

    }

    @Override
    public void doForSingleDot() {
        if (running) {


            toggleTorch();
            try {
                Thread.sleep(timeUnit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toggleTorch();
        }
    }

    @Override
    public void doForSingleDash() {
        if (running) {

            toggleTorch();
            try {
                Thread.sleep(timeUnit * 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            toggleTorch();
        }
    }

    @Override
    public void doForSingleBreak() {
        if (running) {

            try {
                Thread.sleep(timeUnit);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void endOfTranslation() {

        // should not be needed. just in case.
        if (cameraView.getFlash().equals(Flash.TORCH)) {
            cameraView.setFlash(Flash.OFF);

        }
    }

    public void toggleTorch() {
        if (cameraView.getFlash().equals(Flash.TORCH)) {
            cameraView.setFlash(Flash.OFF);
        } else {
            cameraView.setFlash(Flash.TORCH);
        }

    }

}
