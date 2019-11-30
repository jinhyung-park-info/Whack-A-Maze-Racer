package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

public class GemMole extends Mole {

    GemMole(Hole hole){
        super(hole);
        this.molePic = WamView.molePic3;
        this.value = GameConstants.moleValue;
        this.gemValue = GameConstants.gemMoleValue;
        this.lifeCount = 1;
    }
}
