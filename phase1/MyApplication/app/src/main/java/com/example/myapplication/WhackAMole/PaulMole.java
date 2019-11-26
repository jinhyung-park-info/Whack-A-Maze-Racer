package com.example.myapplication.WhackAMole;

public class PaulMole extends Mole{

    PaulMole(Hole hole){
        super(hole);
        this.molePic = WamView.molePic2;
        this.value = -1;
        this.lifeCount = 0;
    }
}
