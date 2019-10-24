package com.example.myapplication.WhackAMole;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

class Hole {
    private Bitmap hole_bmp;
    private float x, y, holeWidth, holeHeight;
    private WamCollection wamCollection;

    Hole(int x, int y, Bitmap hole_bmp, WamCollection wamCollection) {
        this.wamCollection = wamCollection;
        this.x = x;
        this.y = y;
        this.hole_bmp = hole_bmp;
        holeWidth = hole_bmp.getWidth();
        holeHeight = hole_bmp.getHeight();
    }

    void draw(Canvas canvas, Paint paint) {
        canvas.drawBitmap(hole_bmp, x, y, paint);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHoleWidth() {
        return holeWidth;
    }

    public float getHoleHeight() {
        return holeHeight;
    }


}
