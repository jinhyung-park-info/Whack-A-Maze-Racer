package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class LindseyGenericMole extends GenericMole implements Mole{

    LindseyGenericMole(Hole hole){
        super(hole);
        setMoleProperties();
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.molePic;
        this.value = GameConstants.doubleMoleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 1;
    }
}
