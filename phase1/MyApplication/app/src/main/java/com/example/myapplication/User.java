package com.example.myapplication;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
    private String email;
    private String password;
    public static HashMap<String, String> map = new HashMap<>();
    private int score = 0;
    private int streaks = 0;
    private int whatever = 0;


    User(String Email, String Password){
        this.email = Email;
        this.password = Password;
        map.put(email, password);
    }

    public String getEmail() {
        return email;
    }

    public int getScore(){ return score; }

    public int getStreaks(){
        return this.streaks;
    }
    public void setScore(int new_score){
        score = new_score;
    }
    public void setStreaks(int Streaks){
        streaks = Streaks;
    }
    public void setWhatever(int Whatever){whatever = Whatever;}
    public int getWhatever(){ return this.whatever; }
}
