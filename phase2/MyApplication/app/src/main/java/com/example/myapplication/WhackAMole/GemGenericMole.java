package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class GemGenericMole extends GenericMole {

    GemGenericMole(Hole hole){
        super(hole);
        setMoleProperties();
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.molePic3;
        this.value = GameConstants.moleValue;
        this.gemValue = GameConstants.gemMoleValue;
        this.lifeCount = 1;
    }
}
