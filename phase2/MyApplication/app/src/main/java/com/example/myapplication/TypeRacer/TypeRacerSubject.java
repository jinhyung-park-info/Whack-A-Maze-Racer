package com.example.myapplication.TypeRacer;

public interface TypeRacerSubject {
    public void registerObserver(TypeRacerObserver o);
    public void removeObserver(TypeRacerObserver o);
    public void notifyObservers();
}
