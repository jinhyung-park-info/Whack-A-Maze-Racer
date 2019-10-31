package com.example.myapplication.WhackAMole;

/**
 * This Thread controls speed and the time interval between moles. Obtained but significantly
 * modified from source 2)
 */
public class MoleThread extends Thread {

  private int duration = 2400;
  boolean keepRunning = true;

  private WamView wamView;
  private WamManager wamManager;

  MoleThread(WamView wamView, WamManager wamManager) {
    this.wamView = wamView;
    this.wamManager = wamManager;
  }

  void setDuration(int duration) {
    this.duration = duration;
  }

  public void run() {
    while (keepRunning) {
      if (wamView.gameStatus.equals("inGame")) {
        long start_time, end_time;
        start_time = System.currentTimeMillis();
        wamManager.randomMole();
        if (wamManager.score >= 10) {
          setDuration(600);
          Mole.speed = WamView.screenHeight / 200;
        } else if (wamManager.score >= 5) {
          setDuration(1500);
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
        if (wamManager.score >= 15) {
          keepRunning = false;
          this.wamView.gameStatus = "end";
        }
      }
    }
  }
}
