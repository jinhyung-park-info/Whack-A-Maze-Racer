package com.example.myapplication.TypeRacer;

public interface TypeRacerSubject {
    void registerObserver(TypeRacerObserver o);

    void removeObserver(TypeRacerObserver o);

    void notifyObservers();
}
