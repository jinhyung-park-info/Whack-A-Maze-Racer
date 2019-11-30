package com.example.myapplication.WhackAMole;

class LindseyMole extends Mole{

    LindseyMole(Hole hole){
        super(hole);
        this.molePic = WamView.molePic;
        this.value = 2;
        this.gemValue = 0;
        this.lifeCount = 1;
    }
}
