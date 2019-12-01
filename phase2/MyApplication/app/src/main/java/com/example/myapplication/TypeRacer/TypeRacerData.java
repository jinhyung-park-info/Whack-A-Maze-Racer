package com.example.myapplication.TypeRacer;


import java.util.ArrayList;

public class TypeRacerData implements TypeRacerSubject {
    private String correctness="0/25";
    private static TypeRacerData INSTANCE = null;

    private ArrayList<TypeRacerObserver> dataObservers;

    private TypeRacerData(String c) {
        dataObservers = new ArrayList<>();
        getNewDataFromRemote(c);
    }

    // Simulate network
    private void getNewDataFromRemote(String c) {
//        final Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
                setCorrectness(c);
//            }
//        }, 10000);
    }

    // Creates a Singleton of the class
    static TypeRacerData getInstance(String c) {

        INSTANCE = new TypeRacerData(c);
        return INSTANCE;
    }

    @Override
    public void registerObserver(TypeRacerObserver dataObserver) {

        dataObservers.add(dataObserver);
        notifyObservers();
    }

    @Override
    public void removeObserver(TypeRacerObserver dataObserver) {
        dataObservers.remove(dataObserver);
    }

    @Override
    public void notifyObservers() {
        for (TypeRacerObserver observer: dataObservers) {
            observer.onUserDataChanged(correctness);
        }
    }

    private void setCorrectness(String c) {
        this.correctness= c;

    }
}
