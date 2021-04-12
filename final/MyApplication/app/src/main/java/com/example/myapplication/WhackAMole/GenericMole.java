package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

/**
 * A generic mole that grants point to user when hit, and deducts life when missed.
 */

class GenericMole extends Mole {

    GenericMole(Hole hole) {
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
