package com.example.myapplication.WhackAMole;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;


public class Mole {

    public enum Movement {
        UP, DOWN, STILL, HIT
    }

    public static float speed = WamView.screenHeight / 300;
    private Bitmap molePic;

    private float y, x, molePicWidth, molePicHeight;
    private Hole hole;
    private Movement state;
    private int standByDuration;

    private float picLeft, picRight, picTop, picBottom;

    private WamCollection wamCollection;

    public Mole(Hole hole, Bitmap molePic, WamCollection wamCollection) {

        this.molePic = molePic;
        this.hole = hole;
        this.wamCollection = wamCollection;

        molePicWidth = this.molePic.getWidth();
        molePicHeight = this.molePic.getHeight();

        x = hole.getX() + hole.getHoleWidth() / 2 - molePicWidth / 2;
        y = hole.getY() + hole.getHoleHeight() / 2;

        picLeft = x;
        picTop = hole.getY() + hole.getHoleHeight() / 2 - (molePicHeight / 2);
        picRight = x + molePicWidth;
        picBottom = hole.getY() + hole.getHoleHeight() / 2;

        state = Movement.STILL;
        standByDuration = 0;
    }

    public void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(picLeft, picTop, picRight, picBottom);
        canvas.drawBitmap(this.molePic, x, y, paint);
        canvas.restore();
    }

    public void setMovement() {
        if (state == Movement.UP) {
            if (y - speed >= picTop) {
                y -= speed;
            } else {
                y = picTop;
                state = Movement.DOWN;
            }
        } else if (state == Movement.DOWN) {
            if (y + speed <= picBottom) {
                y += speed;
            } else {
                y = picBottom;
                state = Movement.STILL;
                loseLife();
            }
        } else if (state == Movement.HIT) {
            if (standByDuration <= 10) {
                standByDuration++;
            } else {
                standByDuration = 0;
                y = picBottom;
                state = Movement.STILL;
            }
        }
        picLeft = x;
        picTop = hole.getY() + hole.getHoleHeight() / 2 - (molePicHeight * 2 / 3);
        picRight = x + molePicWidth;
        picBottom = hole.getY() + hole.getHoleHeight() / 2;
    }

    public void setState(Movement state) {
        this.state = state;
    }

    public Movement getState() {
        return state;
    }

    public void reset() {
        y = picBottom;
        state = Movement.STILL;
        speed = WamView.screenHeight / 300;
        this.wamCollection.moleThread.setDuration(2400);
    }

    public Rect getTouchRect() {
        Rect rect = new Rect((int) x, (int) y, (int) picRight, (int) picBottom);
        return rect;
    }

    public void loseLife() {
        this.wamCollection.currentLives -= 1;
    }
}
