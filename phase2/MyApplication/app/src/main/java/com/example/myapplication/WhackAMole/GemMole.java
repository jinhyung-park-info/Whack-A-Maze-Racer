package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class GemMole extends Mole {

    GemMole(Hole hole){
        super(hole);
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.molePic3;
        this.value = GameConstants.moleValue;
        this.gemValue = GameConstants.gemMoleValue;
        this.lifeCount = 1;
    }
}
