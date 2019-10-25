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
    private int lifePicWidth, lifePicHeight;
    private int numHoles;
    private int holesX, holesY;

    private int holeWidth, holeHeight;

    public int f_w, f_h, h_x, h_y;
    private Rect hole_r;

    public int score;


    public WamCollection(Bitmap holePic, Rect hole_r, Bitmap molePic, Bitmap lifePic, int numLives, int holesX, int holesY, WamView wamView) {
        this.holeList = new ArrayList<>();
        this.moleList = new ArrayList<>();

        this.hole_r = hole_r;
        this.holePic = holePic;
        holeWidth = holePic.getWidth();
        holeHeight = holePic.getHeight();

        this.lifePic = lifePic;
        this.lifePicWidth = this.lifePic.getWidth();
        this.lifePicHeight = this.lifePic.getHeight();
        this.numLives = numLives;
        this.currentLives = this.numLives;

        this.molePic = molePic;

        this.holesX = holesX;
        this.holesY = holesY;
        this.numHoles = holesX * holesY;

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
        f_w = (hole_r.right - hole_r.left) / holesX;
        f_h = (hole_r.bottom - hole_r.top) / holesY;

        holeList = new ArrayList<>();

        //Add numHoles number of holes into hole collection.
        for (int i = 0; i < this.numHoles; i++) {
            h_x = (i % holesX) * f_w + f_w / 2 - holeWidth / 2;
            h_y = hole_r.top + (i / holesX) * f_h + f_h / 2 - holeHeight / 2;
            holeList.add(new Hole(h_x, h_y, holePic, this));
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
