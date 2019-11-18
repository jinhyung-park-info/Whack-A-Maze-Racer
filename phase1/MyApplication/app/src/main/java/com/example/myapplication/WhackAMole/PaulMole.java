package com.example.myapplication.WhackAMole;

import android.graphics.Bitmap;

public class PaulMole extends Mole{

    PaulMole(Hole hole, Bitmap molePic){
        super(hole, molePic);
        this.value = -1;
        this.lifeCount = 0;
    }
}
