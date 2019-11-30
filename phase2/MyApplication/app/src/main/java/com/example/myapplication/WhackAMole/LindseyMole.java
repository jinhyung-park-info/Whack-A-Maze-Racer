package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class LindseyMole extends Mole{

    LindseyMole(Hole hole){
        super(hole);
        this.molePic = WamView.molePic;
        this.value = GameConstants.doubleMoleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 1;
    }
}
