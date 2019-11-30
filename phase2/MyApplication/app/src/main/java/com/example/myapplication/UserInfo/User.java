package com.example.myapplication.UserInfo;

import com.example.myapplication.GameConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class User implements Serializable, IUser {
        private String email;
        private String password;
        private  HashMap<String, HashMap<String, Object>> map;
        private int lastPlayedLevel = 0;
        private int overallScore = 0;
        private int currency = 0;



        public User(String Email){
            this.email = Email;
            map = new HashMap<>();
            for (String gameName : GameConstants.GameNames) {
                HashMap<String, Object> statistics = new HashMap<String, Object>();
                for (String statisticsOfGame : GameConstants.getArrayOfStatistics(gameName)) {
                    /*if (statisticsOfGame.equals(GameConstants.MoleStats)) {
                        statistics.put(statisticsOfGame, " ");
                    }else {*/
                        statistics.put(statisticsOfGame, 0);
                    //}
                }
                map.put(gameName, statistics);
            }
        }

        public int getCurrency() {
            return currency;
        }

        public void setCurrency(int currency) {
            this.currency = currency;
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
                this.overallScore = overallScore;
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

        public void setStatisticsInDataStructure(ArrayList<Object[]> arrayOfGameStats) {
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
/*
        @Override
        public int compareTo(User user) {
            if(this.getOverallScore() < user.getOverallScore()){
                return 1;
            }else if(this.getOverallScore() > user.getOverallScore()){
                return -1;
            }else{
                return 0;
            }
        }*/

    @Override
    public int compareTo(IUser iUser) {
        if(this.getOverallScore() < iUser.getOverallScore()){
            return 1;
        }else if(this.getOverallScore() > iUser.getOverallScore()){
            return -1;
        }else{
            return 0;
        }
    }
}
