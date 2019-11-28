package com.example.myapplication.WhackAMole;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.myapplication.GameConstants;

import java.util.ArrayList;
import java.util.Random;

/** Inspired by FishTank Project */
class WamManager implements Runnable{

  private int numLives;
  int currentLives;
  private int lifePicWidth, lifePicHeight;
  private int numHoles;
  int holesX, holesY;
  private int holeWidth, holeHeight;
  private int holeDeploymentWidth, holeDeploymentHeight;
  private Rect holeRect;
  int score;
  private boolean keepRunning;
  private int duration;
  private MoleFactory moleFactory = new MoleFactory();

  private ArrayList<Hole> holeList;
  ArrayList<Mole> moleList;

  WamManager(
      Rect hole_r,
      int numLives,
      int numHolesX,
      int numHolesY,
      int score
  ) {
    this.holeList = new ArrayList<>();
    this.moleList = new ArrayList<>();

    this.holeRect = hole_r;
    holeWidth = WamView.holePic.getWidth();
    holeHeight = WamView.holePic.getHeight();

    this.lifePicWidth = WamView.lifePic.getWidth();
    this.lifePicHeight = WamView.lifePic.getHeight();
    this.numLives = numLives;
    this.currentLives = this.numLives;

    this.score = score;

    this.holesX = numHolesX;
    this.holesY = numHolesY;
    this.numHoles = numHolesX * numHolesY;

    this.holeDeploymentWidth = (holeRect.right - holeRect.left) / holesX;
    this.holeDeploymentHeight = (holeRect.bottom - holeRect.top) / holesY;

    this.duration = GameConstants.moleDefaultDuration;

  }

  void draw(Canvas canvas, Paint paint) {
    for (Hole hole : holeList) {
      hole.draw(canvas, paint);
    }

    for (int i = 0; i < moleList.size(); i++) {
      moleList.get(i).draw(canvas, paint);
    }

    int x = 0;
    int y = 0;
    for (int i = 0; i < this.currentLives; i++) {
      canvas.drawBitmap(WamView.lifePic, x, y, paint);
      if (x + lifePicWidth >= WamView.screenWidth * 5 / 8) {
        y += lifePicHeight;
        x = 0;
      } else {
        x += lifePicWidth;
      }
    }
  }

  // Idea of spreading out holes evenly on canvas obtained from source 2)
  void initialize() {
    this.currentLives = this.numLives;

    holeList = new ArrayList<>();

    // Add numHoles number of holes into hole collection.
    int holeX;
    int holeY;
    for (int i = 0; i < this.numHoles; i++) {
      holeX = (i % holesX) * holeDeploymentWidth + holeDeploymentWidth / 2 - holeWidth / 2;
      holeY =
          holeRect.top
              + (i / holesX) * holeDeploymentHeight
              + holeDeploymentHeight / 2
              - holeHeight / 2;
      holeList.add(new Hole(holeX, holeY, WamView.holePic));
    }

    // Add one mole for each hole into mole collection.
    for (Hole hole : holeList) {
      moleList.add(moleFactory.createMole("generic", hole));
      moleList.add(moleFactory.createMole("lindsey", hole));
      moleList.add(moleFactory.createMole("paul", hole));
    }
    keepRunning = true;
    Thread thread = new Thread(this);
    thread.start();
  }

  private void randomMole() {
    Random random = new Random();
    int num = random.nextInt(moleList.size());
    Mole mole = moleList.get(num);
    if (mole.getState() == Mole.Movement.STANDBY) {
      mole.setState(Mole.Movement.UP);
    }
  }

  void move() {
    for (Mole mole : this.moleList) {
      mole.move();
      if (mole.loseLife) {
        this.currentLives -= mole.lifeCount;
        mole.loseLife = false;
      }
    }
  }
  @Override
  public void run() {
    while (keepRunning) {
      long start_time, end_time;
      start_time = System.currentTimeMillis();
      randomMole();
      if (score >= GameConstants.moleSecondSpeedUp) {
        duration = GameConstants.moleSecondSpeed;
        Mole.setSpeed(WamView.screenHeight / 200);
      } else if (score >= GameConstants.moleFirstSpeedUp) {
        duration = GameConstants.moleFirstSpeed;
        Mole.setSpeed(WamView.screenHeight / 240);
      }
      end_time = System.currentTimeMillis();
      if (end_time - start_time < duration) {
        try {
          Thread.sleep(duration - (end_time - start_time));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
