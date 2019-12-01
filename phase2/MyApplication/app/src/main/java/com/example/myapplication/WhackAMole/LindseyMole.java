package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;


/**
 * A mole that looks like professor Shorser. She is so nice that it grants extra points when hit.
 */

class LindseyMole extends Mole {

    LindseyMole(Hole hole){
        super(hole);
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.molePic;
        this.value = GameConstants.doubleMoleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 1;
    }
}
