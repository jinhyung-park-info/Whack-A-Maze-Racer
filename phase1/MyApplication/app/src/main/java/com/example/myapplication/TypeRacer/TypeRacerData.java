package com.example.myapplication.TypeRacer;


import java.util.ArrayList;
import java.util.Iterator;

public class TypeRacerData implements TypeRacerSubject {
    private int scores;
    private int streaks;
    private int lives;
    private ArrayList<TypeRacerObserver> observerList;

    public TypeRacerData() {
        observerList = new ArrayList<TypeRacerObserver>();
    }

    @Override
    public void registerObserver(TypeRacerObserver o) {
        observerList.add(o);
    }

    @Override
    public void unregisterObserver(TypeRacerObserver o) {
        observerList.remove(observerList.indexOf(o));
    }

    @Override
    public void notifyObservers()
    {
        for (Iterator<TypeRacerObserver> it =
             observerList.iterator(); it.hasNext();)
        {
            TypeRacerObserver o = it.next();
            o.update(scores,streaks,lives);
        }
    }

    private int getScores(){return 1;}

    private int getLatestStreaks()
    {
        // return 2 for simplicity
        return 2;
    }

    private int getLatestLives()
    {
        // return 90 for simplicity
        return 10;
    }

    // This method is used update displays
    // when data changes
    public void dataChanged()
    {
        //get latest data
        scores = getScores();
        streaks = getLatestStreaks();
        lives = getLatestLives();

        notifyObservers();
    }
}
