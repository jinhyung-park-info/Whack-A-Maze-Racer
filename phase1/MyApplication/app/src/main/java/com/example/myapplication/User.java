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
    private int last_played_level = 0;
    private boolean thereIsSaved = false;
    private String load_moles_stats;



    User(String Email, String Password){
        this.email = Email;
        this.password = Password;
        //map.put(email, password);
    }

    public String getEmail() {
        return email;
    }

    public int getScore(){ return score; }
    public int getStreaks(){ return this.streaks; }

    public boolean getThereIsSaved() { return this.thereIsSaved; }
    public void setThereIsSaved(boolean isSaved) { this.thereIsSaved = isSaved; }

    public void setScore(int new_score){ score = new_score; }
    public void setStreaks(int Streaks){
        streaks = Streaks;
    }
    public void setNum_maze_games_played(int Whatever){this.num_maze_games_played = Whatever;}
    public int getNum_maze_games_played(){ return this.num_maze_games_played; }
    public void setLast_played_level(int level){this.last_played_level = level; }
    public int getLast_played_level(){return this.last_played_level;}
    public void setLoad_moles_stats(String stats){load_moles_stats = stats;}
    public String getLoad_moles_stats(){return this.load_moles_stats;}
}
