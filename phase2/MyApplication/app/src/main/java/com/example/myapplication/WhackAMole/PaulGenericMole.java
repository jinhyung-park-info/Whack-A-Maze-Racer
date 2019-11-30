package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class PaulGenericMole extends GenericMole {

    PaulGenericMole(Hole hole){
        super(hole);
        setMoleProperties();
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.molePic2;
        this.value = GameConstants.negativeMoleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 0;
    }
}
