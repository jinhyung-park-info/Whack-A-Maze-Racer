package com.example.myapplication.WhackAMole;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

/** Obtained but significantly modified from source 2) */

class Mole {

  public enum Movement {
    UP,
    DOWN,
    STANDBY,
    HIT
  }

  static float speed = WamView.screenHeight / 300;
  private Bitmap molePic;
  private float y, x, molePicWidth, molePicHeight;
  private Hole hole;
  private Movement state;
  private int standByDuration;
  private float picLeft, width, picTop, height;
  boolean loseLife = false;

  Mole(Hole hole, Bitmap molePic) {

    this.molePic = molePic;
    this.hole = hole;

    molePicWidth = this.molePic.getWidth();
    molePicHeight = this.molePic.getHeight();

    x = hole.getX() + hole.getHoleWidth() / 2 - molePicWidth / 2;
    y = hole.getY() + hole.getHoleHeight() / 2;

    picLeft = x;
    picTop = hole.getY() + hole.getHoleHeight() / 2 - (molePicHeight / 2);
    width = x + molePicWidth;
    height = hole.getY() + hole.getHoleHeight() / 2;

    state = Movement.STANDBY;
    standByDuration = 0;
  }

  void draw(Canvas canvas, Paint paint) {
    canvas.save();
    canvas.clipRect(picLeft, picTop, width, height);
    canvas.drawBitmap(this.molePic, x, y, paint);
    canvas.restore();
  }

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
    picLeft = x;
    picTop = hole.getY() + hole.getHoleHeight() / 2 - (molePicHeight * 2 / 3);
    width = x + molePicWidth;
    height = hole.getY() + hole.getHoleHeight() / 2;
  }

  void setState(Movement state) {
    this.state = state;
  }

  Movement getState() {
    return state;
  }

  void reset() {
    y = height;
    state = Movement.STANDBY;
    speed = WamView.screenHeight / 300;
  }

  Rect getTouchRect() {
    return new Rect((int) x, (int) y, (int) width, (int) height);
  }

}
