package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable {
    private String email;
    private String password;
    private  HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();
   /* private int score = 0;
    private int streaks = 0;
    private int num_maze_games_played = 0;
    private int last_played_level = 0;
    private boolean thereIsSaved = false;
    private String load_moles_stats;*/
   private boolean thereIsSaved = false;
   private int last_played_level = 0;
    private int OverallScore = 0;



    User(String Email, String Password){
        this.email = Email;
        this.password = Password;
        for (String GameName: GameConstants.GameNames) {
            HashMap<String, Object> Stastics = new HashMap<String, Object>();
            for (String StasticsOfGame : GameConstants.getArrayOfStatistics(GameName)) {
                if (StasticsOfGame.equals(GameConstants.MoleStats)){
                    Stastics.put(StasticsOfGame, " ");
                }else {
                    Stastics.put(StasticsOfGame, 0);
                }
            }
            map.put(GameName, Stastics);
        }
    }

    public int getOverallScore() {
        return OverallScore;
    }

    public void setOverallScore(int overallScore) {
        OverallScore = overallScore;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, HashMap<String, Object>> getMap() {
        return map;
    }

    private void SetStasticsHelper(Object[] arr){
        String GameName = (String) arr[0];
        for(int i = 1; i <= arr.length; i++){
            if(i % 2 == 1 && i + 1 <= arr.length){
                String StatisticName = (String) arr[i];
                Object new_value = arr[i + 1];
                HashMap<String, Object> GameMap = map.get(GameName);
                if(GameMap != null && GameMap.containsKey(StatisticName)){
                    GameMap.put(StatisticName, new_value);
                }
            }

        }
    }

    public void SetStasticsInMap(ArrayList<Object[]> ArrayOfGameStats){
        for (Object[] arr: ArrayOfGameStats){
            SetStasticsHelper(arr);
        }
    }

    public void setStatistic(String GameName, String StatisticName, Object Statistic){
        if (map.get(GameName) != null){
            HashMap<String, Object> StasticMap = map.get(GameName);
            if(StasticMap.get(StatisticName) != null) StasticMap.put(StatisticName, Statistic);
        }
    }

    public Object getStatistic(String GameName, String StatisticName){
        if (map.get(GameName) != null){
            HashMap<String, Object> StasticMap = map.get(GameName);
            if(StasticMap.get(StatisticName) != null){
                return StasticMap.get(StatisticName);
            }
        }
        return null;
    }

    public int getLast_played_level(){return this.last_played_level;}

    public void setLast_played_level(int level){this.last_played_level = level; }

    public boolean getThereIsSaved() { return this.thereIsSaved; }
    public void setThereIsSaved(boolean isSaved) { this.thereIsSaved = isSaved; }

    /*public int getScore(){ return score; }
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
    public String getLoad_moles_stats(){return this.load_moles_stats;}*/
}
