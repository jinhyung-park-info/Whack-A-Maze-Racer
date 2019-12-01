package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

/**
 * A Mole that looks like professor Gries. If you hit it, you lose a point instead.
 */

class PaulMole extends Mole {

    PaulMole(Hole hole) {
        super(hole);
    }

    @Override
    public void setMoleProperties() {
        this.molePic = WamView.molePic2;
        this.value = GameConstants.negativeMoleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 0;
    }
}
