package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Comparable<User>, Serializable{
    private String email;
    private String password;
    private  HashMap<String, HashMap<String, Object>> map = new HashMap<String, HashMap<String, Object>>();
    private boolean thereIsSaved = false;
    private int lastPlayedLevel = 0;
    private int overallScore = 0;
    private boolean savedToScoreBoard = true;



    User(String Email){
        this.email = Email;
        for (String gameName : GameConstants.GameNames) {
            HashMap<String, Object> statistics = new HashMap<String, Object>();
            for (String statisticsOfGame : GameConstants.getArrayOfStatistics(gameName)) {
                if (statisticsOfGame.equals(GameConstants.MoleStats)) {
                    statistics.put(statisticsOfGame, " ");
                }else {
                    statistics.put(statisticsOfGame, 0);
                }
            }
            map.put(gameName, statistics);
        }
    }

    public boolean isSavedToScoreBoard() {
        return savedToScoreBoard;
    }

    public void setSavedToScoreBoard(boolean savedToScoreBoard) {
        this.savedToScoreBoard = savedToScoreBoard;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getOverallScore() {
        return overallScore;
    }

    public void setOverallScore(int overallScore) {
        if (isSavedToScoreBoard()) {
            this.overallScore = overallScore;
        }
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String, HashMap<String, Object>> getMap() {
        return map;
    }

    private void setStatisticsHelper(Object[] arr) {
        String gameName = (String) arr[0];
        for(int i = 1; i <= arr.length; i++){
            if(i % 2 == 1 && i + 1 <= arr.length){
                String statisticName = (String) arr[i];
                Object newValue = arr[i + 1];
                HashMap<String, Object> gameMap = map.get(gameName);
                if (gameMap != null && gameMap.containsKey(statisticName)) {
                    gameMap.put(statisticName, newValue);
                }
            }

        }
    }

    public void setStatisticsInMap(ArrayList<Object[]> arrayOfGameStats) {
        for (Object[] arr : arrayOfGameStats) {
            setStatisticsHelper(arr);
        }
    }

    public void setStatistic(String gameName, String statisticName, Object statistic) {
        if (map.get(gameName) != null) {
            HashMap<String, Object> statisticMap = map.get(gameName);
            if (statisticMap.get(statisticName) != null) statisticMap.put(statisticName, statistic);
        }
    }

    public Object getStatistic(String gameName, String statisticName) {
        if (map.get(gameName) != null) {
            HashMap<String, Object> statisticMap = map.get(gameName);
            if (statisticMap.get(statisticName) != null) {
                return statisticMap.get(statisticName);
            }
        }
        return null;
    }

    public int getLastPlayedLevel() {
        return this.lastPlayedLevel;
    }

    public void setLastPlayedLevel(int level) {
        this.lastPlayedLevel = level;
    }

    public boolean getThereIsSaved() { return this.thereIsSaved; }
    public void setThereIsSaved(boolean isSaved) { this.thereIsSaved = isSaved; }

    @Override
    public int compareTo(User user) {
        if(this.getOverallScore() < user.getOverallScore()){
            return 1;
        }else if(this.getOverallScore() > user.getOverallScore()){
            return -1;
        }else{
            return 0;
        }
    }

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
    public void setLastPlayedLevel(int level){this.last_played_level = level; }
    public int getLastPlayedLevel(){return this.last_played_level;}
    public void setLoad_moles_stats(String stats){load_moles_stats = stats;}
    public String getLoad_moles_stats(){return this.load_moles_stats;}*/
}
