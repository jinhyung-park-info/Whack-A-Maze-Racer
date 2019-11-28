package com.example.myapplication.WhackAMole;

public class LindseyMole extends Mole{

    LindseyMole(Hole hole){
        super(hole);
        this.molePic = WamView.molePic;
        this.value = 2;
        this.lifeCount = 1;
    }
}
