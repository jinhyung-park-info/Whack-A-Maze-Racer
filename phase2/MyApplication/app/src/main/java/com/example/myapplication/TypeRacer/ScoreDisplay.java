package com.example.myapplication.TypeRacer;

public class ScoreDisplay implements TypeRacerObserver{
    private int scores, streaks, lives;

    public void update(int scores, int streaks,
                       int lives)
    {
        this.scores = scores;
        this.streaks = streaks;
        this.lives = lives;
        display();
    }

    public void display(){}
}
