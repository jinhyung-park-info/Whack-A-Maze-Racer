package com.example.myapplication.TypeRacer;

import com.example.myapplication.TypeRacer.TypeRacerObserver;

public interface TypeRacerSubject {
    public void registerObserver(TypeRacerObserver o);
    public void unregisterObserver(TypeRacerObserver o);
    public void notifyObservers();
}
