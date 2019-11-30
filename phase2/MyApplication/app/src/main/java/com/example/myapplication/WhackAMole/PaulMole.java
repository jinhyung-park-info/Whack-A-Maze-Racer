package com.example.myapplication.WhackAMole;

import com.example.myapplication.GameConstants;

class PaulMole extends Mole{

    PaulMole(Hole hole){
        super(hole);
        this.molePic = WamView.molePic2;
        this.value = GameConstants.negativeMoleValue;
        this.gemValue = GameConstants.nonGemMoleValue;
        this.lifeCount = 0;
    }
}
