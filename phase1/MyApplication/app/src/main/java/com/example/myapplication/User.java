package com.example.myapplication;

import java.io.Serializable;
import java.util.HashMap;

public class User implements Serializable {
    private String email;
    private String password;
    //public static HashMap<String, String> map = new HashMap<>();
    private int score = 0;
    private int streaks = 0;
    private int num_maze_games_played = 0;
    private int lives = 3;


    User(String Email, String Password){
        this.email = Email;
        this.password = Password;
        //map.put(email, password);
    }

    public String getEmail() {
        return email;
    }

    public int getScore(){ return score; }
    public int getStreaks(){
        return this.streaks;
    }
    public int getLives() { return this.lives; }

    public void setScore(int new_score){
        score = new_score;
    }
    public void setStreaks(int Streaks){
        streaks = Streaks;
    }
    public void setLives(int new_lives) { lives = new_lives; }

    public void setNum_maze_games_played(int Whatever){this.num_maze_games_played = Whatever;}
    public int getNum_maze_games_played(){ return this.num_maze_games_played; }
}
