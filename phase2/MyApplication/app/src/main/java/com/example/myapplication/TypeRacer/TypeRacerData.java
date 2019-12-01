package com.example.myapplication.TypeRacer;


import android.os.Handler;

import java.util.ArrayList;

public class TypeRacerData implements TypeRacerSubject {
    private String correctness;
    private static TypeRacerData INSTANCE = null;

    private ArrayList<TypeRacerObserver> dataObservers;

    private TypeRacerData() {
        dataObservers = new ArrayList<>();
        getNewDataFromRemote();
    }

    // Simulate network
    private void getNewDataFromRemote() {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                setUserData("1/5");
//            }
//        }, 10000);
    }

    // Creates a Singleton of the class
    static TypeRacerData getInstance() {

        INSTANCE = new TypeRacerData();
        return INSTANCE;
    }

    @Override
    public void registerObserver(TypeRacerObserver dataObserver) {

            dataObservers.add(dataObserver);
    }

    @Override
    public void removeObserver(TypeRacerObserver repositoryObserver) {
        dataObservers.remove(repositoryObserver);
    }

    @Override
    public void notifyObservers() {
        for (TypeRacerObserver observer: dataObservers) {
            observer.onUserDataChanged(correctness);
        }
    }

    private void setUserData(String c) {
        this.correctness= c;
        notifyObservers();
    }
}
