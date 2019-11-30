package com.example.myapplication.WhackAMole;

class MoleFactory {
    GenericMole createMole(String moleType, Hole hole){
        if(moleType.equalsIgnoreCase("lindsey")){
            return new LindseyGenericMole(hole);
        }else if(moleType.equalsIgnoreCase("paul")){
            return new PaulGenericMole(hole);
        }else if(moleType.equalsIgnoreCase("generic")){
            return new GenericMole(hole);
        }else if(moleType.equalsIgnoreCase("gem")){
            return new GemGenericMole(hole);
        }
        return null;
    }
}
