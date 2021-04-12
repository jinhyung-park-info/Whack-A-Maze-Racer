package com.example.myapplication.WhackAMole;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Creature that comes out of holes that are to be hit.
 */

abstract class Mole {

    public enum Movement {
        UP,
        DOWN,
        STANDBY,
        HIT
    }

    private static float speed = WamView.screenHeight / 300;
    Bitmap molePic;
    private float y, x;
    private Movement state;
    private int standByDuration;
    private float picLeft, width, picTop, height;
    boolean loseLife = false;
    int value;
    int gemValue;
    int lifeCount;

    Mole(Hole hole) {
        setMoleProperties();

        float molePicWidth = this.molePic.getWidth();
        float molePicHeight = this.molePic.getHeight();

        x = hole.getX() + hole.getHoleWidth() / 2 - molePicWidth / 2;
        y = hole.getY() + hole.getHoleHeight() / 2;

        picLeft = x;
        picTop = hole.getY() + hole.getHoleHeight() / 2 - (molePicHeight / 2);
        width = x + molePicWidth;
        height = hole.getY() + hole.getHoleHeight() / 2;

        state = Movement.STANDBY;
        standByDuration = 0;

        picLeft = x;
        picTop = hole.getY() + hole.getHoleHeight() / 2 - (molePicHeight * 2 / 3);
        width = x + molePicWidth;
        height = hole.getY() + hole.getHoleHeight() / 2;
    }

    void draw(Canvas canvas, Paint paint) {
        canvas.save();
        canvas.clipRect(picLeft, picTop, width, height);
        canvas.drawBitmap(this.molePic, x, y, paint);
        canvas.restore();
    }

    abstract void setMoleProperties();

    void move() {
        if (state == Movement.UP) {
            if (y - speed >= picTop) {
                y -= speed;
            } else {
                y = picTop;
                state = Movement.DOWN;
            }
        } else if (state == Movement.DOWN) {
            if (y + speed <= height) {
                y += speed;
            } else {
                y = height;
                state = Movement.STANDBY;
                this.loseLife = true;
            }
        } else if (state == Movement.HIT) {
            if (standByDuration <= 10) {
                standByDuration++;
            } else {
                standByDuration = 0;
                y = height;
                state = Movement.STANDBY;
            }
        }
    }

    void setState(Movement state) {
        this.state = state;
    }

    Movement getState() {
        return state;
    }

    Rect getTouchRect() {
        return new Rect((int) x, (int) y, (int) width, (int) height);
    }

    static void setSpeed(int newSpeed) {
        speed = newSpeed;
    }
}
