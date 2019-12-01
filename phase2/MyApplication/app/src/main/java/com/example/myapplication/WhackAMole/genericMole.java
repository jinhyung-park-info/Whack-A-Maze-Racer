package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class genericMole extends Mole{

    genericMole(Hole hole){
        super(hole);
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.genericMole;
        this.value = GameConstants.moleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 1;
    }
}
