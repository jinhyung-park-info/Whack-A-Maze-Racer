package com.example.myapplication.WhackAMole;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Random;

public class WamCollection {

    private WamView wamView;
    MoleThread moleThread;
    private ArrayList<Hole> holeList;
    ArrayList<Mole> moleList;
    private Bitmap holePic;
    private Bitmap molePic;
    private Bitmap lifePic;
    private int numLives;
    int currentLives;
    private int lifePicWidth;
    private int numHoles;
    private int holesX, holesY;

    private int holeWidth, holeHeight;

    public int holeDeploymentWidth, holeDeploymentHeight, holeX, holeY;
    private Rect holeRect;

    public int score;


    public WamCollection(Bitmap holePic, Rect hole_r, Bitmap molePic, Bitmap lifePic, int numLives, int numHolesX, int numHolesY, WamView wamView) {
        this.holeList = new ArrayList<>();
        this.moleList = new ArrayList<>();

        this.holeRect = hole_r;
        this.holePic = holePic;
        holeWidth = holePic.getWidth();
        holeHeight = holePic.getHeight();

        this.lifePic = lifePic;
        this.lifePicWidth = this.lifePic.getWidth();
        this.numLives = numLives;
        this.currentLives = this.numLives;

        this.molePic = molePic;

        this.holesX = numHolesX;
        this.holesY = numHolesY;
        this.numHoles = numHolesX * numHolesY;

        this.wamView = wamView;
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
            canvas.drawBitmap(lifePic, x, y, paint);
            x += lifePicWidth;
        }

        paint.setTextSize(WamView.screenHeight / 24);
        paint.setColor(Color.WHITE);
        canvas.drawText("Score:" + this.score, WamView.screenWidth * 2 / 3, WamView.screenHeight / 18, paint);
    }

    void initialize() {
        this.currentLives = this.numLives;
        holeDeploymentWidth = (holeRect.right - holeRect.left) / holesX;
        holeDeploymentHeight = (holeRect.bottom - holeRect.top) / holesY;

        holeList = new ArrayList<>();

        //Add numHoles number of holes into hole collection.
        for (int i = 0; i < this.numHoles; i++) {
            holeX = (i % holesX) * holeDeploymentWidth + holeDeploymentWidth / 2 - holeWidth / 2;
            holeY = holeRect.top + (i / holesX) * holeDeploymentHeight + holeDeploymentHeight / 2 - holeHeight / 2;
            holeList.add(new Hole(holeX, holeY, holePic, this));
        }

        //Add one mole for each hole into mole collection.
        for (Hole hole : holeList) {
            moleList.add(new Mole(hole, molePic, this));
        }

        //Start Thread.
        this.moleThread = new MoleThread(this.wamView, this);
        moleThread.start();
    }

    void reinitialize() {
        this.score = 0;
        for (Mole mole : moleList) {
            mole.reset();
        }

        this.currentLives = this.numLives;
    }

    public void randomMole() {
        Random random = new Random();
        int num = random.nextInt(moleList.size());
        Mole mole = moleList.get(num);
        if (mole.getState() == Mole.Movement.STILL) {
            mole.setState(Mole.Movement.UP);
        }
    }

    void setMoleMovement(){
        for(Mole mole: this.moleList){
            mole.setMovement();
        }
    }
}
