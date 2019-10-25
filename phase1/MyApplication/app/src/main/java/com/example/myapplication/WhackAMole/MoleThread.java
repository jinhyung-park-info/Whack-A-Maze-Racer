package com.example.myapplication.WhackAMole;


import android.content.Intent;

import static androidx.core.content.ContextCompat.createDeviceProtectedStorageContext;
import static androidx.core.content.ContextCompat.startActivity;

public class MoleThread extends Thread {

    public boolean keepRunning = true;
    private WamView wamView;
    private WamCollection wamCollection;
    private int duration = 2400;


    MoleThread(WamView main_view, WamCollection wamCollection) {
        this.wamView = main_view;
        this.wamCollection = wamCollection;
    }

    void setDuration(int duration) {
        this.duration = duration;
    }

    public void run() {
        while (keepRunning) {
            if (wamView.gameStatus.equals("inGame")) {
                long start_time, end_time;
                start_time = System.currentTimeMillis();
                wamCollection.randomMole();
                if (wamCollection.score >= 10) {
                    duration = 600;
                    Mole.speed = WamView.screenHeight / 200;
                } else if (wamCollection.score >= 5) {
                    duration = 1500;
                    Mole.speed = WamView.screenHeight / 240;
                }
                end_time = System.currentTimeMillis();
                if (end_time - start_time < duration) {
                    try {
                        Thread.sleep(duration - (end_time - start_time));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(wamCollection.score >= 15){
                    keepRunning = false;
                    this.wamView.gameStatus = "end";
                }
            }
        }
    }
}
